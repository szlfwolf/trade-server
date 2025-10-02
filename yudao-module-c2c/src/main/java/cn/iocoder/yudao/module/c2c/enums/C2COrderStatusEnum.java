package cn.iocoder.yudao.module.c2c.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum C2COrderStatusEnum {
    PENDING(0, "待接单"),
    PARTIAL(1, "部分成交"),
    FILLED(2, "完成"),
    CANCELLED(3, "取消");
    private final Integer code;
    private final String desc;
}

