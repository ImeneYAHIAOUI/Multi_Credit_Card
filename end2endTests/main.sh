#!/bin/bash

apt-get install socat -y

printf '\n'
waitFor="Started CliApplication in"
while ! docker logs cli --tail=1 | grep -q "$waitFor";
 do
      echo "Waiting for CLI to start ..."
      sleep 5
done
  echo "CLI has started!"
  printf '\n'

./memberTests.sh