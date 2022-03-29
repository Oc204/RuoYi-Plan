package com.ruoyi.web.controller.pic;

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
import com.ruoyi.system.domain.Pic;
import com.ruoyi.system.service.IPicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图片Controller
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
@RestController
@RequestMapping("/pic")
public class PicController extends BaseController
{
    @Autowired
    private IPicService picService;

    /**
     * 查询图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:pic:list')")
    @GetMapping("/list")
    public TableDataInfo list(Pic pic)
    {
        startPage();
        List<Pic> list = picService.selectPicList(pic);
        return getDataTable(list);
    }

    /**
     * 查询公开的图片列表
     */
    @GetMapping("/public/list")
    public TableDataInfo publicList()
    {
        Pic pic = new Pic() ;
        List<Pic> list = picService.selectPicPublicList(pic);
        return getDataTable(list);
    }

    /**
     * 导出图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:pic:export')")
    @Log(title = "图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pic pic)
    {
        List<Pic> list = picService.selectPicList(pic);
        ExcelUtil<Pic> util = new ExcelUtil<Pic>(Pic.class);
        util.exportExcel(response, list, "图片数据");
    }

    /**
     * 获取图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(picService.selectPicById(id));
    }

    /**
     * 新增图片
     */
    @PreAuthorize("@ss.hasPermi('system:pic:add')")
    @Log(title = "图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pic pic)
    {
        return toAjax(picService.insertPic(pic));
    }

    /**
     * 上传图片
     */
    @Log(title = "图片", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult uploadPic(@RequestBody Pic pic)
    {
        // 设置为未审核
        pic.setApprove(0L);
        // 设置为未删除
        pic.setHasDelete(1L);

        return toAjax(picService.insertPic(pic));
    }

    /**
     * 修改图片
     */
    @PreAuthorize("@ss.hasPermi('system:pic:edit')")
    @Log(title = "图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pic pic)
    {
        return toAjax(picService.updatePic(pic));
    }

    /**
     * 删除图片
     */
    @PreAuthorize("@ss.hasPermi('system:pic:remove')")
    @Log(title = "图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(picService.deletePicByIds(ids));
    }
}
