package cn.iocoder.yudao.module.c2c.service.trade;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.c2c.controller.admin.trade.vo.TradePageReqVO;
import cn.iocoder.yudao.module.c2c.controller.admin.trade.vo.TradeSaveReqVO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import cn.iocoder.yudao.module.c2c.dal.mysql.order.OrderMapper;
import cn.iocoder.yudao.module.c2c.dal.mysql.trade.TradeMapper;
import cn.iocoder.yudao.module.c2c.dal.mysql.walletaccount.WalletAccountMapper;
import cn.iocoder.yudao.module.c2c.enums.C2CTradeStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c2c.enums.ErrorCodeConstants.TRADE_NOT_EXISTS;

/**
 * C2C交易表（买家接单生成的交易记录） Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TradeServiceImpl implements TradeService {

    @Resource
    private TradeMapper tradeMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private WalletAccountMapper walletMapper;

    @Override
    public Long createTrade(TradeSaveReqVO createReqVO) {
        // 插入
        TradeDO trade = BeanUtils.toBean(createReqVO, TradeDO.class);
        tradeMapper.insert(trade);

        // 返回
        return trade.getId();
    }

    @Override
    public void updateTrade(TradeSaveReqVO updateReqVO) {
        // 校验存在
        validateTradeExists(updateReqVO.getId());
        // 更新
        TradeDO updateObj = BeanUtils.toBean(updateReqVO, TradeDO.class);
        tradeMapper.updateById(updateObj);
    }

    @Override
    public void deleteTrade(Long id) {
        // 校验存在
        validateTradeExists(id);
        // 删除
        tradeMapper.deleteById(id);
    }

    @Override
    public void deleteTradeListByIds(List<Long> ids) {
        // 删除
        tradeMapper.deleteByIds(ids);
    }


    private void validateTradeExists(Long id) {
        if (tradeMapper.selectById(id) == null) {
            throw exception(TRADE_NOT_EXISTS);
        }
    }

    @Override
    public TradeDO getTrade(Long id) {
        return tradeMapper.selectById(id);
    }

    @Override
    public PageResult<TradeDO> getTradePage(TradePageReqVO pageReqVO) {
        return tradeMapper.selectPage(pageReqVO);
    }


    @Transactional
    @Override
    public void handleExpiredTrades() {
        LocalDateTime now = LocalDateTime.now();

        // 1. 买家超时未付款 -> 取消交易，返还卖家冻结资金
        List<TradeDO> unpaidTrades = tradeMapper.selectUnpaidExpiredTrades(now);
        for (TradeDO trade : unpaidTrades) {
            trade.setStatus(C2CTradeStatusEnum.CANCELLED.getCode());
            tradeMapper.updateById(trade);

            // 更新订单剩余金额
            OrderDO order = orderMapper.selectById(trade.getOrderId());
            order.setRemainingAmount(order.getRemainingAmount().add(trade.getAmount()));
            orderMapper.updateById(order);

            // 解冻卖家冻结资金
            walletMapper.updateBalance(order.getUserId(), null, trade.getAmount().negate());
        }

        // 2. 卖家超时未放币 -> 系统自动放币给买家
        List<TradeDO> unReleasedTrades = tradeMapper.selectUnreleasedExpiredTrades(now);
        for (TradeDO trade : unReleasedTrades) {
            trade.setStatus(C2CTradeStatusEnum.RELEASED.getCode());
            tradeMapper.updateById(trade);

            OrderDO order = orderMapper.selectById(trade.getOrderId());

            // 卖家冻结余额扣减
            walletMapper.updateBalance(order.getUserId(), null, trade.getAmount().negate());
            // 买家余额增加
            walletMapper.updateBalance(trade.getTakerId(), trade.getAmount(), null);
        }
    }

    @Transactional
    @Override
    public void handleBuyerTimeout(Long tradeId) {
        TradeDO trade = tradeMapper.selectById(tradeId);
        if (trade == null || !Objects.equals(trade.getStatus(), C2CTradeStatusEnum.ONGOING.getCode())) {
            return; // 已处理或状态不符
        }

        trade.setStatus(C2CTradeStatusEnum.CANCELLED.getCode());
        tradeMapper.updateById(trade);

        // 返还订单剩余量
        OrderDO order = orderMapper.selectById(trade.getOrderId());
        order.setRemainingAmount(order.getRemainingAmount().add(trade.getAmount()));
        orderMapper.updateById(order);

        // 卖家解冻
        walletMapper.updateBalance(order.getUserId(), null, trade.getAmount().negate());
    }

    @Transactional
    @Override
    public void handleSellerTimeout(Long tradeId) {
        TradeDO trade = tradeMapper.selectById(tradeId);
        if (trade == null || !Objects.equals(trade.getStatus(), C2CTradeStatusEnum.PAID.getCode())) {
            return;
        }

        trade.setStatus(C2CTradeStatusEnum.RELEASED.getCode());
        tradeMapper.updateById(trade);

        OrderDO order = orderMapper.selectById(trade.getOrderId());

        // 卖家扣冻结
        walletMapper.updateBalance(order.getUserId(), null, trade.getAmount().negate());
        // 买家加余额
        walletMapper.updateBalance(trade.getTakerId(), trade.getAmount(), null);
    }

}