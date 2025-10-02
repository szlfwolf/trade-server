package cn.iocoder.yudao.module.c2c.controller.admin.trade.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - C2C交易表（买家接单生成的交易记录）分页 Request VO")
@Data
public class TradePageReqVO extends PageParam {

    @Schema(description = "关联的挂单ID", example = "13054")
    private Long orderId;

    @Schema(description = "接单用户ID（买家/卖家）", example = "1761")
    private Long takerId;

    @Schema(description = "交易金额（USDT数量）")
    private BigDecimal amount;

    @Schema(description = "交易状态：0=进行中，1=买家已付款，2=已放币，3=取消", example = "2")
    private Integer status;

    @Schema(description = "买家付款超时时间（未付款则交易失败）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payExpireTime;

    @Schema(description = "卖家放币超时时间（未放币则系统自动放币）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] releaseExpireTime;

    @Schema(description = "交易创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}