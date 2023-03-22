CREATE TABLE LOG_ORDER_STATUS
(
    id serial primary key,
    order_id bigint ,
    times timestamp DEFAULT current_timestamp,
    user_change varchar(255),
    note text,
    current_status int,
    new_status int default 1,
    account_id bigint,
    constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id),
    constraint fk_orders FOREIGN KEY (order_id) REFERENCES orders(id)
);