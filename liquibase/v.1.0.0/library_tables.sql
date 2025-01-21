--liquibase formatted sql

--changeset Eugene_Elantsev:1
CREATE TABLE author
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(100),
    description VARCHAR(255)
);

--changeset Eugene_Elantsev:2
CREATE TABLE book
(
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    date_of_publication DATE,
    page_count          INT,
    description         VARCHAR(255),
    number_of_units     INT
);

--changeset Eugene_Elantsev:3
CREATE TABLE book_author
(
    id_book   BIGINT NOT NULL REFERENCES book (id),
    id_author BIGINT NOT NULL REFERENCES author (id),

    PRIMARY KEY (id_book, id_author)
);

--changeset Eugene_Elantsev:4
CREATE TABLE reader
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    patronymic  VARCHAR(255),
    birth_date  DATE,
    gender      VARCHAR(255),
    description VARCHAR(255)
);

--changeset Eugene_Elantsev:5
CREATE TABLE issue
(
    id          BIGSERIAL PRIMARY KEY,
    book_id     BIGINT NOT NULL REFERENCES book (id),
    reader_id   BIGINT NOT NULL REFERENCES reader (id),
    issued_at   DATE,
    returned_at DATE
);

