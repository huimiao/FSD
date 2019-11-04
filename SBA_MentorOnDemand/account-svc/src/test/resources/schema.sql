CREATE TABLE USERS
(
    id bigint IDENTITY(10001,1) primary key,
    user_name              VARCHAR(128) unique NOT NULL,
    password               VARCHAR(256) NOT NULL,
    first_name             VARCHAR(128) NOT NULL DEFAULT '',
    last_name              VARCHAR(128) NOT NULL DEFAULT '',
    contact_number         bigint  NOT NULL DEFAULT 0,
    reg_datetime           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reg_code               VARCHAR(32)  NULL     DEFAULT '',
    active                 BOOLEAN      NOT NULL DEFAULT true,
    years_of_experience    INT                   DEFAULT 0,
    linkedin_url           VARCHAR(256) NULL     DEFAULT '',
    confirmed_signup       BOOLEAN      NOT NULL DEFAULT false,
    force_reset_password   BOOLEAN               DEFAULT false,
    rest_password_datetime TIMESTAMP             DEFAULT '1900-01-01 00:00:00'
);


CREATE TABLE roles
(
    id bigint IDENTITY(10001,1) primary key,
    role VARCHAR(256) unique NOT NULL
);


CREATE TABLE user_role
(
    id bigint IDENTITY(10001,1) primary key,
    uid BIGINT NOT NULL,
    rid BIGINT NOT NULL
);


