CREATE TABLE CATEGORIES
(
    id serial primary key,
    category_name varchar(255) not null,
    status int,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp
);