package cn.iocoder.yudao.module.c2c.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - C2C挂单表（买卖双方的挂单信息） Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrderRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16698")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "挂单用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5450")
    @ExcelProperty("挂单用户ID")
    private Long userId;

    @Schema(description = "挂单类型：1=BUY买入，2=SELL卖出", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("挂单类型：1=BUY买入，2=SELL卖出")
    private Integer type;

    @Schema(description = "挂单总额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("挂单总额（USDT数量）")
    private BigDecimal totalAmount;

    @Schema(description = "剩余可成交额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("剩余可成交额（USDT数量）")
    private BigDecimal remainingAmount;

    @Schema(description = "单价（CNY/USDT）", requiredMode = Schema.RequiredMode.REQUIRED, example = "16999")
    @ExcelProperty("单价（CNY/USDT）")
    private BigDecimal price;

    @Schema(description = "订单状态：0=待接单，1=部分成交，2=完成，3=取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("订单状态：0=待接单，1=部分成交，2=完成，3=取消")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单过期时间（超过此时间未成交则自动取消）")
    @ExcelProperty("订单过期时间（超过此时间未成交则自动取消）")
    private LocalDateTime expireTime;

}