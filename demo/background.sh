#!/bin/bash

function runCommand() {
  echo "$1" | socat - exec:"docker attach cli",pty
  sleep 1
  docker logs cli --tail=1
}


member=$(runCommand 'register-member "sasha" "sashatouille@rust.crab" "posix sorceress" "28/03/2000"')
memberId=$(echo "$member" |  grep -oP '(?<=id=)(.*)(?=, name)' | head -n -1)
shop=$(runCommand 'add-shop "pizzaLand"  "20 rue des lucioles, biot"')
shopId=$(echo "$shop" | grep -oP '(?<=id : )(.*)(?=, name)' | head -n -1 )
echo "shopId : $shopId"
echo "add-product $shopId \"pizza 4 fromage\" 12 50 0"
product1=$(runCommand "add-product $shopId \"pizza 4 fromage\" 12 50 0")
product2=$(runCommand "add-product $shopId \"marguerite\" 10 40 0")
productId1=$(echo "$product1" | grep -oP '(?<=id : )(.*)(?= Name)' | head -n -1 )
productId2=$(echo "$product2" | grep -oP '(?<=id : )(.*)(?= Name)' | head -n -1 )

gift1=$(runCommand "add-gift $shopId 20 \"mini pizza\" REGULAR")
gift2=$(runCommand "add-gift $shopId 30 \"regular pizza\" VFP")

gift1Id=$(echo "$gift1" | grep -oP '(?<=giftId=)(.*)(?=, shop)' | head -n -1 )
gift2Id=$(echo "$gift2" | grep -oP '(?<=giftId=)(.*)(?=, shop)' | head -n -1 )

runCommand "create-purchase-with-cash $memberId $shopId --itemIds $productId1 $productId2 --itemQuantities 2 1"
runCommand "create-purchase-with-cash $memberId $shopId --itemIds $productId2 --itemQuantities 1"
runCommand "create-purchase-with-cash $memberId $shopId --itemIds $productId1 --itemQuantities 3"
runCommand "create-purchase-with-cash $memberId $shopId --itemIds $productId2 --itemQuantities 5"

echo "memberId : $memberId"
echo "shopId : $shopId"
echo "productId1 : $productId1"
echo "productId2 : $productId2"
echo "gift1Id : $gift1Id"
echo "gift2Id : $gift2Id"


