package cn.iocoder.yudao.module.c2c.service.order;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * C2C挂单表（买卖双方的挂单信息） Service 接口
 *
 * @author 芋道源码
 */
public interface OrderService {

    /**
     * 创建C2C挂单表（买卖双方的挂单信息）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrder(@Valid OrderSaveReqVO createReqVO);

    /**
     * 更新C2C挂单表（买卖双方的挂单信息）
     *
     * @param updateReqVO 更新信息
     */
    void updateOrder(@Valid OrderSaveReqVO updateReqVO);

    /**
     * 删除C2C挂单表（买卖双方的挂单信息）
     *
     * @param id 编号
     */
    void deleteOrder(Long id);

    /**
    * 批量删除C2C挂单表（买卖双方的挂单信息）
    *
    * @param ids 编号
    */
    void deleteOrderListByIds(List<Long> ids);

    /**
     * 获得C2C挂单表（买卖双方的挂单信息）
     *
     * @param id 编号
     * @return C2C挂单表（买卖双方的挂单信息）
     */
    OrderDO getOrder(Long id);

    /**
     * 获得C2C挂单表（买卖双方的挂单信息）分页
     *
     * @param pageReqVO 分页查询
     * @return C2C挂单表（买卖双方的挂单信息）分页
     */
    PageResult<OrderDO> getOrderPage(OrderPageReqVO pageReqVO);

}