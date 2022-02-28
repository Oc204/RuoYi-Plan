package com.ruoyi.web.controller.mytodo;

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
import com.ruoyi.system.domain.PlanPlist;
import com.ruoyi.system.service.IPlanPlistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划清单关联Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/planplist")
public class PlanPlistController extends BaseController
{
    @Autowired
    private IPlanPlistService planPlistService;

    /**
     * 查询计划清单关联列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanPlist planPlist)
    {
        startPage();
        List<PlanPlist> list = planPlistService.selectPlanPlistList(planPlist);
        return getDataTable(list);
    }

    /**
     * 导出计划清单关联列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:export')")
    @Log(title = "计划清单关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlanPlist planPlist)
    {
        List<PlanPlist> list = planPlistService.selectPlanPlistList(planPlist);
        ExcelUtil<PlanPlist> util = new ExcelUtil<PlanPlist>(PlanPlist.class);
        util.exportExcel(response, list, "计划清单关联数据");
    }

    /**
     * 获取计划清单关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(planPlistService.selectPlanPlistById(id));
    }

    /**
     * 新增计划清单关联
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:add')")
    @Log(title = "计划清单关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanPlist planPlist)
    {
        return toAjax(planPlistService.insertPlanPlist(planPlist));
    }

    /**
     * 修改计划清单关联
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:edit')")
    @Log(title = "计划清单关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanPlist planPlist)
    {
        return toAjax(planPlistService.updatePlanPlist(planPlist));
    }

    /**
     * 删除计划清单关联
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:remove')")
    @Log(title = "计划清单关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(planPlistService.deletePlanPlistByIds(ids));
    }
}
