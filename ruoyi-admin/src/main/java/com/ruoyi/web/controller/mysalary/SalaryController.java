package com.ruoyi.web.controller.mysalary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.vo.SalaryVo;
import org.springframework.beans.BeanUtils;
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
import com.ruoyi.system.domain.Salary;
import com.ruoyi.system.service.ISalaryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划Controller
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mysalary/salary")
public class SalaryController extends BaseController
{
    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private ISalaryService salaryService;

    /**
     * 查询计划列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:list')")
    @GetMapping("/list")
    public TableDataInfo list(Salary salary)
    {
        startPage();
        List<Salary> list = salaryService.selectSalaryList(salary);
        List<SalaryVo> voList = new ArrayList<>() ;
        for(int i=0; i<list.size(); i++) {
            SalaryVo vo = new SalaryVo() ;
            BeanUtils.copyProperties(list.get(i),vo);

            if(list.get(i).getSalaryPic() != null) {
                String[] arr = list.get(i).getSalaryPic().split(",") ;
                if (arr.length > 0) {
                    List<String> picList = new ArrayList<>() ;
                    for (String str : arr) {
                        str = serverConfig.getUrl() + str ;
                        picList.add(str) ;
                    }
                    vo.setSalaryPic(picList);
                }
            }

            voList.add(vo) ;
        }
        return getDataTable(voList);
    }

    /**
     * 导出计划列表
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:export')")
    @Log(title = "计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Salary salary)
    {
        List<Salary> list = salaryService.selectSalaryList(salary);
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        util.exportExcel(response, list, "计划数据");
    }

    /**
     * 获取计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(salaryService.selectSalaryById(id));
    }

    /**
     * 新增计划
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:add')")
    @Log(title = "计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Salary salary)
    {
        salary.setCreateBy(getUsername());
        return toAjax(salaryService.insertSalary(salary));
    }

    /**
     * 修改计划
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:edit')")
    @Log(title = "计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Salary salary)
    {
        salary.setUpdateBy(getUsername());
        return toAjax(salaryService.updateSalary(salary));
    }

    /**
     * 删除计划
     */
    @PreAuthorize("@ss.hasPermi('mytodo:salary:remove')")
    @Log(title = "计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(salaryService.deleteSalaryByIds(ids));
    }
}
