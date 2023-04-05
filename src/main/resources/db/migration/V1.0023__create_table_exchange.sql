CREATE TABLE EXCHANGES
(
    id serial primary key,
    reason text,
    note text,
    ctime timestamp DEFAULT current_timestamp
);