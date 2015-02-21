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
  KEY `FK_roles_users_id` (`principal_id`),
  CONSTRAINT `FK_roles_users_id` FOREIGN KEY (`principal_id`) REFERENCES `principles` (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `roles` (`user_role`, `principal_id`) VALUES
	('admin', 'jako');

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_date` DATE NULL DEFAULT NULL,
	`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_bin',
	`creator` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_categories_users_id` (`creator`),
	CONSTRAINT `FK_categories_users_id` FOREIGN KEY (`creator`) REFERENCES `principles` (`id`)
) COLLATE='utf8_bin' ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_date` DATE NULL DEFAULT NULL,
	`amount` DECIMAL(12,2) NULL DEFAULT NULL,
	`type` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_bin',
	`category_id` BIGINT(20) NULL DEFAULT NULL,
	`creator` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_payments_category_id` (`category_id`),
	CONSTRAINT `FK_payments_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
	INDEX `FK_payments_users_id` (`creator`),
	CONSTRAINT `FK_payments_users_id` FOREIGN KEY (`creator`) REFERENCES `principles` (`id`)
) COLLATE='utf8_bin' ENGINE=InnoDB;