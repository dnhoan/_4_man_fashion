CREATE TABLE PRODUCTS
(
    id serial primary key,
    product_name varchar(225) not null,
    product_id bigint not null,
    description text,
    details text,
    material_id bigint not null,
    category_id bigint not null,
    model_id bigint not null,
    gender int,
    material_name varchar(225) not null,
    category_name varchar(225) not null,
    model_name varchar(225) not null,
    status int,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp,
    constraint fk_materials FOREIGN KEY (material_id) REFERENCES materials(id),
    constraint fk_categories FOREIGN KEY (category_id) REFERENCES categories(id),
    constraint fk_models FOREIGN KEY (model_id) REFERENCES models(id)
);