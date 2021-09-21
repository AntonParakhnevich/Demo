create table category
(
    id   BIGINT auto_increment PRIMARY KEY,
    name VARCHAR
);

create table user
(
    id         BIGINT auto_increment PRIMARY KEY,
    first_Name VARCHAR,
    last_Name  VARCHAR
);

create table image
(
    id      BIGINT auto_increment PRIMARY KEY,
    name    VARCHAR,
    user_id BIGINT references user (id)
);

create table image_category
(
    image_id    BIGINT references image (id),
    category_id BIGINT references category (id)
);

create table address
(
    id      BIGINT PRIMARY KEY references user (id),
    country VARCHAR,
    city    VARCHAR
);
