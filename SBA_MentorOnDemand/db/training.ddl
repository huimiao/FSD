DROP DATABASE IF EXISTS `school`;
CREATE SCHEMA `school` DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `training`;
CREATE TABLE `training`
(
    `id`          BIGINT       NOT NULL auto_increment,
    `musername`   VARCHAR(128) NOT NULL,
    `susername`   VARCHAR(256) NOT NULL,
    `year_of_exp` INT     default 0,
    `start_time`  time,
    `end_time`    time,
    `start_date`  date,
    `end_date`    date,
    `fee`         DECIMAL default 100,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;



DROP TABLE IF EXISTS `training`;
CREATE TABLE `training`
(
    `id`              BIGINT       NOT NULL auto_increment,
    `cid`             BIGINT       NOT NULL,
    `uusername`       VARCHAR(128) NOT NULL,
    `status`          CHAR(1) default '1',
    `rating`          INT     default 0,
    `amount_received` DECIMAL default 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `training_UNIQUE` (`cid`, 'uusername' ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

