package cn.iocoder.yudao.module.c2c.dal.dataobject.walletaccount;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * C2C用户钱包账户 DO
 *
 * @author 芋道源码
 */
@TableName("c2c_wallet_account")
@KeySequence("c2c_wallet_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletAccountDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 可用余额
     */
    private BigDecimal balance;
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;
    /**
     * 累计提现
     */
    private BigDecimal totalWithdraw;


}