package cn.iocoder.yudao.module.c2c.controller.admin.trade;

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

import cn.iocoder.yudao.module.c2c.controller.admin.trade.vo.*;
import cn.iocoder.yudao.module.c2c.dal.dataobject.trade.TradeDO;
import cn.iocoder.yudao.module.c2c.service.trade.TradeService;

@Tag(name = "管理后台 - C2C交易表（买家接单生成的交易记录）")
@RestController
@RequestMapping("/c2c/trade")
@Validated
public class TradeController {

    @Resource
    private TradeService tradeService;

    @PostMapping("/create")
    @Operation(summary = "创建C2C交易表（买家接单生成的交易记录）")
    @PreAuthorize("@ss.hasPermission('c2c:trade:create')")
    public CommonResult<Long> createTrade(@Valid @RequestBody TradeSaveReqVO createReqVO) {
        return success(tradeService.createTrade(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新C2C交易表（买家接单生成的交易记录）")
    @PreAuthorize("@ss.hasPermission('c2c:trade:update')")
    public CommonResult<Boolean> updateTrade(@Valid @RequestBody TradeSaveReqVO updateReqVO) {
        tradeService.updateTrade(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除C2C交易表（买家接单生成的交易记录）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c2c:trade:delete')")
    public CommonResult<Boolean> deleteTrade(@RequestParam("id") Long id) {
        tradeService.deleteTrade(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除C2C交易表（买家接单生成的交易记录）")
                @PreAuthorize("@ss.hasPermission('c2c:trade:delete')")
    public CommonResult<Boolean> deleteTradeList(@RequestParam("ids") List<Long> ids) {
        tradeService.deleteTradeListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得C2C交易表（买家接单生成的交易记录）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c2c:trade:query')")
    public CommonResult<TradeRespVO> getTrade(@RequestParam("id") Long id) {
        TradeDO trade = tradeService.getTrade(id);
        return success(BeanUtils.toBean(trade, TradeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得C2C交易表（买家接单生成的交易记录）分页")
    @PreAuthorize("@ss.hasPermission('c2c:trade:query')")
    public CommonResult<PageResult<TradeRespVO>> getTradePage(@Valid TradePageReqVO pageReqVO) {
        PageResult<TradeDO> pageResult = tradeService.getTradePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TradeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出C2C交易表（买家接单生成的交易记录） Excel")
    @PreAuthorize("@ss.hasPermission('c2c:trade:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTradeExcel(@Valid TradePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TradeDO> list = tradeService.getTradePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "C2C交易表（买家接单生成的交易记录）.xls", "数据", TradeRespVO.class,
                        BeanUtils.toBean(list, TradeRespVO.class));
    }

}