package com.ruoyi.web.controller.mytodo;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.system.domain.Plist;
import com.ruoyi.system.service.IPlistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 清单Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mytodo/plist")
public class PlistController extends BaseController
{
    @Autowired
    private IPlistService plistService;

    /**
     * 查询清单列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:list')")
    @GetMapping("/list")
    public TableDataInfo list(Plist plist)
    {
        startPage();
        List<Plist> list = plistService.selectPlistList(plist);
        return getDataTable(list);
    }

    /**
     * 查询当前登录用户的清单列表
     */
    @ApiOperation("查询当前登录用户的清单列表")
    @PreAuthorize("@ss.hasPermi('mytodo:plist:list')")
    @GetMapping("/user/list")
    public AjaxResult currentUserList(Plist plist) {
        plist.setUserId(getUserId());
        List<Plist> result = plistService.selectPlistList(plist) ;
        return AjaxResult.success(result);
    }

    /**
     * 导出清单列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:export')")
    @Log(title = "清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Plist plist)
    {
        List<Plist> list = plistService.selectPlistList(plist);
        ExcelUtil<Plist> util = new ExcelUtil<Plist>(Plist.class);
        util.exportExcel(response, list, "清单数据");
    }

    /**
     * 获取清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plistService.selectPlistById(id));
    }

    /**
     * 新增清单
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:add')")
    @Log(title = "清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Plist plist)
    {
        plist.setCreateBy(getUsername());
        return toAjax(plistService.insertPlist(plist));
    }

    /**
     * 修改清单
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:edit')")
    @Log(title = "清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Plist plist)
    {
        plist.setUpdateBy(getUsername());
        return toAjax(plistService.updatePlist(plist));
    }

    /**
     * 删除清单
     */
    @PreAuthorize("@ss.hasPermi('mytodo:plist:remove')")
    @Log(title = "清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(plistService.deletePlistByIds(ids));
    }
}
