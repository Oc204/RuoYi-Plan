package com.ruoyi.web.controller.mypay;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Pay;
import com.ruoyi.system.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/mypay/analysis")
public class PayAnalysisController {

    @Autowired
    private IPayService payService;

    @GetMapping("/year/{year}")
    public AjaxResult YearAnalysis(@PathVariable("year") String year) {

        if("undefined".equals(year)){
            Calendar cal = Calendar.getInstance();
            year = String.valueOf(cal.get(Calendar.YEAR));
        }
        List<Map<String,Object>> list = payService.anaylysisByYear(year) ;

        List<Object> month = new ArrayList<>() ;
        List<Object> paySum = new ArrayList<>() ;

        for(Map<String,Object> item : list){
            month.add(item.get("month")) ;
            paySum.add(item.get("sum")) ;
        }

        return AjaxResult.success(setMonth(year, month, paySum));
    }

    public Map<String, Object[]> setMonth(String year, List<Object> month, List<Object> paySum) {
        List<String> monthNum = new ArrayList<>() ;
        List<Object> monthPaySum = new ArrayList<>() ;
        Map<String, Object[]> result = new HashMap<>() ;

        int j = 0 ;
        for (int i = 0 ; i <= 12 ;i ++) {
            if ((i+1)>=10){
                monthNum.add(year+ "-" + (i+1)) ;
            }else {
                monthNum.add(year+ "-0" + (i+1)) ;
            }

            // 如果存在月支出记录，则记录支出详情值
            if (month.size()>0&&month.size()>j){
                if (month.get(j).equals(monthNum.get(i))){
                    monthPaySum.add(paySum.get(j)) ;
                    j++;
                }else{
                    monthPaySum.add(0) ;
                }
            }else{
                monthPaySum.add(0) ;
            }

        }

        result.put("month",monthNum.toArray(new Object[monthNum.size()])) ;
        result.put("paySum",monthPaySum.toArray(new Object[monthPaySum.size()])) ;

        return result;
    }
}
