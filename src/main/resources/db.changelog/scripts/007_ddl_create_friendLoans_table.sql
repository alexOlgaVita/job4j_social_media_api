create table friendLoans
(
    id serial primary key,
    user_id1 int references users(id),
    user_id2 int  references users(id)
);