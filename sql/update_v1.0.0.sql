USE `loadstar_user_dev`;
ALTER TABLE `passbook`
  ADD COLUMN `create_time` DATETIME NULL AFTER `link`,
  ADD COLUMN `update_time` DATETIME NULL AFTER `create_time`,
  ADD COLUMN `is_delete` BIT DEFAULT 0  NULL AFTER `update_time`;
