DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2021-02-20 17:24:00', 'обед', 500),
       (100000, '2021-02-20 23:24:00', 'ужин', 700),
       (100000, '2021-02-20 10:24:00', 'завтрак', 300),
       (100001, '2021-02-21 17:24:00', 'обед', 500),
       (100001, '2021-02-21 23:24:00', 'ужин', 700),
       (100001, '2021-02-21 10:24:00', 'завтрак', 300)
