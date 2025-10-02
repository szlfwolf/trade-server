package cn.iocoder.yudao.module.c2c.service.walletaccount;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.walletaccount.WalletAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * C2C用户钱包账户 Service 接口
 *
 * @author 芋道源码
 */
public interface WalletAccountService {

    /**
     * 创建C2C用户钱包账户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWalletAccount(@Valid WalletAccountSaveReqVO createReqVO);

    /**
     * 更新C2C用户钱包账户
     *
     * @param updateReqVO 更新信息
     */
    void updateWalletAccount(@Valid WalletAccountSaveReqVO updateReqVO);

    /**
     * 删除C2C用户钱包账户
     *
     * @param id 编号
     */
    void deleteWalletAccount(Long id);

    /**
    * 批量删除C2C用户钱包账户
    *
    * @param ids 编号
    */
    void deleteWalletAccountListByIds(List<Long> ids);

    /**
     * 获得C2C用户钱包账户
     *
     * @param id 编号
     * @return C2C用户钱包账户
     */
    WalletAccountDO getWalletAccount(Long id);

    /**
     * 获得C2C用户钱包账户分页
     *
     * @param pageReqVO 分页查询
     * @return C2C用户钱包账户分页
     */
    PageResult<WalletAccountDO> getWalletAccountPage(WalletAccountPageReqVO pageReqVO);

    /**
     * 创建交易
     *
     * @param userId 用户编号
     * @param amount 交易金额
     */
    void createSellOrder(Long userId, BigDecimal amount, BigDecimal price);


    /**
     * 交易成功，释放冻结的代币
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void releaseCoin(Long tradeId);

}