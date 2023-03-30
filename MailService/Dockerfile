FROM rust:latest as build

# create a new empty shell project
RUN USER=root cargo new --bin MAILSERVICE
WORKDIR /MAILSERVICE

# copy over your manifests
COPY ./Cargo.lock ./Cargo.lock
COPY ./Cargo.toml ./Cargo.toml

# this build step will cache your dependencies
RUN cargo build --release
RUN rm src/*.rs

# copy your source tree
COPY ./src ./src

# build for release
RUN rm ./target/release/deps/MAILSERVICE*
RUN cargo build --release


FROM rust:1.55.0-slim-buster

# copy the build artifact from the build stage
COPY --from=build /MAILSERVICE/target/release/MAILSERVICE .
EXPOSE 8090
# set the startup command to run your binary
CMD ["./MAILSERVICE"]