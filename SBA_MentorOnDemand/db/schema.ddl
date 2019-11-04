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


