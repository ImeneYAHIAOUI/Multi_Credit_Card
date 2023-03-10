use std::collections::HashMap;
use std::fmt::{Display};
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
    static ref PARKING: Mutex<HashMap<String, Parking>> = Mutex::new(HashMap::new());
}



#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Parking {
    pub id: String,
    pub car_reg_num: String,
    pub parking_spot_number: i32,
    pub parking_date_time: DateTime<Utc>,
    pub parking_duration: i64,
    pub parking_end_date_time: DateTime<Utc>,
}

impl Parking {
    pub fn new(car_reg_num: String, parking_spot_number: i32,  parking_date_time: DateTime<Utc>, parking_duration: i64, parking_end_date_time: DateTime<Utc>) -> Self {
        Self {
            id: Uuid::new_v4().to_string(),
            car_reg_num,
            parking_spot_number,
            parking_date_time,
            parking_duration,
            parking_end_date_time,
        }
    }
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct ParkingRequest {
    pub car_reg_num: Option<String>,
    pub parking_spot_number: Option<i32>,
    pub parking_date_time: Option<i64>,
    pub parking_duration: Option<i64>,
}

impl ParkingRequest {
    pub fn to_parking(&self) -> Option<Parking> {
        match (self.car_reg_num.clone(), self.parking_spot_number.clone(), self.parking_date_time.clone(), self.parking_duration.clone()) {
            (Some(car_reg_num), Some(parking_spot_number), Some(parking_date_time), Some(parking_duration)) => {
                let parking_date_time = DateTime::<Utc>::from_utc(NaiveDateTime::from_timestamp_opt(parking_date_time, 0).unwrap(), Utc);
                let parking_end_date_time = parking_date_time + chrono::Duration::seconds(parking_duration);
                Some(Parking::new(car_reg_num, parking_spot_number, parking_date_time, parking_duration, parking_end_date_time))
            }
            _ => None,
        }
    }
}


#[derive(Debug, Display)]
pub enum ParkingError {
    MissingCarRegNum,
    MissingParkingSpotNumber,
    MissingParkingDateTime,
    MissingParkingDuration,
    InvalidParkingDateTime,
    InvalidParkingDuration,
    ParkingCreationFailure,
}




impl ResponseError for ParkingError {
    fn status_code(&self) -> StatusCode {
        match self {
            ParkingError::MissingCarRegNum => StatusCode::NOT_FOUND,
            ParkingError::MissingParkingSpotNumber => StatusCode::NOT_FOUND,
            ParkingError::MissingParkingDateTime => StatusCode::NOT_FOUND,
            ParkingError::MissingParkingDuration => StatusCode::NOT_FOUND,
            ParkingError::InvalidParkingDateTime => StatusCode::BAD_REQUEST,
            ParkingError::InvalidParkingDuration => StatusCode::BAD_REQUEST,
            ParkingError::ParkingCreationFailure => StatusCode::BAD_REQUEST
        }
    }
    fn error_response(&self) -> HttpResponse {
        HttpResponse::build(self.status_code())
            .content_type("application/json")
            .body(self.to_string())
    }
}




#[post("/parking")]
pub async fn create_parking ( request: Json<ParkingRequest>)
    -> Result<Json<Parking>, ParkingError>{
    let parkingRequest = ParkingRequest {
        car_reg_num: request.car_reg_num.clone(),
        parking_spot_number: request.parking_spot_number.clone(),
        parking_date_time: request.parking_date_time.clone(),
        parking_duration: request.parking_duration.clone(),
    };
    let parking_option = parkingRequest.to_parking();
    match parking_option {
        Some(parking) => {
            PARKING.lock().unwrap().insert(parking.id.clone(), parking.clone());
            Ok(Json(parking))
        }
        None => Err(ParkingError::ParkingCreationFailure)

    }
}
#[get("/parking/{id}")]
pub async fn get(path: Path<(String,)>) -> HttpResponse {
    let found_parking: Option<Parking> = PARKING.lock().unwrap().get(&path.into_inner().0).to_owned().cloned();
    match found_parking {
        Some(parking) => HttpResponse::Ok()
            .content_type("application/json")
            .json(parking),
        None => HttpResponse::NoContent()
            .content_type("application/json")
            .await
            .unwrap(),
    }
}



#[get("/parking")]
pub async fn list() -> HttpResponse {
    HttpResponse::Ok()
        .content_type("application/json")
        .json(PARKING.lock().unwrap().values().cloned().collect::<Vec<Parking>>())

}

#[delete("/parking/{id}")]
pub async fn delete(path: Path<(String,)>) -> HttpResponse {
    PARKING.lock().unwrap().remove(&path.into_inner().0);
    HttpResponse::Ok()
        .content_type("application/json")
        .json(PARKING.lock().unwrap().values().cloned().collect::<Vec<Parking>>())
}
