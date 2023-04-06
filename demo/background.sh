#!/bin/bash

function runCommand() {
  echo "$1" | socat - exec:"docker attach cli",pty
  sleep 1
  docker logs cli --tail=1
}

member=$(runCommand 'register-member "sasha" "sashatouille@rust.crab" "posix sorceress" "28/03/2000"')
shop=$(runCommand 'add-shop "pizzaLand"  "20 rue des lucioles, biot"')
shopId=$(echo "$shop" | grep -oP '(?<=id : )(.*)(?=, name)' | head -n -1 )
echo "shopId : $shopId"
echo "add-product $shopId \"pizza 4 fromage\" 12 50 0"
product1=$(runCommand "add-product $shopId \"pizza 4 fromage\" 12 50 0")
product2=$(runCommand "add-product $shopId \"marguerite\" 10 40 0")


