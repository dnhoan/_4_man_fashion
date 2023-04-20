ALTER TABLE accounts DROP CONSTRAINT accounts_phone_number_key;
ALTER TABLE accounts ALTER COLUMN phone_number type varchar(20);
ALTER TABLE accounts ALTER COLUMN email type varchar(225);
