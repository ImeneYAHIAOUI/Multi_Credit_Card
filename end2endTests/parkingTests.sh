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


#tests

echo "Starting parking tests"

echo "test 1: start parking for non vfp member"

member=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
result=$(runCommand "start-parking 123456789 john.doe@mail.com  50")
expected='422 : "{"mail":"user not vfp","carRegistrationNumber":" ","startTime":null,"endTime":null,"parkingSpotNumber":0}"'
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 2: start parking for vfp member"
changeStatus=$(runCommand "update-status john.doe@mail.com VFP")
result=$(runCommand "start-parking 123456789 john.doe@mail.com  50")
expected='parking {car registration number : 123456789, mail : john.doe@mail.com, parking spot number : 50, starting time : (.*), ending time : (.*)}'
assertEquals "$expected" "$result"
globalResult+=($?)

deleteMember=$(runCommand "delete-member john.doe@mail.com")



for localResult in "${globalResult[@]}"
do
    if [ $localResult -ne 0 ]; then
        printf "${RED}${BOLD}Test failed${NC}\n"
        exit 1
    fi
done