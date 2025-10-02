package cn.iocoder.yudao.module.c2c.dal.mysql.walletaccount;

import java.math.BigDecimal;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.c2c.dal.dataobject.walletaccount.WalletAccountDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * C2C用户钱包账户 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WalletAccountMapper extends BaseMapperX<WalletAccountDO> {

    default PageResult<WalletAccountDO> selectPage(WalletAccountPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WalletAccountDO>()
                .eqIfPresent(WalletAccountDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WalletAccountDO::getBalance, reqVO.getBalance())
                .eqIfPresent(WalletAccountDO::getFrozenBalance, reqVO.getFrozenBalance())
                .eqIfPresent(WalletAccountDO::getTotalRecharge, reqVO.getTotalRecharge())
                .eqIfPresent(WalletAccountDO::getTotalWithdraw, reqVO.getTotalWithdraw())
                .betweenIfPresent(WalletAccountDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WalletAccountDO::getId));
    }

    default WalletAccountDO selectByUserId(Long userId){
        return selectOne(WalletAccountDO::getUserId, userId);
    }

    /**
     * 更新余额 / 冻结余额
     * @param userId 用户ID
     * @param balanceDelta 可用余额变动，可为 null
     * @param frozenDelta 冻结余额变动，可为 null
     */
    @Update({
            "<script>",
            "UPDATE c2c_wallet_account ",
            "SET update_time = NOW()",
            "<if test='balanceDelta != null'>, balance = balance + #{balanceDelta}</if>",
            "<if test='frozenDelta != null'>, frozen_balance = frozen_balance + #{frozenDelta}</if>",
            "WHERE user_id = #{userId}",
            "</script>"
    })
    int updateBalance(@Param("userId") Long userId,
                      @Param("balanceDelta") BigDecimal balanceDelta,
                      @Param("frozenDelta") BigDecimal frozenDelta);
}