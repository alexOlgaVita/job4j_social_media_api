create table posts
(
    id serial primary key,
    name VARCHAR not null,
    description varchar not null,
    created timestamp not null,
    user_id int  references users(id)
);