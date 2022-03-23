package com.ruoyi.web.controller.mytodo;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.service.ITaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/task")
public class TaskController extends BaseController
{
    @Autowired
    private ITaskService taskService;

    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(Task task)
    {
        startPage();
        List<Task> list = taskService.selectTaskList(task);
        return getDataTable(list);
    }

    /**
     * 查询当前登录用户、当前清单下的任务
     */
    @ApiOperation("查询当前登录用户的任务列表")
    @PreAuthorize("@ss.hasPermi('mytodo:task:list')")
    @GetMapping("/user/plist/list")
    public AjaxResult currentTaskList(Task task) {
        task.setUserId(getUserId());
        List<Task> result = taskService.selectTaskList(task);
        return AjaxResult.success(result);
    }

    /**
     * 导出任务列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:export')")
    @Log(title = "任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Task task)
    {
        List<Task> list = taskService.selectTaskList(task);
        ExcelUtil<Task> util = new ExcelUtil<Task>(Task.class);
        util.exportExcel(response, list, "任务数据");
    }

    /**
     * 获取任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskService.selectTaskById(id));
    }

    /**
     * 新增任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:add')")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Task task)
    {
        task.setCreateBy(getUsername());
        return toAjax(taskService.insertTask(task));
    }

    /**
     * 修改任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:edit')")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Task task)
    {
        task.setUpdateBy(getUsername());
        return toAjax(taskService.updateTask(task));
    }

    /**
     * 自动增加番茄数
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:edit')")
    @Log(title = "自动增加番茄数", businessType = BusinessType.UPDATE)
    @GetMapping("/autoAddTomatoNum")
    public AjaxResult autoAddTomatoNum(@RequestParam("taskId")Long taskId , @RequestParam("timeLong")Long timeLong )
    {
        return toAjax(taskService.updateTomato(taskId , timeLong));
    }

    /**
     * 删除任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:task:remove')")
    @Log(title = "任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskService.deleteTaskByIds(ids));
    }
}
