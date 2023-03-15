CREATE TABLE VOUCHERS
(
    id serial primary key,
    voucher_code varchar(225) unique not null,
    voucher_name varchar(225) unique not null,
    start_date timestamp,
    end_date timestamp,
    voucher_type int,
    discount float,
    quantity int,
    minimum_invoice_value float,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp,
    status int
);