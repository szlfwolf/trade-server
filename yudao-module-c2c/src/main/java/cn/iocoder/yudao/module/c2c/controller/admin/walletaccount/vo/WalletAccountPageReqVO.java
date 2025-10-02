package cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - C2C用户钱包账户分页 Request VO")
@Data
public class WalletAccountPageReqVO extends PageParam {

    @Schema(description = "用户ID", example = "31196")
    private Long userId;

    @Schema(description = "可用余额")
    private BigDecimal balance;

    @Schema(description = "冻结余额")
    private BigDecimal frozenBalance;

    @Schema(description = "累计充值")
    private BigDecimal totalRecharge;

    @Schema(description = "累计提现")
    private BigDecimal totalWithdraw;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}