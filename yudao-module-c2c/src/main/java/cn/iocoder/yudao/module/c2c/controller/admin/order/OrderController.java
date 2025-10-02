package cn.iocoder.yudao.module.c2c.controller.admin.order;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.OrderPageReqVO;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.OrderRespVO;
import cn.iocoder.yudao.module.c2c.controller.admin.order.vo.OrderSaveReqVO;
import cn.iocoder.yudao.module.c2c.dal.dataobject.order.OrderDO;
import cn.iocoder.yudao.module.c2c.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - C2C挂单表（买卖双方的挂单信息）")
@RestController
@RequestMapping("/c2c/order")
@Validated
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "创建C2C挂单表（买卖双方的挂单信息）")
    @PreAuthorize("@ss.hasPermission('c2c:order:create')")
    public CommonResult<Long> createOrder(@Valid @RequestBody OrderSaveReqVO createReqVO) {
        return success(orderService.createOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新C2C挂单表（买卖双方的挂单信息）")
    @PreAuthorize("@ss.hasPermission('c2c:order:update')")
    public CommonResult<Boolean> updateOrder(@Valid @RequestBody OrderSaveReqVO updateReqVO) {
        orderService.updateOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除C2C挂单表（买卖双方的挂单信息）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c2c:order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除C2C挂单表（买卖双方的挂单信息）")
                @PreAuthorize("@ss.hasPermission('c2c:order:delete')")
    public CommonResult<Boolean> deleteOrderList(@RequestParam("ids") List<Long> ids) {
        orderService.deleteOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得C2C挂单表（买卖双方的挂单信息）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c2c:order:query')")
    public CommonResult<OrderRespVO> getOrder(@RequestParam("id") Long id) {
        OrderDO order = orderService.getOrder(id);
        return success(BeanUtils.toBean(order, OrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得C2C挂单表（买卖双方的挂单信息）分页")
    @PreAuthorize("@ss.hasPermission('c2c:order:query')")
    public CommonResult<PageResult<OrderRespVO>> getOrderPage(@Valid OrderPageReqVO pageReqVO) {
        PageResult<OrderDO> pageResult = orderService.getOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出C2C挂单表（买卖双方的挂单信息） Excel")
    @PreAuthorize("@ss.hasPermission('c2c:order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrderExcel(@Valid OrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrderDO> list = orderService.getOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "C2C挂单表（买卖双方的挂单信息）.xls", "数据", OrderRespVO.class,
                        BeanUtils.toBean(list, OrderRespVO.class));
    }

}