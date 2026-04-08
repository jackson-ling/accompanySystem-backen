-- 删除 favorite 表的 type 字段及相关索引
-- 执行时间：2026-03-18

-- 1. 删除联合唯一索引 (如果存在)
ALTER TABLE `favorite` DROP INDEX `uk_user_item_type`;

-- 2. 删除联合索引 (如果存在)
ALTER TABLE `favorite` DROP INDEX `idx_item_id_type`;

-- 3. 删除 type 字段
ALTER TABLE `favorite` DROP COLUMN `type`;

-- 4. 创建新的联合唯一索引 (user_id, item_id)
ALTER TABLE `favorite` ADD UNIQUE KEY `uk_user_item` (`user_id`, `item_id`);
