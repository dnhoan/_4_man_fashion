CREATE TABLE MATERIALS
(
    id serial primary key,
    material_name varchar(50) not null,
    status int,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp
);