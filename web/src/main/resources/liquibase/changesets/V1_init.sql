CREATE TABLE users
(
    id               BIGINT UNSIGNED AUTO_INCREMENT,
    age              INTEGER               NOT NULL,
    lastname         VARCHAR(40)            NOT NULL,
    firstname        VARCHAR(20)            NOT NULL,
    surname          VARCHAR(40)            NOT NULL,
    email            VARCHAR(50)            NOT NULL,
    role             VARCHAR(255),
    PRIMARY KEY (id)
);