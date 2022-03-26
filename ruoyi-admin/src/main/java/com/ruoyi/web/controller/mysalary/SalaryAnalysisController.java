package com.ruoyi.web.controller.mysalary;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.VariableValueType;
import com.ruoyi.system.domain.Salary;
import com.ruoyi.system.service.ISalaryService;
import com.ruoyi.web.controller.BaseAnalysisController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 收入分析Controller
 *
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mysalary/analysis")
public class SalaryAnalysisController extends BaseAnalysisController {

    private static final int MONTHNUMBER = 12  ;

    @Autowired
    private ISalaryService salaryService;

    @GetMapping("/month")
    public AjaxResult monthAnalysis(Salary salary) {

        return AjaxResult.success(salaryService.anaylysisByMonth(salary));
    }

    @GetMapping("/year/{year}")
    public AjaxResult yearAnalysis(@PathVariable("year") String year) {

        if(VariableValueType.UNDEFINED.getCode().equals(year)){
            Calendar cal = Calendar.getInstance();
            year = String.valueOf(cal.get(Calendar.YEAR));
        }
        Map<String, Object[]> result;
        List<Map<String,Object>> list = salaryService.anaylysisByYear(year) ;

        List<Object> month = new ArrayList<>() ;
        List<Object> salarySum = new ArrayList<>() ;

        for(Map<String,Object> item : list){
            month.add(item.get("month")) ;
            salarySum.add(item.get("sum")) ;
        }

        result = setMonth(year, month, salarySum) ;

        return AjaxResult.success(result);
    }

    public Map<String, Object[]> setMonth(String year, List<Object> month, List<Object> salarySum) {
        Map<String, Object[]> result = new HashMap<>(255) ;

        Map<String, List<Object>> data = formatByDateTime(year, month, salarySum) ;

        result.put("month",data.get("timeResult").toArray(new Object[data.get("timeResult").size()])) ;
        result.put("salarySum",data.get("sumResult").toArray(new Object[data.get("sumResult").size()])) ;

        return result;
    }
}
