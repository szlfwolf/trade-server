package cn.iocoder.yudao.module.c2c.dal.dataobject.trade;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * C2C交易表（买家接单生成的交易记录） DO
 *
 * @author 芋道源码
 */
@TableName("c2c_trade")
@KeySequence("c2c_trade_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 关联的挂单ID
     */
    private Long orderId;
    /**
     * 接单用户ID（买家/卖家）
     */
    private Long takerId;
    /**
     * 交易金额（USDT数量）
     */
    private BigDecimal amount;

    /**
     * 交易状态：0=进行中，1=买家已付款，2=已放币，3=取消
     */
    private Integer status;
    /**
     * 买家付款超时时间（未付款则交易失败）
     */
    private LocalDateTime payExpireTime;
    /**
     * 卖家放币超时时间（未放币则系统自动放币）
     */
    private LocalDateTime releaseExpireTime;


}
