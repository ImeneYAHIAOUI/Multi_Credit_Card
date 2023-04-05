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
echo "Starting transaction tests"

echo "test 1: create-purchase-with-card"
member=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
memberId=$(echo "$member" | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
shop=$(runCommand "add-shop \"testShop\" \"20 route des lucioles\"");
shopId=$(echo $shop | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
product=$(runCommand "add-product ${shopId} \"testProduct\" 10.0 20 0")
product2=$(runCommand "add-product ${shopId} \"testProduct2\" 5.0 20 20")
productId=$(echo $product | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
productId2=$(echo $product2 | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
result=$(runCommand "create-purchase-with-card ${memberId}  ${shopId} --ItemId ${productId} ${productId2} --ItemQuantity 1 2")
expected="purchase { id=(.*), date=(.*), totalCost=25.0, pointsGained=0, status=PAID, shopId=(.*), memberId=(.*), items=\[item { id=(.*), quantity=1, price=10.0, productId=(.*) }, item { id=(.*), quantity=2, price=5.0, productId=(.*) }\] }"

assertEquals "$expected" "$result"
globalResult+=($?)



for localResult in "${globalResult[@]}"
do
    if [ $localResult -ne 0 ]; then
        printf "${RED}${BOLD}Test failed${NC}\n"
        exit 1
    fi
done

