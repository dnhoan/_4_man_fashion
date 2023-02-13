CREATE TABLE COLORS
(
    id serial primary key,
    color_code varchar(20) unique not null,
    color_name varchar(225) unique not null,
    status int,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp);