#!/bin/bash

function runCommand() {
  echo "$1" | socat - exec:"docker attach cli",pty
  sleep 1
  docker logs cli --tail=1
}


runCommand "start-parking 123456789 sashatouille@rust.crab 50"

