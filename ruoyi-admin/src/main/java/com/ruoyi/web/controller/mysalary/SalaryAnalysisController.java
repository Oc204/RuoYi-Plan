package com.ruoyi.web.controller.mysalary;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Salary;
import com.ruoyi.system.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysalary/analysis")
public class SalaryAnalysisController {

    @Autowired
    private ISalaryService salaryService;

    @GetMapping("/month")
    public AjaxResult MonthAnalysis(Salary salary) {

        return AjaxResult.success(salaryService.anaylysisByMonth(salary));
    }

    @GetMapping("/year")
    public AjaxResult YearAnalysis(String year) {

        return AjaxResult.success(salaryService.anaylysisByYear(year));
    }
}
