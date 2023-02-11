CREATE TABLE ACCOUNTS(
      id SERIAL PRIMARY KEY,
      email varchar(225) null,
      phone_number varchar(20) unique not null,
      password varchar(225) not null,
      status int
);

insert into ACCOUNTS(email, phone_number, password, status) values ('admin@gmail.com', '0963852741', '123', 1);
insert into ACCOUNTS(email, phone_number, password, status) values ('employee@gmail.com', '0718529632', '123', 1);
insert into ACCOUNTS(email, phone_number, password, status) values ('user@gmail.com', '0852741963', '123', 1);