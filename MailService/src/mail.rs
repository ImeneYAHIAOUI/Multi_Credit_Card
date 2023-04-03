use std::collections::HashMap;
use std::fmt::{Display, Formatter};
use chrono::{DateTime, NaiveDateTime, Utc};
use derive_more::{Display};
use std::sync::Mutex;
use lazy_static::lazy_static;


use actix_web::{
    get,
    post,
    delete,
    web::Path,
    web::Json,
    web::Data,
    HttpResponse,
    http::StatusCode,
    error::ResponseError,
};
use serde::{Serialize, Deserialize};
use uuid::Uuid;

lazy_static! {
    static ref MAILSERVICE: Mutex<HashMap<String, MailService >> = Mutex::new(HashMap::new());
}

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct MailService {
    pub id: String,
    pub sender: String,
    pub receivers: Vec<String>,
    pub subject: String
}

impl MailService {
    pub fn new(sender: String, receivers: Vec<String>, subject: String) -> Self {
        Self {
            id: Uuid::new_v4().to_string(),
            sender,
            receivers,
            subject
        }
    }
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct MailSenderRequest {
    pub sender: Option<String>,
    pub receivers: Option<Vec<String>>,
    pub subject: Option<String>,

}

impl MailSenderRequest {
    pub fn to_mail_sender(&self) -> Option<MailService> {
        match (self.sender.clone(), self.receivers.clone(), self.subject.clone()) {
            (Some(sender), Some(receivers), Some(subject)) => {
                Some(MailService::new(sender, receivers, subject))
            }
            _ => None,
        }
    }
}

#[derive(Debug, Display)]
pub enum MailError {
    MissingSender,
    MissingReceivers,
    MissingSubject,
    MailCreationFailure,
}

impl ResponseError for MailError {
    fn status_code(&self) -> StatusCode {
        match self {
            MailError::MissingReceivers => StatusCode::NOT_FOUND,
            MailError::MissingSubject => StatusCode::NOT_FOUND,
            MailError::MissingSender => StatusCode::NOT_FOUND,
            MailError::MailCreationFailure => StatusCode::BAD_REQUEST
        }
    }
    fn error_response(&self) -> HttpResponse {
        HttpResponse::build(self.status_code())
            .content_type("application/json")
            .body(self.to_string())
    }
}

#[post("/mail")]
pub async fn create_mail(request: Json<MailSenderRequest>)
                         -> Result<Json<MailService>, MailError>{
    let mail_sender_request = MailSenderRequest {
        sender: request.sender.clone(),
        receivers: request.receivers.clone(),
        subject: request.subject.clone(),
    };
    let mail_sender_option = mail_sender_request.to_mail_sender();
    match mail_sender_option {
        Some(mail) => {
            MAILSERVICE.lock().unwrap().insert(mail.id.clone(), mail.clone());
            Ok(Json(mail))
        }
        None => Err(MailError::MailCreationFailure)
    }
}

#[get("/mail/{id}")]
pub async fn get(path: Path<(String,)>) -> HttpResponse {
    let found_mail: Option<MailService> = MAILSERVICE.lock().unwrap().get(&path.into_inner().0).to_owned().cloned();
    match found_mail {
        Some(mail) => HttpResponse::Ok()
            .content_type("application/json")
            .json(mail),
        None => HttpResponse::NoContent()
            .content_type("application/json")
            .await
            .unwrap(),
    }
}

#[get("/mail")]
pub async fn list() -> HttpResponse {
    HttpResponse::Ok()
        .content_type("application/json")
        .json(MAILSERVICE.lock().unwrap().values().cloned().collect::<Vec<MailService>>())
}

#[delete("/mail/{id}")]
pub async fn delete(path: Path<(String,)>) -> HttpResponse {
    MAILSERVICE.lock().unwrap().remove(&path.into_inner().0);
    HttpResponse::Ok()
        .content_type("application/json")
        .json(MAILSERVICE.lock().unwrap().values().cloned().collect::<Vec<MailService>>())
}
