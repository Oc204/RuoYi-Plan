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
import com.ruoyi.system.domain.Tomato;
import com.ruoyi.system.service.ITomatoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 番茄Controller
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/mytodo/tomato")
public class TomatoController extends BaseController
{
    @Autowired
    private ITomatoService tomatoService;

    /**
     * 查询番茄列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tomato tomato)
    {
        startPage();
        List<Tomato> list = tomatoService.selectTomatoList(tomato);
        return getDataTable(list);
    }

    /**
     * 导出番茄列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:export')")
    @Log(title = "番茄", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Tomato tomato)
    {
        List<Tomato> list = tomatoService.selectTomatoList(tomato);
        ExcelUtil<Tomato> util = new ExcelUtil<Tomato>(Tomato.class);
        util.exportExcel(response, list, "番茄数据");
    }

    /**
     * 获取番茄详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tomatoService.selectTomatoById(id));
    }

    /**
     * 获取番茄统计信息
     */
    @GetMapping(value = "/index/statistics")
    public AjaxResult getInfo()
    {
        return AjaxResult.success(tomatoService.selectTomatoStatisticsByUserId());
    }

    /**
     * 新增番茄
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:add')")
    @Log(title = "番茄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Tomato tomato)
    {
        return toAjax(tomatoService.insertTomato(tomato));
    }

    /**
     * 修改番茄
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:edit')")
    @Log(title = "番茄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Tomato tomato)
    {
        return toAjax(tomatoService.updateTomato(tomato));
    }

    /**
     * 删除番茄
     */
    @PreAuthorize("@ss.hasPermi('mytodo:tomato:remove')")
    @Log(title = "番茄", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tomatoService.deleteTomatoByIds(ids));
    }
}
