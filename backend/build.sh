#!/bin/bash

echo "Compiling the TCF Spring BACKEND within a multi-stage docker build"

docker build --build-arg JAR_FILE=backend-0.0.1-SNAPSHOT.jar -t equipef/tcf-spring-backend .

