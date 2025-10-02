package cn.iocoder.yudao.module.c2c.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - C2C交易表（买家接单生成的交易记录） Response VO")
@Data
@ExcelIgnoreUnannotated
public class TradeRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8942")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "关联的挂单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13054")
    @ExcelProperty("关联的挂单ID")
    private Long orderId;

    @Schema(description = "接单用户ID（买家/卖家）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1761")
    @ExcelProperty("接单用户ID（买家/卖家）")
    private Long takerId;

    @Schema(description = "交易金额（USDT数量）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("交易金额（USDT数量）")
    private BigDecimal amount;

    @Schema(description = "交易状态：0=进行中，1=买家已付款，2=已放币，3=取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("交易状态：0=进行中，1=买家已付款，2=已放币，3=取消")
    private Integer status;

    @Schema(description = "买家付款超时时间（未付款则交易失败）")
    @ExcelProperty("买家付款超时时间（未付款则交易失败）")
    private LocalDateTime payExpireTime;

    @Schema(description = "卖家放币超时时间（未放币则系统自动放币）")
    @ExcelProperty("卖家放币超时时间（未放币则系统自动放币）")
    private LocalDateTime releaseExpireTime;

    @Schema(description = "交易创建时间")
    @ExcelProperty("交易创建时间")
    private LocalDateTime createTime;

}