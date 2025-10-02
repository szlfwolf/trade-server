package cn.iocoder.yudao.module.c2c.controller.admin.order.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - C2C挂单表（买卖双方的挂单信息）分页 Request VO")
@Data
public class OrderPageReqVO extends PageParam {

    @Schema(description = "挂单用户ID", example = "5450")
    private Long userId;

    @Schema(description = "挂单类型：1=BUY买入，2=SELL卖出", example = "2")
    private Integer type;

    @Schema(description = "挂单总额（USDT数量）")
    private BigDecimal totalAmount;

    @Schema(description = "剩余可成交额（USDT数量）")
    private BigDecimal remainingAmount;

    @Schema(description = "单价（CNY/USDT）", example = "16999")
    private BigDecimal price;

    @Schema(description = "订单状态：0=待接单，1=部分成交，2=完成，3=取消", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "订单过期时间（超过此时间未成交则自动取消）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expireTime;

}