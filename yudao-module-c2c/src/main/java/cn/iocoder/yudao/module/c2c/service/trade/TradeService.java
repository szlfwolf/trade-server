package cn.iocoder.yudao.module.c2c.service.trade;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c2c.controller.admin.trade.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * C2C交易表（买家接单生成的交易记录） Service 接口
 *
 * @author 芋道源码
 */
public interface TradeService {

    /**
     * 创建C2C交易表（买家接单生成的交易记录）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTrade(@Valid TradeSaveReqVO createReqVO);

    /**
     * 更新C2C交易表（买家接单生成的交易记录）
     *
     * @param updateReqVO 更新信息
     */
    void updateTrade(@Valid TradeSaveReqVO updateReqVO);

    /**
     * 删除C2C交易表（买家接单生成的交易记录）
     *
     * @param id 编号
     */
    void deleteTrade(Long id);

    /**
    * 批量删除C2C交易表（买家接单生成的交易记录）
    *
    * @param ids 编号
    */
    void deleteTradeListByIds(List<Long> ids);

    /**
     * 获得C2C交易表（买家接单生成的交易记录）
     *
     * @param id 编号
     * @return C2C交易表（买家接单生成的交易记录）
     */
    TradeDO getTrade(Long id);

    /**
     * 获得C2C交易表（买家接单生成的交易记录）分页
     *
     * @param pageReqVO 分页查询
     * @return C2C交易表（买家接单生成的交易记录）分页
     */
    PageResult<TradeDO> getTradePage(TradePageReqVO pageReqVO);

    void handleExpiredTrades();

    void handleBuyerTimeout(Long tradeId);

    void handleSellerTimeout(Long tradeId);
}