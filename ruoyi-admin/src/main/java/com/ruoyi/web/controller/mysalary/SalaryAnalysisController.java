package com.ruoyi.web.controller.mysalary;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.VariableValueType;
import com.ruoyi.system.domain.Salary;
import com.ruoyi.system.service.ISalaryService;
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
public class SalaryAnalysisController {

    private static final int MONTHNUMBER = 12  ;

    @Autowired
    private ISalaryService salaryService;

    @GetMapping("/month")
    public AjaxResult MonthAnalysis(Salary salary) {

        return AjaxResult.success(salaryService.anaylysisByMonth(salary));
    }

    @GetMapping("/year/{year}")
    public AjaxResult YearAnalysis(@PathVariable("year") String year) {

        if(VariableValueType.UNDEFINED.getCode().equals(year)){
            Calendar cal = Calendar.getInstance();
            year = String.valueOf(cal.get(Calendar.YEAR));
        }
        Map<String, Object[]> result = new HashMap<>() ;
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
        List<String> monthNum = new ArrayList<>() ;
        List<Object> monthSalarySum = new ArrayList<>() ;
        Map<String, Object[]> result = new HashMap<>() ;

        int j = 0 ;
        for (int i = 0 ; i <= MONTHNUMBER ;i ++) {
            if ((i+1)>=10){
                monthNum.add(year+ "-" + (i+1)) ;
            }else {
                monthNum.add(year+ "-0" + (i+1)) ;
            }

            // 如果存在月工资记录，则记录工资详情值
            if (month.size()>0&&month.size()>j){
                if (month.get(j).equals(monthNum.get(i))){
                    monthSalarySum.add(salarySum.get(j)) ;
                    j++;
                }else{
                    monthSalarySum.add(0) ;
                }
            }else{
                monthSalarySum.add(0) ;
            }

        }

        result.put("month",monthNum.toArray(new Object[monthNum.size()])) ;
        result.put("salarySum",monthSalarySum.toArray(new Object[monthSalarySum.size()])) ;

        return result;
    }
}
