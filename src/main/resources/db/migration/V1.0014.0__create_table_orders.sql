CREATE TABLE ORDERS
(
    id serial primary key,
    order_id varchar(20) unique not null,
    customer_id bigint not null,
    order_status int,
    recipient_name varchar(225),
    recipient_phone varchar(15),
    recipient_email varchar(225),
    address text,
    shipfee float,
    goods_value float,
    checkout float,
    sale float,
    total_money float,
    delivery int,
    purchase_type int,
    note text,
    cancel_not text,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp,
    constraint fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);