CREATE TABLE photos (
   id serial PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   path varchar not null unique
);