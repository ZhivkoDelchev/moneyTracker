DROP DATABASE IF EXISTS `money_tracker`;
CREATE DATABASE IF NOT EXISTS `money_tracker` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `money_tracker`;

DROP TABLE IF EXISTS `principles`;
CREATE TABLE IF NOT EXISTS `principles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` date DEFAULT NULL,
  `principal_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `principal_id` (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `principles` (`created_date`, `principal_id`, `password`) VALUES
	('1970-01-01', 'jako', '202cb962ac59075b964b07152d234b70');

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_role` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `principal_id` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK67A8EBD8A40D993` (`principal_id`),
  CONSTRAINT `FK67A8EBD8A40D993` FOREIGN KEY (`principal_id`) REFERENCES `principles` (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `roles` (`user_role`, `principal_id`) VALUES
	('admin', 'jako');

CREATE TABLE `categories` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_date` DATE NULL DEFAULT NULL,
	`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_bin',
	`user_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_f5hobmotrjyyffipi045ga93k` (`user_id`),
	CONSTRAINT `FK_f5hobmotrjyyffipi045ga93k` FOREIGN KEY (`user_id`) REFERENCES `principles` (`id`)
) COLLATE='utf8_bin' ENGINE=InnoDB AUTO_INCREMENT=1;
