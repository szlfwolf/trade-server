package cn.iocoder.yudao.module.c2c.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum C2CTradeStatusEnum {
    ONGOING(0, "进行中"),
    PAID(1, "买家已付款"),
    RELEASED(2, "已放币"),
    CANCELLED(3, "取消");
    private final Integer code;
    private final String desc;
}
