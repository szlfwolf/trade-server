package cn.iocoder.yudao.module.c2c.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * yudao-module-c2c 错误码枚举类
 *
 * yudao-module-c2c 系统，使用 2-001-000-000 段
 */
public interface ErrorCodeConstants {
    // ========== C2C挂单表（买卖双方的挂单信息） 2-001-000-001 ==========
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(2-001-000-001, "C2C挂单表（买卖双方的挂单信息）不存在");


    // ========== C2C交易表（买家接单生成的交易记录） 2-001-001-001 ==========
    ErrorCode TRADE_NOT_EXISTS = new ErrorCode(2-001-001-001, "C2C交易表（买家接单生成的交易记录）不存在");
    ErrorCode TRADE_ORDER_STATUS_NOT_ALLOWED = new ErrorCode(2-001-001-002, "订单不可接单");
    ErrorCode TRADE_ORDER_REMAIN_AMOUNT_NOT_ENOUGH = new ErrorCode(2-001-001-003, "挂单余额不足");


    // ========== C2C用户钱包账户 2-001-002-001 ==========
    ErrorCode WALLET_ACCOUNT_NOT_EXISTS = new ErrorCode(2-001-002-001, "C2C用户钱包账户不存在");
    ErrorCode WALLET_ACCOUNT_BALANCE_NOT_ENOUGH = new ErrorCode(2-001-002-001, "余额不足");

}