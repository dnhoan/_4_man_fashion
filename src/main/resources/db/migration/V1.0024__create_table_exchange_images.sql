CREATE TABLE EXCHANGE_IMAGES
(
    id serial primary key,
    exchange_id bigint,
    image text,
    constraint fk_exchanges FOREIGN KEY (exchange_id) REFERENCES exchanges(id)
);