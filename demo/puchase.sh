#!/bin/bash

function runCommand() {
  echo "$1" | socat - exec:"docker attach cli",pty
  sleep 1
  docker logs cli --tail=1
}

runCommand "create-purchase-with-card 1 1 123456896983 --itemIds 1 2 --itemQuantities 2 1"