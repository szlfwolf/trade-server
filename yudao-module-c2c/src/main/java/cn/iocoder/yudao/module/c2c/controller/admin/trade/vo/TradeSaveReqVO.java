package cn.iocoder.yudao.module.c2c.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - C2C交易表（买家接单生成的交易记录）新增/修改 Request VO")
@Data
public class TradeSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8942")
    private Long id;

    @Schema(description = "关联的挂单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13054")
    @NotNull(message = "关联的挂单ID不能为空")
    private Long orderId;

    @Schema(description = "接单用户ID（买家/卖家）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1761")
    @NotNull(message = "接单用户ID（买家/卖家）不能为空")
    private Long takerId;

    @Schema(description = "交易金额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "交易金额（USDT数量）不能为空")
    private BigDecimal amount;

    @Schema(description = "交易状态：0=进行中，1=买家已付款，2=已放币，3=取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "交易状态：0=进行中，1=买家已付款，2=已放币，3=取消不能为空")
    private Integer status;

    @Schema(description = "买家付款超时时间（未付款则交易失败）")
    private LocalDateTime payExpireTime;

    @Schema(description = "卖家放币超时时间（未放币则系统自动放币）")
    private LocalDateTime releaseExpireTime;

}