DROP DATABASE IF EXISTS `ACCOUNT`;
CREATE SCHEMA `ACCOUNT` DEFAULT CHARACTER SET utf8;


DROP TABLE IF EXISTS `ACCOUNT`.`USERS`;
CREATE TABLE `ACCOUNT`.`USERS`
(
    `id`                     BIGINT       NOT NULL auto_increment,
    `user_name`              VARCHAR(128) NOT NULL,
    `password`               VARCHAR(256) NOT NULL,
    `first_name`             VARCHAR(128) NOT NULL DEFAULT '',
    `last_name`              VARCHAR(128) NOT NULL DEFAULT '',
    `contact_number`         VARCHAR(32)  NOT NULL DEFAULT '000000',
    `reg_datetime`           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `reg_code`               VARCHAR(32)  NULL     DEFAULT '',
    `active`                 BOOLEAN      NOT NULL DEFAULT true,
    `years_of_experience`    INT                   DEFAULT 0,
    `linkedin_url`           VARCHAR(256) NULL     DEFAULT '',
    `confirmed_signup`       BOOLEAN      NOT NULL DEFAULT false,
    `force_reset_password`   BOOLEAN               DEFAULT false,
    `rest_password_datetime` DATE                  DEFAULT '1900-01-01 00:00:00',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `username_UNIQUE` (`user_name` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
alter table `ACCOUNT`.`USERS`
    AUTO_INCREMENT = 10000;

DROP TABLE IF EXISTS `ACCOUNT`.`roleS`;
CREATE TABLE `ACCOUNT`.`roles`
(
    `id`   BIGINT       NOT NULL auto_increment,
    `role` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `role_UNIQUE` (`role` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
alter table `ACCOUNT`.`roles`
    AUTO_INCREMENT = 10000;

DROP TABLE IF EXISTS `ACCOUNT`.`user_role`;
CREATE TABLE `ACCOUNT`.`user_role`
(
    `id`  BIGINT NOT NULL auto_increment,
    `uid` BIGINT NOT NULL,
    `rid` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
alter table `ACCOUNT`.`user_role`
    AUTO_INCREMENT = 10000;


INSERT INTO `account`.`users`(`user_name`, `password`, `first_name`, `last_name`, `contact_number`, `reg_datetime`,
                              `active`, `years_of_experience`, `linkedin_url`, `confirmed_signup`)
VALUES ('huimiao@cn.ibm.com', '123456', 'Hui', 'Miao', '123456789', current_timestamp, 1, 10, '', 1);


INSERT INTO `account`.`roles`(`role`)
values('ROLE_USER');
INSERT INTO `account`.`roles`(`role`)
values('ROLE_MENTOR');
INSERT INTO `account`.`roles`(`role`)
values('ROLE_ADMIN');