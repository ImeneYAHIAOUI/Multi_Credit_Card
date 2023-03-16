FROM rust:latest as build

# create a new empty shell project
RUN USER=root cargo new --bin ISWUPLS
WORKDIR /ISWUPLS

# copy over your manifests
COPY ./Cargo.lock ./Cargo.lock
COPY ./Cargo.toml ./Cargo.toml

# this build step will cache your dependencies
RUN cargo build --release
RUN rm src/*.rs

# copy your source tree
COPY ./src ./src

# build for release
RUN rm ./target/release/deps/ISWUPLS*
RUN cargo build --release


FROM rust:1.55.0-slim-buster

# copy the build artifact from the build stage
COPY --from=build /ISWUPLS/target/release/ISWUPLS .
EXPOSE 8085
# set the startup command to run your binary
CMD ["./ISWUPLS"]