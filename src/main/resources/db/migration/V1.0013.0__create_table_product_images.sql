CREATE TABLE PRODUCT_IMAGES
(
    id serial primary key,
    product_id bigint not null,
    image text,
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id)
);