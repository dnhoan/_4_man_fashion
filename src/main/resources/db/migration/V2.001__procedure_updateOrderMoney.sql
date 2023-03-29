CREATE OR REPLACE PROCEDURE updateOrderMoney(inOrderId bigint)
    language plpgsql
    as $$
    DECLARE
        goodValue float;
    BEGIN
        select sum(price * quantity) into goodValue from order_details o where o.order_id = inOrderId and o.status_order_detail = 1;
        update orders set goods_value = goodValue, total_money = goodValue +  shipfee - sale where id = inOrderId;
    END; $$