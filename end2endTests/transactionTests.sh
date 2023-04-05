#!/bin/bash

RED='\033[0;31m'
NC='\033[0m'
GREEN='\033[0;32m'
BOLD='\033[1m'


function runCommand() {
    echo "$1" | socat - exec:"docker attach cli",pty
    sleep 1
    docker logs cli --tail=1
}



function assertEquals(){
    echo "$2" |grep -Eq "$1"
    if [ $? -eq 0 ]; then
        printf "${GREEN}Test passed${NC}\n"
        return 0
    else
        printf "${RED}Test failed${NC}\n"
        return 1
    fi
}
