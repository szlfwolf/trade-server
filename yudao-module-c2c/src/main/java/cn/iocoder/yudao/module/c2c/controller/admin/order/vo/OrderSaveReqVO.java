package cn.iocoder.yudao.module.c2c.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - C2C挂单表（买卖双方的挂单信息）新增/修改 Request VO")
@Data
public class OrderSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16698")
    private Long id;

    @Schema(description = "挂单用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5450")
    @NotNull(message = "挂单用户ID不能为空")
    private Long userId;

    @Schema(description = "挂单类型：1=BUY买入，2=SELL卖出", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "挂单类型：1=BUY买入，2=SELL卖出不能为空")
    private Integer type;

    @Schema(description = "挂单总额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "挂单总额（USDT数量）不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "剩余可成交额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "剩余可成交额（USDT数量）不能为空")
    private BigDecimal remainingAmount;

    @Schema(description = "单价（CNY/USDT）", requiredMode = Schema.RequiredMode.REQUIRED, example = "16999")
    @NotNull(message = "单价（CNY/USDT）不能为空")
    private BigDecimal price;

    @Schema(description = "订单状态：0=待接单，1=部分成交，2=完成，3=取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "订单状态：0=待接单，1=部分成交，2=完成，3=取消不能为空")
    private Integer status;

    @Schema(description = "订单过期时间（超过此时间未成交则自动取消）")
    private LocalDateTime expireTime;

}