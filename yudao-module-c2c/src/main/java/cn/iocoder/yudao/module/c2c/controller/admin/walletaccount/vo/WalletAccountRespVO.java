package cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - C2C用户钱包账户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WalletAccountRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8293")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31196")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "可用余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可用余额")
    private BigDecimal balance;

    @Schema(description = "冻结余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("冻结余额")
    private BigDecimal frozenBalance;

    @Schema(description = "累计充值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("累计充值")
    private BigDecimal totalRecharge;

    @Schema(description = "累计提现", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("累计提现")
    private BigDecimal totalWithdraw;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}