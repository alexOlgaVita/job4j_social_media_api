create table participates
(
    id serial primary key,
    user_id int references users(id),
    post_id int  references posts(id)
);