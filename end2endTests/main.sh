#!/bin/bash

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

./memberTests.sh