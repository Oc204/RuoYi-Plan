package com.ruoyi.web.controller.mytodo.setting;

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
import com.ruoyi.system.domain.TomatoSetting;
import com.ruoyi.system.service.ITomatoSettingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 番茄Controller
 * 
 * @author ruoyi
 * @date 2022-03-20
 */
@RestController
@RequestMapping("/mytodo/setting/tomato")
public class TomatoSettingController extends BaseController
{
    @Autowired
    private ITomatoSettingService tomatoSettingService;

    /**
     * 查询番茄列表
     */
    @PreAuthorize("@ss.hasPermi('system:setting:list')")
    @GetMapping("/list")
    public TableDataInfo list(TomatoSetting tomatoSetting)
    {
        startPage();
        List<TomatoSetting> list = tomatoSettingService.selectTomatoSettingList(tomatoSetting);
        return getDataTable(list);
    }

    /**
     * 导出番茄列表
     */
    @PreAuthorize("@ss.hasPermi('system:setting:export')")
    @Log(title = "番茄", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TomatoSetting tomatoSetting)
    {
        List<TomatoSetting> list = tomatoSettingService.selectTomatoSettingList(tomatoSetting);
        ExcelUtil<TomatoSetting> util = new ExcelUtil<TomatoSetting>(TomatoSetting.class);
        util.exportExcel(response, list, "番茄数据");
    }

    /**
     * 获取番茄详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:setting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tomatoSettingService.selectTomatoSettingById(id));
    }

    /**
     * 获取当前用户番茄设置信息
     */
    @PreAuthorize("@ss.hasPermi('system:setting:query')")
    @GetMapping(value = "/getTomato")
    public AjaxResult getTomato()
    {
        TomatoSetting tomatoSetting = tomatoSettingService.selectTomatoSettingByUserId() ;
        // 没有设置番茄时间则默认为25
        if(tomatoSetting == null){
            TomatoSetting obj = new TomatoSetting() ;
            obj.setTomatoTime(25L);
            tomatoSetting =obj ;
        } ;

        return AjaxResult.success(tomatoSetting);
    }

    /**
     * 新增番茄
     */
    @PreAuthorize("@ss.hasPermi('system:setting:add')")
    @Log(title = "番茄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TomatoSetting tomatoSetting)
    {
        return toAjax(tomatoSettingService.insertTomatoSetting(tomatoSetting));
    }

    /**
     * 修改番茄
     */
    @PreAuthorize("@ss.hasPermi('system:setting:edit')")
    @Log(title = "番茄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TomatoSetting tomatoSetting)
    {
        return toAjax(tomatoSettingService.updateTomatoSetting(tomatoSetting));
    }

    /**
     * 删除番茄
     */
    @PreAuthorize("@ss.hasPermi('system:setting:remove')")
    @Log(title = "番茄", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tomatoSettingService.deleteTomatoSettingByIds(ids));
    }
}
