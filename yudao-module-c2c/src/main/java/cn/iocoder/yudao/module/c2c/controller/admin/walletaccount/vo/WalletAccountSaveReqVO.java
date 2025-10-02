package cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - C2C用户钱包账户新增/修改 Request VO")
@Data
public class WalletAccountSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8293")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31196")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "可用余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "可用余额不能为空")
    private BigDecimal balance;

    @Schema(description = "冻结余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "冻结余额不能为空")
    private BigDecimal frozenBalance;

    @Schema(description = "累计充值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "累计充值不能为空")
    private BigDecimal totalRecharge;

    @Schema(description = "累计提现", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "累计提现不能为空")
    private BigDecimal totalWithdraw;

}