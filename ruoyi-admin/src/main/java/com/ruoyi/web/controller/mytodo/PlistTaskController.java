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
import com.ruoyi.system.domain.PlistTask;
import com.ruoyi.system.service.IPlistTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 清单任务关联Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/task")
public class PlistTaskController extends BaseController
{
    @Autowired
    private IPlistTaskService plistTaskService;

    /**
     * 查询清单任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlistTask plistTask)
    {
        startPage();
        List<PlistTask> list = plistTaskService.selectPlistTaskList(plistTask);
        return getDataTable(list);
    }

    /**
     * 导出清单任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:task:export')")
    @Log(title = "清单任务关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlistTask plistTask)
    {
        List<PlistTask> list = plistTaskService.selectPlistTaskList(plistTask);
        ExcelUtil<PlistTask> util = new ExcelUtil<PlistTask>(PlistTask.class);
        util.exportExcel(response, list, "清单任务关联数据");
    }

    /**
     * 获取清单任务关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plistTaskService.selectPlistTaskById(id));
    }

    /**
     * 新增清单任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:task:add')")
    @Log(title = "清单任务关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlistTask plistTask)
    {
        return toAjax(plistTaskService.insertPlistTask(plistTask));
    }

    /**
     * 修改清单任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:task:edit')")
    @Log(title = "清单任务关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlistTask plistTask)
    {
        return toAjax(plistTaskService.updatePlistTask(plistTask));
    }

    /**
     * 删除清单任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:task:remove')")
    @Log(title = "清单任务关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(plistTaskService.deletePlistTaskByIds(ids));
    }
}
