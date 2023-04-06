#!/bin/bash

docker-compose up -d --build

printf '\n'
waitFor="Started CliApplication in"
i=0
while ! docker inspect -f '{{.State.Status}}' cli | grep -q "running" && [ $i -lt 10 ];
 do
      echo "Waiting for CLI to start ..."
      sleep 5
      i=$((i+1))
done
  echo "CLI has started!"
  printf '\n'

./end2endTests/memberTests.sh
./end2endTests/parkingTests.sh

docker-compose down

docker rm server
docker rm cli
docker rm db
docker rm bank
docker rm mailservice
docker rm iswupls