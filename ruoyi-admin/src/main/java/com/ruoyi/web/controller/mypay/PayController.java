package com.ruoyi.web.controller.mypay;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Pay;
import com.ruoyi.system.service.IPayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划Controller
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
@RestController
@RequestMapping("/mypay/pay")
public class PayController extends BaseController
{
    @Autowired
    private IPayService payService;

    /**
     * 查询计划列表
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:list')")
    @GetMapping("/list")
    public TableDataInfo list(Pay pay)
    {
        startPage();
        pay.setUserId(getUserId());
        List<Pay> list = payService.selectPayList(pay);
        return getDataTable(list);
    }

    /**
     * 导出计划列表
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:export')")
    @Log(title = "计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pay pay)
    {
        List<Pay> list = payService.selectPayList(pay);
        ExcelUtil<Pay> util = new ExcelUtil<Pay>(Pay.class);
        util.exportExcel(response, list, "计划数据");
    }

    /**
     * 获取计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payService.selectPayById(id));
    }

    /**
     * 新增计划
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:add')")
    @Log(title = "计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pay pay)
    {
        pay.setCreateBy(getUsername());
        return toAjax(payService.insertPay(pay));
    }

    /**
     * 修改计划
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:edit')")
    @Log(title = "计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pay pay)
    {
        pay.setUpdateBy(getUsername());
        return toAjax(payService.updatePay(pay));
    }

    /**
     * 删除计划
     */
    @PreAuthorize("@ss.hasPermi('mypay:pay:remove')")
    @Log(title = "计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(payService.deletePayByIds(ids));
    }
}
