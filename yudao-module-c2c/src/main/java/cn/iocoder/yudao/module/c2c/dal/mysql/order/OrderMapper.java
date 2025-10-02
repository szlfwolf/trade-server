package cn.iocoder.yudao.module.c2c.dal.mysql.order;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.*;

/**
 * C2C挂单表（买卖双方的挂单信息） Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OrderMapper extends BaseMapperX<OrderDO> {

    default PageResult<OrderDO> selectPage(OrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getUserId, reqVO.getUserId())
                .eqIfPresent(OrderDO::getType, reqVO.getType())
                .eqIfPresent(OrderDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(OrderDO::getRemainingAmount, reqVO.getRemainingAmount())
                .eqIfPresent(OrderDO::getPrice, reqVO.getPrice())
                .eqIfPresent(OrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(OrderDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(OrderDO::getExpireTime, reqVO.getExpireTime())
                .orderByDesc(OrderDO::getId));
    }

}