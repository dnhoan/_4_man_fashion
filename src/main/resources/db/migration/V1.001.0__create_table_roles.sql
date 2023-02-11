CREATE TABLE ROLES(
     id SERIAL PRIMARY KEY,
     role_name varchar(50) unique not null
);

insert into ROLES(role_name) values ('ROLE_ADMIN');
insert into ROLES(role_name) values ('ROLE_EMPLOYEE');
insert into ROLES(role_name) values ('ROLE_USER');
