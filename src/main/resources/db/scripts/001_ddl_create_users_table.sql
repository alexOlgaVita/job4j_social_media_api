create table users
(
    id serial primary key,
    name varchar not null,
    email varchar unique not null,
    password varchar not null,
    user_zone varchar not null
);