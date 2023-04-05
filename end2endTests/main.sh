#!/bin/bash

printf '\n'
waitFor="Started CliApplication in"
while ! docker inspect -f '{{.State.Status}}' cli | grep -q "running";
 do
      echo "Waiting for CLI to start ..."
      sleep 5
done
  echo "CLI has started!"
  printf '\n'

./memberTests.sh