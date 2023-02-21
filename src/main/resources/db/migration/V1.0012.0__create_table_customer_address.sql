CREATE TABLE CUSTOMER_ADDRESS
(
    id serial primary key,
    customer_id bigint not null,
    ward_code int,
    ward varchar(225),
    district_code int,
    district varchar(225),
    province_code int,
    province varchar(225),
    detail varchar(225),
    status int,
    constraint fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);