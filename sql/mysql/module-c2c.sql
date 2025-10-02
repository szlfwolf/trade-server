-- =====================================
-- C2C 挂单表（用户发布买单/卖单的主表）
-- =====================================
drop table if exists c2c_order;
CREATE TABLE c2c_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL COMMENT '挂单用户ID',
  type TINYINT NOT NULL COMMENT '挂单类型：1=BUY买入，2=SELL卖出',
  total_amount DECIMAL(20,8) NOT NULL COMMENT '挂单总额（USDT数量）',
  remaining_amount DECIMAL(20,8) NOT NULL COMMENT '剩余可成交额（USDT数量）',
  price DECIMAL(20,8) NOT NULL COMMENT '单价（CNY/USDT）',
  status TINYINT NOT NULL COMMENT '订单状态：0=待接单，1=部分成交，2=完成，3=取消',
  expire_time DATETIME COMMENT '订单过期时间（超过此时间未成交则自动取消）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号'
) COMMENT='C2C挂单表（买卖双方的挂单信息）';


-- =====================================
-- C2C 交易表（记录买家接单后的子交易）
-- =====================================
drop table if exists c2c_trade;
CREATE TABLE c2c_trade (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  order_id BIGINT NOT NULL COMMENT '关联的挂单ID',
  taker_id BIGINT NOT NULL COMMENT '接单用户ID（买家/卖家）',
  amount DECIMAL(20,8) NOT NULL COMMENT '交易金额（USDT数量）',
  price DECIMAL(20,8) NOT NULL COMMENT '成交单价（CNY/USDT），从订单生成时固定',
  status TINYINT NOT NULL COMMENT '交易状态：0=进行中，1=买家已付款，2=已放币，3=取消',
  pay_expire_time DATETIME COMMENT '买家付款超时时间（未付款则交易失败）',
  release_expire_time DATETIME COMMENT '卖家放币超时时间（未放币则系统自动放币）',

    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号'
) COMMENT='C2C交易表（买家接单生成的交易记录）';



-- =====================================
-- C2C 用户钱包表
-- =====================================
drop table if exists c2c_wallet_account;
CREATE TABLE c2c_wallet_account (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL UNIQUE COMMENT '用户ID',
  balance DECIMAL(20,8) NOT NULL DEFAULT 0 COMMENT '可用余额',
  frozen_balance DECIMAL(20,8) NOT NULL DEFAULT 0 COMMENT '冻结余额',
  total_recharge DECIMAL(20,8) NOT NULL DEFAULT 0 COMMENT '累计充值',
  total_withdraw DECIMAL(20,8) NOT NULL DEFAULT 0 COMMENT '累计提现',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号'
) COMMENT='C2C用户钱包账户表';


-- =====================================
-- C2C 充值记录表
-- =====================================
drop table if exists c2c_wallet_recharge;
CREATE TABLE c2c_wallet_recharge (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  amount DECIMAL(20,8) NOT NULL COMMENT '充值金额',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0=待确认，1=成功，2=失败',
  channel VARCHAR(64) COMMENT '充值渠道（银行转账/USDT链上/支付网关）',
  tx_hash VARCHAR(128) COMMENT '交易流水号（链上hash或支付单号）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号'
) COMMENT='C2C钱包充值记录表';


-- =====================================
-- C2C 提现记录表
-- =====================================
drop table if exists c2c_wallet_withdraw;
CREATE TABLE c2c_wallet_withdraw (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  amount DECIMAL(20,8) NOT NULL COMMENT '提现金额',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0=审核中，1=成功，2=失败',
  channel VARCHAR(64) COMMENT '提现渠道（银行转账/USDT链上/支付网关）',
  tx_hash VARCHAR(128) COMMENT '交易流水号（链上hash或支付单号）',
 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号'
) COMMENT='C2C钱包提现记录表';

