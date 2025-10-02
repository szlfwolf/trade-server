package cn.iocoder.yudao.module.c2c.dal.dataobject.order;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * C2C挂单表（买卖双方的挂单信息） DO
 *
 * @author 芋道源码
 */
@TableName("c2c_order")
@KeySequence("c2c_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 挂单用户ID
     */
    private Long userId;
    /**
     * 挂单类型：1=BUY买入，2=SELL卖出
     */
    private Integer type;
    /**
     * 挂单总额（USDT数量）
     */
    private BigDecimal totalAmount;
    /**
     * 剩余可成交额（USDT数量）
     */
    private BigDecimal remainingAmount;
    /**
     * 单价（CNY/USDT）
     */
    private BigDecimal price;
    /**
     * 订单状态：0=待接单，1=部分成交，2=完成，3=取消
     */
    private Integer status;
    /**
     * 订单过期时间（超过此时间未成交则自动取消）
     */
    private LocalDateTime expireTime;


}