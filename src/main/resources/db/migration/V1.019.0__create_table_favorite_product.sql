CREATE TABLE favorite_product
(
    id serial primary key,
    customer_id bigint,
    product_detail_id bigint,
    ctime timestamp DEFAULT current_timestamp,
    constraint fk_customer_favorite FOREIGN KEY (customer_id) REFERENCES customers(id),
    constraint fk_product_favorite FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);