package cn.iocoder.yudao.module.c2c.service.walletaccount;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import cn.iocoder.yudao.module.c2c.dal.mysql.order.OrderMapper;
import cn.iocoder.yudao.module.c2c.dal.mysql.trade.TradeMapper;
import cn.iocoder.yudao.module.c2c.enums.C2CTradeStatusEnum;
import cn.iocoder.yudao.module.c2c.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.walletaccount.WalletAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.c2c.dal.mysql.walletaccount.WalletAccountMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.c2c.enums.ErrorCodeConstants.*;

/**
 * C2C用户钱包账户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class WalletAccountServiceImpl implements WalletAccountService {

    @Resource
    private WalletAccountMapper walletAccountMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private TradeMapper tradeMapper;

    @Override
    public Long createWalletAccount(WalletAccountSaveReqVO createReqVO) {
        // 插入
        WalletAccountDO walletAccount = BeanUtils.toBean(createReqVO, WalletAccountDO.class);
        walletAccountMapper.insert(walletAccount);

        // 返回
        return walletAccount.getId();
    }

    @Override
    public void updateWalletAccount(WalletAccountSaveReqVO updateReqVO) {
        // 校验存在
        validateWalletAccountExists(updateReqVO.getId());
        // 更新
        WalletAccountDO updateObj = BeanUtils.toBean(updateReqVO, WalletAccountDO.class);
        walletAccountMapper.updateById(updateObj);
    }

    @Override
    public void deleteWalletAccount(Long id) {
        // 校验存在
        validateWalletAccountExists(id);
        // 删除
        walletAccountMapper.deleteById(id);
    }

    @Override
        public void deleteWalletAccountListByIds(List<Long> ids) {
        // 删除
        walletAccountMapper.deleteByIds(ids);
        }


    private void validateWalletAccountExists(Long id) {
        if (walletAccountMapper.selectById(id) == null) {
            throw exception(WALLET_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public WalletAccountDO getWalletAccount(Long id) {
        return walletAccountMapper.selectById(id);
    }

    @Override
    public PageResult<WalletAccountDO> getWalletAccountPage(WalletAccountPageReqVO pageReqVO) {
        return walletAccountMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void createSellOrder(Long userId, BigDecimal amount, BigDecimal price) {
        WalletAccountDO wallet = this.walletAccountMapper.selectByUserId(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw exception(WALLET_ACCOUNT_BALANCE_NOT_ENOUGH);
        }

        // 扣除可用余额，增加冻结余额
        walletAccountMapper.updateBalance(userId, amount.negate(), amount);

        // 创建挂单
        OrderDO order = new OrderDO();
        order.setUserId(userId);
        order.setType(2); // 卖单
        order.setTotalAmount(amount);
        order.setRemainingAmount(amount);
        order.setPrice(price);
        order.setStatus(0);
        orderMapper.insert(order);
    }


    @Override
    @Transactional
    public void releaseCoin(Long tradeId) {
        TradeDO trade = tradeMapper.selectById(tradeId);
        OrderDO order = orderMapper.selectById(trade.getOrderId());

        // 卖家资金从冻结余额扣除
        walletAccountMapper.updateBalance(order.getUserId(), null, trade.getAmount().negate());

        // 买家余额增加
        walletAccountMapper.updateBalance(trade.getTakerId(), trade.getAmount(), null);

        // 更新状态
        trade.setStatus(C2CTradeStatusEnum.RELEASED.getCode());
        tradeMapper.updateById(trade);
    }

}