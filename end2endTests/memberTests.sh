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

function assertEquals() {
  echo "$2" | grep -Eq "$1"
  if [ $? -eq 0 ]; then
    printf " | ${GREEN}Passed${NC}\n"
    return 0
  else
    printf " | ${RED}Failed${NC}\n"
    return 1
  fi
}

#tests
echo "Starting member tests"

printf "Test 1: register member"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
expected="member { id=(.*), name=john doe, mail=john.doe@mail.com, password=password, birthDate=1995-05-26, membershipCardNumber=(.*), points=0, balance=0.0, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)
memberId=$(echo "$result" | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)

printf "Test 2: register member with same mail"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
expected="member { id=(.*), name=john doe, mail=john.doe@mail.com, password=password, birthDate=1995-05-26, membershipCardNumber=(.*), points=0, balance=0.0, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 3: archive member"
result=$(runCommand "archive-member john.doe@mail.com")
expected="Member archived"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 4: get member information"
result=$(runCommand "get-member-info $memberId")
expected="member { id=(.*), name=john doe, mail=john.doe@mail.com, password=password, birthDate=1995-05-26, membershipCardNumber=(.*), points=0, balance=0.0, status=EXPIRED }"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 5: update member status"
result=$(runCommand "update-status john.doe@mail.com VFP")
expected="CliUpdateStatus{mail='john.doe@mail.com', status='VFP'}"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 6: update member status"
result=$(runCommand "update-status john.doe@mail.com REGULAR")
expected="CliUpdateStatus{mail='john.doe@mail.com', status='REGULAR'}"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 7: update member information"
command="update-member ${memberId} --name \"john smith\" --password wordpass --birthDate 26/09/2000"
result=$(runCommand "$command")
expected="member { id=(.*), name=john smith, mail=john.doe@mail.com, password=wordpass, birthDate=2000-09-26, membershipCardNumber=(.*), points=0, balance=0.0, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 8: renew membership"
command="renew-membership ${memberId}"
result=$(runCommand "$command")
expected='404 : "{"id":0,"name":" ","mail":"","password":"","birthDate":"too early to renew","points":0,"balance":0.0,"status":null,"membershipCardNumber":null}"'
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 9: charge membership card"
command="charge-membership-card ${memberId} 5.5 123456789896983"
result=$(runCommand "$command")
expected="Membership card charged"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 10: get member information"
result=$(runCommand "get-member-info $memberId")
expected="member { id=(.*), name=john smith, mail=john.doe@mail.com, password=wordpass, birthDate=2000-09-26, membershipCardNumber=(.*), points=0, balance=5.5, status=REGULAR }"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 11: delete member"
result=$(runCommand "delete-member john.doe@mail.com")
expected="Member deleted"
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 12: create member with underage birthdate"

result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/2015")
expected='422 : "{"id":0,"name":"under age","mail":"","password":"","birthDate":"","points":0,"balance":0.0,"status":null,"membershipCardNumber":null}'
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 13: create member with invalid mail"

result=$(runCommand "register-member \"john doe\" john.doe.com password 26/05/1995")
expected='422 : "{"error":"Cannot process Member information","details":"Validation failed for argument [0]*'
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 14: create member with invalid password"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com ps 26/05/2015")
expected='422 : "{"error":"Cannot process Member information","details":"Validation failed for argument [0]*'
assertEquals "$expected" "$result"
globalResult+=($?)

printf "Test 15: create member with invalid birthdate"
result=$(runCommand "register-member \"john doe\" john.doe@mail.com password 45217")
expected='422 : "{"error":"Cannot process Member information","details":"Validation failed for argument [0]*'
assertEquals "$expected" "$result"
globalResult+=($?)

for localResult in "${globalResult[@]}"; do
  if [ $localResult -ne 0 ]; then
    printf "${RED}${BOLD}Test failed${NC}\n"
    exit 1
  fi
done
