#!/bin/bash

RED='\033[0;31m'
NC='\033[0m'
GREEN='\033[0;32m'
BOLD='\033[1m'

apt install socat -y

function runCommand() {
    echo "$1" | socat - exec:"docker attach cli",pty
    sleep 1
    docker logs cli --tail=1
}

function waitForCLIStart(){
   printf '\n'
   waitFor="Started CliApplication in"
   while ! docker logs cli --tail=1 | grep -q "$waitFor"; do
        echo "Waiting for CLI to start ..."
        sleep 5
    done
    echo "CLI has started!"
    printf '\n'
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
echo "Starting tests"
echo "test 1: register member"

result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
expected="member { id=(.*), name=john doe, mail=john.doe@mail.com, password=password, birthDate=1995-05-26, membershipCardNumber=(.*), points=0, balance=0.0, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)



echo "test 2: register member with same mail"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
expected="member { id=(.*), name=john doe, mail=john.doe@mail.com, password=password, birthDate=1995-05-26, membershipCardNumber=(.*), points=0, balance=0.0, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 3: archive member"
result=$(runCommand "archive-member john.doe@mail.com")
expected="Member archived"
assertEquals "$expected" "$result"
globalResult+=($?)


echo "test 4: delete member"
result=$(runCommand "delete-member john.doe@mail.com")
expected="Member deleted"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 5: create member with underage birthdate"

result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/2015")
expected='422 : "{"id":0,"name":"under age","mail":"","password":"","birthDate":"","points":0,"balance":0.0,"status":null,"membershipCardNumber":null}'
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 6: create member with invalid mail"

result=$(runCommand "register-member \"john doe\" john.doe.com password 26/05/1995")
expected='422 : "{"error":"Cannot process Member information","details":"Validation failed for argument [0]*'
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 7: create member with invalid password"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com ps 26/05/2015")
expected='422 : "{"error":"Cannot process Member information","details":"Validation failed for argument [0]*'
assertEquals "$expected" "$result"
globalResult+=($?)





for localResult in "${globalResult[@]}"
do
    if [ $localResult -ne 0 ]; then
        printf "${RED}${BOLD}Test failed${NC}\n"
        exit 1
    fi
done
