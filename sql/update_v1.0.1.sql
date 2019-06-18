USE `loadstar_user_dev`;
ALTER TABLE `passbook`
  ADD COLUMN `create_time` DATETIME NULL AFTER `link`,
  ADD COLUMN `update_time` DATETIME NULL AFTER `create_time`,
  ADD COLUMN `is_delete` BIT DEFAULT 0  NULL AFTER `update_time`;

ALTER TABLE `plugin`
    ADD COLUMN `create_time` DATETIME NULL,
    ADD COLUMN `update_time` DATETIME NULL AFTER `create_time`;

ALTER TABLE `tip`
    ADD COLUMN `create_time` DATETIME NULL,
    ADD COLUMN `update_time` DATETIME NULL AFTER `create_time`;


ALTER TABLE `user_plugin`
    ADD COLUMN `create_time` DATETIME NULL,
    ADD COLUMN `update_time` DATETIME NULL AFTER `create_time`;

USE `loadstar_link_dev`;

