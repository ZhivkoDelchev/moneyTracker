CREATE TABLE `account` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL COLLATE 'utf8_bin',
	`initial_amount` DECIMAL(12,2) NOT NULL,
	`creator` BIGINT(20) NOT NULL,
	`created_date` DATETIME NULL DEFAULT NULL,
	`last_edit_date` DATETIME DEFAULT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_account_users_id` FOREIGN KEY (`creator`) REFERENCES `principles` (`id`),
	UNIQUE INDEX `name` (`name`, `creator`)
) COLLATE='utf8_bin' ENGINE=InnoDB AUTO_INCREMENT=1;

INSERT INTO account(name, initial_amount, creator, created_date) SELECT "Default", 0, id, created_date FROM principles;

ALTER TABLE `payments`
	ADD COLUMN `account` BIGINT(20) NULL `creator`,
	ADD CONSTRAINT `FK_payments_accounts_id` FOREIGN KEY (`account`) REFERENCES `account` (`id`);

UPDATE payments p
LEFT JOIN account a ON
    a.creator = p.creator
SET
    p.account = a.id;
