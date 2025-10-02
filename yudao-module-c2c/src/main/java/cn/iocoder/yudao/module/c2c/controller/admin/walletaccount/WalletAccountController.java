package cn.iocoder.yudao.module.c2c.controller.admin.walletaccount;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.c2c.controller.admin.walletaccount.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.walletaccount.WalletAccountDO;
import cn.iocoder.yudao.module.c2c.service.walletaccount.WalletAccountService;

@Tag(name = "管理后台 - C2C用户钱包账户")
@RestController
@RequestMapping("/c2c/wallet-account")
@Validated
public class WalletAccountController {

    @Resource
    private WalletAccountService walletAccountService;

    @PostMapping("/create")
    @Operation(summary = "创建C2C用户钱包账户")
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:create')")
    public CommonResult<Long> createWalletAccount(@Valid @RequestBody WalletAccountSaveReqVO createReqVO) {
        return success(walletAccountService.createWalletAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新C2C用户钱包账户")
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:update')")
    public CommonResult<Boolean> updateWalletAccount(@Valid @RequestBody WalletAccountSaveReqVO updateReqVO) {
        walletAccountService.updateWalletAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除C2C用户钱包账户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:delete')")
    public CommonResult<Boolean> deleteWalletAccount(@RequestParam("id") Long id) {
        walletAccountService.deleteWalletAccount(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除C2C用户钱包账户")
                @PreAuthorize("@ss.hasPermission('c2c:wallet-account:delete')")
    public CommonResult<Boolean> deleteWalletAccountList(@RequestParam("ids") List<Long> ids) {
        walletAccountService.deleteWalletAccountListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得C2C用户钱包账户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:query')")
    public CommonResult<WalletAccountRespVO> getWalletAccount(@RequestParam("id") Long id) {
        WalletAccountDO walletAccount = walletAccountService.getWalletAccount(id);
        return success(BeanUtils.toBean(walletAccount, WalletAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得C2C用户钱包账户分页")
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:query')")
    public CommonResult<PageResult<WalletAccountRespVO>> getWalletAccountPage(@Valid WalletAccountPageReqVO pageReqVO) {
        PageResult<WalletAccountDO> pageResult = walletAccountService.getWalletAccountPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WalletAccountRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出C2C用户钱包账户 Excel")
    @PreAuthorize("@ss.hasPermission('c2c:wallet-account:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWalletAccountExcel(@Valid WalletAccountPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WalletAccountDO> list = walletAccountService.getWalletAccountPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "C2C用户钱包账户.xls", "数据", WalletAccountRespVO.class,
                        BeanUtils.toBean(list, WalletAccountRespVO.class));
    }

}