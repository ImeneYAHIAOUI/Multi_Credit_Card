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
    static ref SURVEYSERVICE: Mutex<HashMap<String, SurveyService >> = Mutex::new(HashMap::new());
}

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct MailService {
    pub id: String,
    pub sender: String,
    pub receivers: Vec<String>,
    pub subject: String,
    pub mail_content: String
}

impl MailService {
    pub fn new(sender: String, receivers: Vec<String>, subject: String, mail_content: String) -> Self {
        Self {
            id : Uuid::new_v4().to_string(),
            sender,
            receivers,
            subject,
            mail_content
        }
    }

}#[derive(Debug, Serialize, Deserialize, Clone)]

pub struct SurveyService {
    pub id: String,
    pub sender: String,
    pub receivers: Vec<String>,
    pub questions: Vec<Question>,
}

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Question {
    pub title: String,
    pub possibleAnswers: Vec<String>,
}

impl Question {
    pub fn new(title: String, possibleAnswers: Vec<String>) -> Self {
        Self {
            title,
            possibleAnswers
        }
    }
}

impl SurveyService {
    pub fn new(sender: String, receivers: Vec<String>, questions: Vec<Question>) -> Self {
        Self {
            id:Uuid::new_v4().to_string(),
            sender,
            receivers,
            questions
        }
    }
}



#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct MailSenderRequest {
    pub sender: Option<String>,
    pub receivers: Option<Vec<String>>,
    pub subject: Option<String>,
    pub mail_content: Option<String>
}

impl MailSenderRequest {
    pub fn to_mail_sender(&self) -> Option<MailService> {
        match (self.sender.clone(), self.receivers.clone(), self.subject.clone(), self.mail_content.clone()) {
            (Some(sender), Some(receivers), Some(subject), Some(mail_content)) => {
                Some(MailService::new(sender, receivers, subject, mail_content))
            }
            _ => None,
        }
    }
}#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct SurveySenderRequest {
    pub sender: Option<String>,
    pub receivers: Option<Vec<String>>,
    pub questions: Option<Vec<Question>>
}

impl SurveySenderRequest {
    pub fn to_survey_sender(&self) -> Option<SurveyService> {
        match (self.sender.clone(), self.receivers.clone(), self.questions.clone()) {
            (Some(sender), Some(receivers), Some(questions)) => {
                Some(SurveyService::new(sender, receivers, questions))
            }
            _ => None,
        }
    }
}



#[post("/admin/mail")]
pub async fn send_mail(request: Json<MailSenderRequest>)
                       -> HttpResponse{
    let mail_sender_request = MailSenderRequest {
        sender: request.sender.clone(),
        receivers: request.receivers.clone(),
        subject: request.subject.clone(),
        mail_content: request.mail_content.clone()
    };
    let mail_sender_option = mail_sender_request.to_mail_sender();
    match mail_sender_option {
        Some(mail) => {
            MAILSERVICE.lock().unwrap().insert(mail.id.clone(), mail.clone());
            HttpResponse::Created()
                .content_type("application/json")
                .json(mail)
        }
        _None => HttpResponse::BadRequest()
            .content_type("application/json")
            .await
            .unwrap(),
    }
}

#[post("/admin/survey")]
pub async fn send_survey(request: Json<SurveySenderRequest>)
                         -> HttpResponse{
    let survey_sender_request = SurveySenderRequest {
        sender: request.sender.clone(),
        receivers: request.receivers.clone(),
        questions: request.questions.clone()
    };
    let survey_sender_option = survey_sender_request.to_survey_sender();
    match survey_sender_option {
        Some(survey) => {
            SURVEYSERVICE.lock().unwrap().insert(survey.id.clone(), survey.clone());
            HttpResponse::Created()
                .content_type("application/json")
                .json(survey)
        }
        _None => HttpResponse::BadRequest()
            .content_type("application/json")
            .await
            .unwrap(),
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
