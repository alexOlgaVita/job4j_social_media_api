create table posts_photos
(
    id serial primary key,
    post_id int references posts(id),
    photo_id int  references photos(id)
);