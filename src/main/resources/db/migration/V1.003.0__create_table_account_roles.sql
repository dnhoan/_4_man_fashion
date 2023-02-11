CREATE TABLE ACCOUNT_ROLES(
     id SERIAL PRIMARY KEY,
     account_id bigint,
     role_id bigint,
     constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id),
     constraint fk_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);

insert into ACCOUNT_ROLES(account_id, role_id) values (1, 1);
insert into ACCOUNT_ROLES(account_id, role_id) values (2, 2);
insert into ACCOUNT_ROLES(account_id, role_id) values (3, 3);