#!/usr/bin/bash

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

echo "Starting transaction tests"

echo "test 1"

member=$(runCommand "register-member \"john doe\" john.doe@mail.com password 26/05/1995")
memberId=$(echo "$member" | grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
shop=$(runCommand "add-shop \"testShop\"  \"200 rue des lucioles, biot\"")
shopId=$(echo "$shop" | grep -oP '(?<=id : )(.*)(?=, name)' | head -n -1)
product1=$(runCommand "add-product $shopId \"pizza 4 fromage\" 12 50 0")
product2=$(runCommand "add-product $shopId \"marguerite\" 10 20 0")
productId1=$(echo "$product1" | grep -oP '(?<=id : )(.*)(?= Name)' | head -n -1)
productId2=$(echo "$product2" | grep -oP '(?<=id : )(.*)(?= Name)' | head -n -1)
gift1=$(runCommand "add-gift $shopId 20 \"mini pizza\" REGULAR")
gift2=$(runCommand "add-gift $shopId 30 \"regular pizza\" VFP")
giftId1=$(echo "$gift1" | grep -oP '(?<=giftId=)(.*)(?=, shop)' | head -n -1)
giftId2=$(echo "$gift2" | grep -oP '(?<=giftId=)(.*)(?=, shop)' | head -n -1)

echo $memberId
echo $shopId
echo $productId1
echo $productId2
echo $giftId1
echo $giftId2






for localResult in "${globalResult[@]}"
do
    if [ $localResult -ne 0 ]; then
        printf "${RED}${BOLD}Test failed${NC}\n"
#        exit 1
    fi
done