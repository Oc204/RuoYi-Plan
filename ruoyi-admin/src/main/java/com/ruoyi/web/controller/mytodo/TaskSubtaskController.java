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
import com.ruoyi.system.domain.TaskSubtask;
import com.ruoyi.system.service.ITaskSubtaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务子任务关联Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/subtask")
public class TaskSubtaskController extends BaseController
{
    @Autowired
    private ITaskSubtaskService taskSubtaskService;

    /**
     * 查询任务子任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskSubtask taskSubtask)
    {
        startPage();
        List<TaskSubtask> list = taskSubtaskService.selectTaskSubtaskList(taskSubtask);
        return getDataTable(list);
    }

    /**
     * 导出任务子任务关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:export')")
    @Log(title = "任务子任务关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskSubtask taskSubtask)
    {
        List<TaskSubtask> list = taskSubtaskService.selectTaskSubtaskList(taskSubtask);
        ExcelUtil<TaskSubtask> util = new ExcelUtil<TaskSubtask>(TaskSubtask.class);
        util.exportExcel(response, list, "任务子任务关联数据");
    }

    /**
     * 获取任务子任务关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskSubtaskService.selectTaskSubtaskById(id));
    }

    /**
     * 新增任务子任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:add')")
    @Log(title = "任务子任务关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskSubtask taskSubtask)
    {
        return toAjax(taskSubtaskService.insertTaskSubtask(taskSubtask));
    }

    /**
     * 修改任务子任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:edit')")
    @Log(title = "任务子任务关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskSubtask taskSubtask)
    {
        return toAjax(taskSubtaskService.updateTaskSubtask(taskSubtask));
    }

    /**
     * 删除任务子任务关联
     */
    @PreAuthorize("@ss.hasPermi('system:subtask:remove')")
    @Log(title = "任务子任务关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskSubtaskService.deleteTaskSubtaskByIds(ids));
    }
}
