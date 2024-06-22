DROP TABLE IF EXISTS app_user CASCADE;

CREATE TABLE app_user
(
    id    serial primary key,
    name  text not null,
    email text not null unique
);

INSERT INTO app_user
VALUES ('1', 'John', 'john@gmail.com'),
       ('2', 'Wick', 'wick@gmail.com');