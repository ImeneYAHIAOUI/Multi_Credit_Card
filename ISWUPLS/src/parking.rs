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
    pub parking_date_time: i64,
    pub parking_duration: i64,
    pub parking_end_date_time: i64,
}

impl Parking {
    pub fn new(car_reg_num: String, parking_spot_number: i32,  parking_date_time: i64, parking_duration: i64, parking_end_date_time: i64) -> Self {
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
                Some(Parking::new(car_reg_num, parking_spot_number, parking_date_time, parking_duration, parking_date_time+parking_duration))
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






#[post("/parking")]
pub async fn create_parking ( request: Json<ParkingRequest>)
    ->  HttpResponse {
    println!("create_parking: {:?}", request);
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
            HttpResponse::Created()
                .content_type("application/json")
                .json(parking)
        }
        _None => HttpResponse::BadRequest()
            .content_type("application/json")
            .await
            .unwrap(),

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

#[get("/parking")]
pub async fn get_parking_by_car_number(request: String) -> HttpResponse {
    let found_parking: Option<Parking>  = PARKING.lock().unwrap().values().find(|parking| parking.car_reg_num == request).to_owned().cloned();
    match found_parking {
        Some(parking) => {
            HttpResponse::Ok()
                .content_type("application/json")
                .json(parking)
        },
        None => HttpResponse::NoContent()
            .content_type("application/json")
            .await
            .unwrap(),
    }
}

#[delete("/parking/{id}")]
pub async fn delete(path: Path<(String,)>) -> HttpResponse {
    PARKING.lock().unwrap().remove(&path.into_inner().0);
    HttpResponse::Ok()
        .content_type("application/json")
        .json(PARKING.lock().unwrap().values().cloned().collect::<Vec<Parking>>())
}
