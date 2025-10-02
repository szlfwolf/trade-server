package cn.iocoder.yudao.module.c2c.dal.mysql.trade;

import java.time.LocalDateTime;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c2c.controller.admin.trade.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * C2C交易表（买家接单生成的交易记录） Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeMapper extends BaseMapperX<TradeDO> {

    default PageResult<TradeDO> selectPage(TradePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TradeDO>()
                .eqIfPresent(TradeDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(TradeDO::getTakerId, reqVO.getTakerId())
                .eqIfPresent(TradeDO::getAmount, reqVO.getAmount())
                .eqIfPresent(TradeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TradeDO::getPayExpireTime, reqVO.getPayExpireTime())
                .betweenIfPresent(TradeDO::getReleaseExpireTime, reqVO.getReleaseExpireTime())
                .betweenIfPresent(TradeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TradeDO::getId));
    }


    @Select("SELECT * FROM c2c_trade " +
            "WHERE status = 0 AND pay_expire_time < #{now}")
    List<TradeDO> selectUnpaidExpiredTrades(@Param("now") LocalDateTime now);

    @Select("SELECT * FROM c2c_trade " +
            "WHERE status = 1 AND release_expire_time < #{now}")
    List<TradeDO> selectUnreleasedExpiredTrades(@Param("now") LocalDateTime now);

}