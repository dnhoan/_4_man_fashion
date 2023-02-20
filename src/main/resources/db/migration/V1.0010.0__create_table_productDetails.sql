CREATE TABLE PRODUCT_DETAILS
(
    id serial primary key,
    product_id bigint not null,
    size_id bigint not null ,
    color_id bigint not null ,
    stock int,
    product_detail_code varchar(50) not null,
    price float,
    color_name varchar(225) not null,
    size varchar(225) not null,
    status int,
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id),
    constraint fk_sizes FOREIGN KEY (size_id) REFERENCES sizes(id),
    constraint fk_categories FOREIGN KEY (color_id) REFERENCES colors(id)
);