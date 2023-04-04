#!/bin/bash

echo "Compiling the TCF Spring CLI within a multi-stage docker build"

docker build --build-arg JAR_FILE=multicard-cli-0.0.1-SNAPSHOT.jar -t equipef/tcf-spring-cli .
