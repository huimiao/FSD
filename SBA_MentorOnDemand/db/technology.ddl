DROP DATABASE IF EXISTS `technology`;
CREATE SCHEMA `technology` DEFAULT CHARACTER SET utf8;

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



INSERT INTO skills(name, toc, prerequisites) values('Java', '-', '-');
INSERT INTO skills(name, toc, prerequisites) values('React', '-', '-');
INSERT INTO skills(name, toc, prerequisites) values('AngularJS', '-', '-');
