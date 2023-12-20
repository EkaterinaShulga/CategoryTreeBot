-- liquibase formatted sql

--changeset shulga:1

CREATE TABLE IF NOT EXISTS categories
(
    id       INTEGER  primary key,
    title       TEXT

);

CREATE TABLE IF NOT EXISTS sub_categories
(
    id          INTEGER  primary key,
    title       TEXT,
    category_id     INTEGER references categories (id) on delete cascade


);


