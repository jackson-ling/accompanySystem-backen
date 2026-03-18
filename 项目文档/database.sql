-- =====================================================
-- 陪护系统数据库建表脚本
-- 适用于 MySQL 8.0+
-- =====================================================

-- ----------------------------
-- 创建数据库
-- ----------------------------
DROP DATABASE IF EXISTS `accompany_db`;
CREATE DATABASE `accompany_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `accompany_db`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额',
    `user_type` TINYINT DEFAULT 1 COMMENT '用户类型: 1-普通用户, 2-陪诊师, 3-管理员',
    `gender` TINYINT DEFAULT NULL COMMENT '性别: 0-未知, 1-男, 2-女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_user_type` (`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 就诊人表
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(50) NOT NULL COMMENT '就诊人姓名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `id_card` VARCHAR(20) DEFAULT NULL COMMENT '身份证号',
    `gender` TINYINT DEFAULT NULL COMMENT '性别: 0-未知, 1-男, 2-女',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '地址',
    `relationship` VARCHAR(20) DEFAULT NULL COMMENT '与用户关系(本人/父母/配偶/子女/其他)',
    `medical_card_no` VARCHAR(50) DEFAULT NULL COMMENT '就诊卡号',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认: 0-否, 1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='就诊人表';

-- ----------------------------
-- 3. 陪诊师信息表
-- ----------------------------
DROP TABLE IF EXISTS `companion_profile`;
CREATE TABLE `companion_profile` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '关联用户ID',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `score` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分(0-5)',
    `orders` INT DEFAULT 0 COMMENT '总接单数',
    `comments` INT DEFAULT 0 COMMENT '评论数',
    `certified` TINYINT DEFAULT 0 COMMENT '是否认证: 0-否, 1-是',
    `qualified` TINYINT DEFAULT 0 COMMENT '是否有资质: 0-否, 1-是',
    `experience` VARCHAR(50) DEFAULT NULL COMMENT '工作年限',
    `tags` JSON DEFAULT NULL COMMENT '标签数组',
    `services` JSON DEFAULT NULL COMMENT '服务类别ID数组',
    `intro` TEXT DEFAULT NULL COMMENT '个人简介',
    `is_online` TINYINT DEFAULT 0 COMMENT '是否在线: 0-否, 1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='陪诊师信息表';

-- 统一使用 score 字段命名

-- ----------------------------
-- 4. 服务分类表
-- ----------------------------
DROP TABLE IF EXISTS `service_category`;
CREATE TABLE `service_category` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `icon` VARCHAR(500) DEFAULT NULL COMMENT '图标',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务分类表';

-- ----------------------------
-- 5. 服务项目表
-- ----------------------------
DROP TABLE IF EXISTS `service_item`;
CREATE TABLE `service_item` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
    `name` VARCHAR(100) NOT NULL COMMENT '服务名称',
    `description` TEXT DEFAULT NULL COMMENT '服务描述',
    `features` JSON DEFAULT NULL COMMENT '服务特点(JSON数组)',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `type` VARCHAR(50) DEFAULT NULL COMMENT '服务类型: companion-陪诊, agency-代办',
    `sales` INT DEFAULT 0 COMMENT '销量',
    `image` VARCHAR(500) DEFAULT NULL COMMENT '图片',
    `cover` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
    `duration` VARCHAR(50) DEFAULT NULL COMMENT '服务时长',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-下架, 1-上架',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_category_id` (`category_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务项目表';

-- ----------------------------
-- 6. 订单表
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号(唯一)',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `companion_id` BIGINT DEFAULT NULL COMMENT '陪诊师ID(可为空,抢单前为空)',
    `patient_id` BIGINT NOT NULL COMMENT '就诊人ID',
    `service_id` BIGINT NOT NULL COMMENT '服务项目ID',
    `service_name` VARCHAR(100) NOT NULL COMMENT '服务名称',
    `service_image` VARCHAR(500) DEFAULT NULL COMMENT '服务图片',
    `hospital` VARCHAR(100) DEFAULT NULL COMMENT '医院',
    `department` VARCHAR(50) DEFAULT NULL COMMENT '科室',
    `appointment_time` DATETIME DEFAULT NULL COMMENT '预约时间',
    `pickup_option` TINYINT DEFAULT 1 COMMENT '接人方式: 1-医院门口, 2-指定地点',
    `pickup_address` VARCHAR(255) DEFAULT NULL COMMENT '接人地址',
    `remarks` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    `actual_amount` DECIMAL(10,2) DEFAULT NULL COMMENT '实付金额',
    `pay_method` TINYINT DEFAULT NULL COMMENT '支付方式: 1-微信, 2-支付宝, 3-余额',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `status` TINYINT DEFAULT 0 COMMENT '订单状态: 0-待支付, 1-待接单, 2-已接单, 3-已完成, 4-已取消, 5-退款中, 6-已退款, 7-服务中',
    `refund_reason` VARCHAR(500) DEFAULT NULL COMMENT '退款原因',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退款时间',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_companion_id` (`companion_id`),
    INDEX `idx_order_no` (`order_no`),
    INDEX `idx_status` (`status`),
    INDEX `idx_appointment_time` (`appointment_time`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- 7. 订单评价表
-- ----------------------------
DROP TABLE IF EXISTS `order_review`;
CREATE TABLE `order_review` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `order_id` BIGINT NOT NULL UNIQUE COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `companion_id` BIGINT NOT NULL COMMENT '陪诊师ID',
    `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
    `user_avatar` VARCHAR(500) DEFAULT NULL COMMENT '用户头像',
    `score` DECIMAL(3,2) NOT NULL COMMENT '评分(1-5)',
    `content` TEXT DEFAULT NULL COMMENT '评价内容',
    `images` JSON DEFAULT NULL COMMENT '图片(JSON数组)',
    `time` DATETIME DEFAULT NULL COMMENT '评价时间',
    INDEX `idx_order_id` (`order_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_companion_id` (`companion_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单评价表';

-- ----------------------------
-- 8. 消息会话表
-- ----------------------------
DROP TABLE IF EXISTS `message_conversation`;
CREATE TABLE `message_conversation` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '会话类型: customer_service-客服, companion-陪诊师',
    `target_id` BIGINT DEFAULT NULL COMMENT '目标ID(客服ID或陪诊师ID)',
    `name` VARCHAR(50) DEFAULT NULL COMMENT '名称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `last_message` VARCHAR(255) DEFAULT NULL COMMENT '最后一条消息',
    `last_message_time` DATETIME DEFAULT NULL COMMENT '最后消息时间',
    `unread_count` INT DEFAULT 0 COMMENT '未读数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_target_id` (`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息会话表';

-- ----------------------------
-- 9. 聊天消息表
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `conversation_id` BIGINT NOT NULL COMMENT '会话ID',
    `user_id` BIGINT NOT NULL COMMENT '发送者ID',
    `text` TEXT NOT NULL COMMENT '消息内容',
    `is_me` TINYINT DEFAULT 0 COMMENT '是否我发送: 0-否, 1-是',
    `type` VARCHAR(20) DEFAULT 'text' COMMENT '消息类型: text/image/voice',
    `time` DATETIME DEFAULT NULL COMMENT '发送时间',
    INDEX `idx_conversation_id` (`conversation_id`),
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';

-- ----------------------------
-- 10. 充值记录表
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '充值订单号',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '充值金额',
    `method` VARCHAR(20) DEFAULT NULL COMMENT '支付方式: wechat-微信, alipay-支付宝',
    `pay_status` TINYINT DEFAULT 0 COMMENT '支付状态: 0-待支付, 1-支付成功, 2-支付失败',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表';

-- ----------------------------
-- 11. 消费记录表
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `type` VARCHAR(20) DEFAULT NULL COMMENT '类型: recharge-充值, consume-消费, refund-退款',
    `title` VARCHAR(100) DEFAULT NULL COMMENT '标题',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额(正数)',
    `balance` DECIMAL(10,2) NOT NULL COMMENT '余额',
    `order_no` VARCHAR(32) DEFAULT NULL COMMENT '关联订单号',
    `time` DATETIME DEFAULT NULL COMMENT '时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消费记录表';

-- ----------------------------
-- 12. 陪诊师收入记录表
-- ----------------------------
DROP TABLE IF EXISTS `companion_income`;
CREATE TABLE `companion_income` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '陪诊师用户ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(32) DEFAULT NULL COMMENT '订单编号',
    `service_name` VARCHAR(100) DEFAULT NULL COMMENT '服务名称',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '收入金额',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending-待结算, completed-已结算, cancelled-已取消',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `settle_time` DATETIME DEFAULT NULL COMMENT '结算时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='陪诊师收入记录表';

-- ----------------------------
-- 13. 收藏表
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '类型: companion-陪诊师, service-服务',
    `item_id` BIGINT NOT NULL COMMENT '收藏项ID',
    `name` VARCHAR(50) DEFAULT NULL COMMENT '名称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
    `time` DATETIME DEFAULT NULL COMMENT '收藏时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_item_id_type` (`item_id`, `type`),
    UNIQUE KEY `uk_user_item_type` (`user_id`, `item_id`, `type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- ----------------------------
-- 14. 反馈表
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `content` TEXT NOT NULL COMMENT '反馈内容',
    `images` JSON DEFAULT NULL COMMENT '图片(JSON数组)',
    `contact` VARCHAR(50) DEFAULT NULL COMMENT '联系方式',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态: pending-待处理, processed-已处理',
    `reply` TEXT DEFAULT NULL COMMENT '回复内容',
    `reply_time` DATETIME DEFAULT NULL COMMENT '回复时间',
    `time` DATETIME DEFAULT NULL COMMENT '提交时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='反馈表';

-- ----------------------------
-- 15. 医院表
-- ----------------------------
DROP TABLE IF EXISTS `hospital`;
CREATE TABLE `hospital` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(100) NOT NULL COMMENT '医院名称',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '地址',
    `level` VARCHAR(20) DEFAULT NULL COMMENT '医院等级: 三甲/三乙/二甲/二乙/一级',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院表';

-- ----------------------------
-- 16. 科室表
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '科室名称',
    `hospital_id` BIGINT DEFAULT NULL COMMENT '医院ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_hospital_id` (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科室表';

-- ----------------------------
-- 17. 陪诊师统计表
-- ----------------------------
DROP TABLE IF EXISTS `companion_statistics`;
CREATE TABLE `companion_statistics` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '陪诊师用户ID',
    `today_income` DECIMAL(10,2) DEFAULT 0.00 COMMENT '今日收入',
    `today_orders` INT DEFAULT 0 COMMENT '今日订单数',
    `month_income` DECIMAL(10,2) DEFAULT 0.00 COMMENT '本月收入',
    `month_orders` INT DEFAULT 0 COMMENT '本月订单数',
    `total_income` DECIMAL(10,2) DEFAULT 0.00 COMMENT '总收入',
    `total_orders` INT DEFAULT 0 COMMENT '总订单数',
    `score` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `followers` INT DEFAULT 0 COMMENT '粉丝数',
    `work_days` INT DEFAULT 0 COMMENT '工作天数',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='陪诊师统计表';

-- ----------------------------
-- 18. AI聊天记录表
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_history`;
CREATE TABLE `ai_chat_history` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `message` TEXT NOT NULL COMMENT '用户消息',
    `reply` TEXT NOT NULL COMMENT 'AI回复',
    `suggestions` JSON DEFAULT NULL COMMENT '建议问题(JSON数组)',
    `time` DATETIME DEFAULT NULL COMMENT '聊天时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天记录表';

-- ----------------------------
-- 添加外键约束
-- ----------------------------

-- 就诊人表外键
ALTER TABLE `patient`
ADD CONSTRAINT `fk_patient_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

-- 陪诊师信息表外键
ALTER TABLE `companion_profile`
ADD CONSTRAINT `fk_companion_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

-- 订单表外键
ALTER TABLE `orders`
ADD CONSTRAINT `fk_orders_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
ADD CONSTRAINT `fk_orders_companion` FOREIGN KEY (`companion_id`) REFERENCES `companion_profile`(`id`) ON DELETE SET NULL,
ADD CONSTRAINT `fk_orders_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient`(`id`),
ADD CONSTRAINT `fk_orders_service` FOREIGN KEY (`service_id`) REFERENCES `service_item`(`id`);

-- 订单评价表外键
ALTER TABLE `order_review`
ADD CONSTRAINT `fk_review_order` FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
ADD CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
ADD CONSTRAINT `fk_review_companion` FOREIGN KEY (`companion_id`) REFERENCES `companion_profile`(`id`);

-- 消息会话表外键
ALTER TABLE `message_conversation`
ADD CONSTRAINT `fk_conversation_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

-- 聊天消息表外键
ALTER TABLE `chat_message`
ADD CONSTRAINT `fk_message_conversation` FOREIGN KEY (`conversation_id`) 
REFERENCES `message_conversation`(`id`) ON DELETE CASCADE,
ADD CONSTRAINT `fk_message_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`);

-- 充值记录表外键
ALTER TABLE `recharge_record`
ADD CONSTRAINT `fk_recharge_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`);

-- 消费记录表外键
ALTER TABLE `transaction_record`
ADD CONSTRAINT `fk_transaction_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`);

-- 陪诊师收入记录表外键
ALTER TABLE `companion_income`
ADD CONSTRAINT `fk_income_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
ADD CONSTRAINT `fk_income_order` FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`);

-- 收藏表外键
ALTER TABLE `favorite`
ADD CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

-- 反馈表外键
ALTER TABLE `feedback`
ADD CONSTRAINT `fk_feedback_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`);

-- 科室表外键
ALTER TABLE `department`
ADD CONSTRAINT `fk_department_hospital` FOREIGN KEY (`hospital_id`) REFERENCES `hospital`(`id`);

-- 陪诊师统计表外键
ALTER TABLE `companion_statistics`
ADD CONSTRAINT `fk_statistics_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

-- AI聊天记录表外键
ALTER TABLE `ai_chat_history`
ADD CONSTRAINT `fk_ai_chat_user` FOREIGN KEY (`user_id`) 
REFERENCES `sys_user`(`id`) ON DELETE CASCADE;

SET FOREIGN_KEY_CHECKS = 1;
