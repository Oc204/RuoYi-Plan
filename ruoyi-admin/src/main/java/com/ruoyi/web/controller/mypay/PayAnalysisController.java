package com.ruoyi.web.controller.mypay;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.VariableValueType;
import com.ruoyi.system.service.IPayService;
import com.ruoyi.web.controller.BaseAnalysisController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 支出分析Controller
 *
 * @author ruoyi
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/mypay/analysis")
public class PayAnalysisController extends BaseAnalysisController {

    @Autowired
    private IPayService payService;

    @GetMapping("/year/{year}")
    public AjaxResult yearAnalysis(@PathVariable("year") String year) {

        if(VariableValueType.UNDEFINED.getCode().equals(year)){
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
        Map<String, Object[]> result = new HashMap<>(255) ;

        Map<String, List<Object>> data = formatByDateTime(year, month, paySum) ;

        result.put("month",data.get("timeResult").toArray(new Object[data.get("timeResult").size()])) ;
        result.put("paySum",data.get("sumResult").toArray(new Object[data.get("sumResult").size()])) ;

        return result;
    }
}
