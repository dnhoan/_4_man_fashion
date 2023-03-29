CREATE TABLE ORDER_DETAILS
(
    id serial primary key,
    order_id bigint,
    price float,
    quantity int,
    product_detail_id int,
    constraint fk_orders FOREIGN KEY (order_id) REFERENCES orders(id),
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);