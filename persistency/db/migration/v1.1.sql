ALTER TABLE `roles`
	ADD COLUMN `user_id` bigint(20) NOT NULL AFTER `principal_id`;
UPDATE roles
	JOIN
		principles
		ON principles.principal_id = roles.principal_id
	SET
		roles.user_id = principles.id;

ALTER TABLE `roles`
	ADD CONSTRAINT `FK_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `principles` (`id`);

ALTER TABLE `roles`
	DROP INDEX `FK_roles_users_id`;
ALTER TABLE `roles`
	DROP COLUMN `principal_id`,
	DROP INDEX `FK_roles_users_id`,
	DROP FOREIGN KEY `FK_roles_users_id`;