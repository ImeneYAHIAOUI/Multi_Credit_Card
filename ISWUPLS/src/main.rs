mod parking;

use std::{env, io};
use actix_web::{App, HttpServer, Responder, main};
use actix_web::middleware::Logger;
use actix_web::web::Data;

#[actix_web::main]
async fn main() -> io::Result<()> {
    env::set_var("RUST_LOG", "actix_web=debug,actix_server=info");
    env::set_var("RUST_BACKTRACE", "1");
    env_logger::init();


    // set up database connection pool

    HttpServer::new(|| {

        App::new()

            // enable logger - always register actix-web Logger middleware last
            .wrap(Logger::default())
            // register HTTP requests handlers
            .service(parking::list)
            .service(parking::get)
            .service(parking::create_parking)
            .service(parking::delete)



    })
        .bind("0.0.0.0:8085")?
        .run()
        .await

}


