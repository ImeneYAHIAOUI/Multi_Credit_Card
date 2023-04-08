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


echo "test 1 : purchase with cash"

result=$(runCommand "create-purchase-with-cash $memberId $shopId --itemIds $productId1 $productId2 --itemQuantities 2 1")
expected="purchase{memberId=$memberId, shop=$shopId, earnedPoints=(.*), totalPrice=(.*), creditCardNumber='null', items=(.*), quantities=(.*)}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 2 : purchase with credit card"

result=$(runCommand "create-purchase-with-card $memberId $shopId 123456896983 --itemIds $productId1 $productId2 --itemQuantities 2 1")
expected="purchase{memberId=$memberId, shop=$shopId, earnedPoints=(.*), totalPrice=(.*), creditCardNumber='123456896983', items=(.*), quantities=(.*)}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 3 : purchase with membership card with insufficient balance"

result=$(runCommand "create-purchase-with-membership-card $memberId $shopId --itemIds $productId1 $productId2 --itemQuantities 2 1")
expected="422 : \"{\"id\":0,\"date\":\"insuffisante balance\",\"memberAccount\":0,\"shop\":0,\"earnedPoints\":0,\"totalPrice\":0.0,\"creditCardNumber\":\"\",\"items\":null,\"quantities\":null,\"paymentMethod\":null}\""
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 4 : purchase with membership card after charging membership card"

charge=$(runCommand "charge-membership-card $memberId 100 123456896983")
result=$(runCommand "create-purchase-with-membership-card $memberId $shopId --itemIds $productId1 $productId2 --itemQuantities 2 1")
expected="purchase{memberId=$memberId, shop=$shopId, earnedPoints=(.*), totalPrice=(.*), creditCardNumber='null', items=(.*), quantities=(.*)}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 5 : get a regular gift"

result=$(runCommand "spend-points-for-gift $memberId $shopId $giftId1")
expected="usePoints{memberId=$memberId, shop=$shopId, pointsUsed=(.*), gift=(.*)}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 6 : get a VFP gift without the vfp status"

result=$(runCommand "spend-points-for-gift $memberId $shopId $giftId2")
expected="422 : \"{\"id\":0,\"date\":\"transaction declined\",\"memberAccount\":0,\"shop\":0,\"pointsUsed\":0,\"gift\":0}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 7 : get a VFP gift with the vfp status"
changeStatus=$(runCommand "update-status john.doe@mail.com VFP")
result=$(runCommand "spend-points-for-gift $memberId $shopId $giftId2")
expected="usePoints{memberId=$memberId, shop=$shopId, pointsUsed=(.*), gift=(.*)}"
assertEquals "$expected" "$result"
globalResult+=($?)

echo "test 8 : get a gift with insufficient points"

gift3=$(runCommand "add-gift $shopId 100000 \"large pizza\" VFP")
giftId3=$(echo "$gift3" | grep -oP '(?<=giftId=)(.*)(?=, shop)' | head -n -1)

result=$(runCommand "spend-points-for-gift $memberId $shopId $giftId3")
expected="422 : \"{\"id\":0,\"date\":\"insuffisante points\",\"memberAccount\":0,\"shop\":0,\"pointsUsed\":0,\"gift\":0}"
assertEquals "$expected" "$result"
globalResult+=($?)

deleteMember=$(runCommand "delete-member-with-id $memberId")
deleteShop=$(runCommand "delete-shop $shopId")




for localResult in "${globalResult[@]}"
do
    if [ $localResult -ne 0 ]; then
        printf "${RED}${BOLD}Test failed${NC}\n"
#        exit 1
    fi
done