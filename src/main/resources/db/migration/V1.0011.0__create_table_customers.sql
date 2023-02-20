CREATE TABLE CUSTOMERS
(
    id serial primary key,
    customer_name varchar(225) not null,
    gender int,
    birthday bigint,
    phone_number text not null ,
    address text,
    email varchar(225) not null,
    avatar text,
    account_id bigint not null,
    note text,
    status int,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp,
    constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts (id)
);