#!/bin/bash

function runCommand() {
  echo "$1" | socat - exec:"docker attach cli",pty
  sleep 1
  docker logs cli --tail=1
}

runCommand "spend-points-for-gift 1 1 2"