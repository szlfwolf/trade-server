package cn.iocoder.yudao.module.c2c.service.order;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.OrderPageReqVO;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.OrderSaveReqVO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import cn.iocoder.yudao.module.c2c.dal.mysql.order.OrderMapper;
import cn.iocoder.yudao.module.c2c.dal.mysql.trade.TradeMapper;
import cn.iocoder.yudao.module.c2c.enums.C2COrderStatusEnum;
import cn.iocoder.yudao.module.c2c.enums.C2CTradeStatusEnum;
import cn.iocoder.yudao.module.c2c.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c2c.enums.ErrorCodeConstants.ORDER_NOT_EXISTS;

/**
 * C2C挂单表（买卖双方的挂单信息） Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private TradeMapper tradeMapper;

    @Override
    public Long createOrder(OrderSaveReqVO createReqVO) {
        // 插入
        OrderDO order = BeanUtils.toBean(createReqVO, OrderDO.class);
        orderMapper.insert(order);

        // 返回
        return order.getId();
    }

    @Override
    public void updateOrder(OrderSaveReqVO updateReqVO) {
        // 校验存在
        validateOrderExists(updateReqVO.getId());
        // 更新
        OrderDO updateObj = BeanUtils.toBean(updateReqVO, OrderDO.class);
        orderMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrder(Long id) {
        // 校验存在
        validateOrderExists(id);
        // 删除
        orderMapper.deleteById(id);
    }

    @Override
        public void deleteOrderListByIds(List<Long> ids) {
        // 删除
        orderMapper.deleteByIds(ids);
        }


    private void validateOrderExists(Long id) {
        if (orderMapper.selectById(id) == null) {
            throw exception(ORDER_NOT_EXISTS);
        }
    }

    @Override
    public OrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public PageResult<OrderDO> getOrderPage(OrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

}