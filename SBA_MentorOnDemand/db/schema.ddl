DROP TABLE IF EXISTS `skillS`;
CREATE TABLE `skillS`
(
    `id`            BIGINT       NOT NULL auto_increment,
    `name`          VARCHAR(256) NOT NULL,
    `toc`           VARCHAR(256) NOT NULL,
    `prerequisites` VARCHAR(512) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `skill_UNIQUE` (`name` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `mentor_skill`;
CREATE TABLE `mentor_skill`
(
    `id`                  BIGINT        NOT NULL auto_increment,
    `mid`                 BIGINT        NOT NULL,
    `sid`                 BIGINT        NOT NULL,
    `slf_rating`          TINYINT       NOT NULL,
    `year_of_exp`         TINYINT       NOT NULL,
    `trainings_delivered` INT           NOT NULL,
    `facilities_offered`  VARCHAR(1024) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `mentor_skill_UNIQUE` (`mid`, `sid` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `mentor_calendar`;
CREATE TABLE `mentor_calendar`
(
    `id`         BIGINT NOT NULL auto_increment,
    `mid`        BIGINT NOT NULL,
    `start_time` time   NOT NULL,
    `end_time`   time   NOT NULL,
    `start_date` date   NOT NULL,
    `end_date`   date   NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `trainingS`;
CREATE TABLE `trainingS`
(
    `id`              BIGINT     NOT NULL auto_increment,
    `uid`             BIGINT     NOT NULL,
    `mid`             BIGINT     NOT NULL,
    `sid`             BIGINT     NOT NULL,
    `status`          CHAR(1)    NOT NULL,
    `progress`        TINYINT(1) NOT NULL,
    `rating`          TINYINT    NOT NULL,
    `start_time`      TIME       NOT NULL,
    `end_time`        TIME       NOT NULL,
    `start_date`      DATE       NOT NULL,
    `end_date`        DATE       NOT NULL,
    `amount_received` DECIMAL    NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS `paymentS`;
CREATE TABLE `paymentS`
(
    `id`            BIGINT      NOT NULL auto_increment,
    `mid`           BIGINT      NOT NULL,
    `tid`           BIGINT      NOT NULL,
    `sid`           BIGINT      NOT NULL,
    `txn_type`      VARCHAR(64) NOT NULL,
    `pay_timestamp` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `amount`        DECIMAL     NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


