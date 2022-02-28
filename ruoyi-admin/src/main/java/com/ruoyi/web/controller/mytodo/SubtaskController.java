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
import com.ruoyi.system.domain.Subtask;
import com.ruoyi.system.service.ISubtaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 子任务Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/subtask")
public class SubtaskController extends BaseController
{
    @Autowired
    private ISubtaskService subtaskService;

    /**
     * 查询子任务列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:list')")
    @GetMapping("/list")
    public TableDataInfo list(Subtask subtask)
    {
        startPage();
        List<Subtask> list = subtaskService.selectSubtaskList(subtask);
        return getDataTable(list);
    }

    /**
     * 导出子任务列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:export')")
    @Log(title = "子任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Subtask subtask)
    {
        List<Subtask> list = subtaskService.selectSubtaskList(subtask);
        ExcelUtil<Subtask> util = new ExcelUtil<Subtask>(Subtask.class);
        util.exportExcel(response, list, "子任务数据");
    }

    /**
     * 获取子任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(subtaskService.selectSubtaskById(id));
    }

    /**
     * 新增子任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:add')")
    @Log(title = "子任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Subtask subtask)
    {
        return toAjax(subtaskService.insertSubtask(subtask));
    }

    /**
     * 修改子任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:edit')")
    @Log(title = "子任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Subtask subtask)
    {
        return toAjax(subtaskService.updateSubtask(subtask));
    }

    /**
     * 删除子任务
     */
    @PreAuthorize("@ss.hasPermi('mytodo:subtask:remove')")
    @Log(title = "子任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(subtaskService.deleteSubtaskByIds(ids));
    }
}
