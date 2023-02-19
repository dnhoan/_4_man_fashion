CREATE TABLE ACCOUNT_ROLES(
                              id SERIAL PRIMARY KEY,
                              order_id bigint,
                              voucher_id bigint,
                              constraint fk_orders FOREIGN KEY (order_id) REFERENCES orders(id),
                              constraint fk_vouchers FOREIGN KEY (voucher_id) REFERENCES vouchers(id)
);