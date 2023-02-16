CREATE TABLE MODELS(
                         id SERIAL PRIMARY KEY,
                         model_name varchar(225) null,
                         status int,
                         ctime timestamp DEFAULT current_timestamp,
                         mtime timestamp);
);