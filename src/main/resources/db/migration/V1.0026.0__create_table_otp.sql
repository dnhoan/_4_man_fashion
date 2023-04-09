CREATE TABLE OTP
(
    id serial primary key,
    otp_code varchar(10),
    email_account varchar(100),
    phoneNumber_account varchar(100),
    isUse_At bigint,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp NULL,
    status int
);