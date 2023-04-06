


register-member "sasha" "sashatouille@rust.crab" "posix sorceress" "28/03/2000"
add-shop "pizzaLand"  "20 rue des lucioles, biot"
add-product $shopId \"pizza 4 fromage\" 12 50 0

add-product $shopId \"marguerite\" 10 40 0

add-gift $shopId 20 \"mini pizza\" REGULAR
add-gift $shopId 30 \"regular pizza\" VFP


create-purchase-with-cash $memberId $shopId --itemIds $productId1 $productId2 --itemQuantities 2 1
create-purchase-with-cash $memberId $shopId --itemIds $productId2 --itemQuantities 1
create-purchase-with-cash $memberId $shopId --itemIds $productId1 --itemQuantities 3
create-purchase-with-cash $memberId $shopId --itemIds $productId2 --itemQuantities 5




