package com.ruoyi.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分析类通用处理
 *
 * @author ruoyi
 * @date 2022-03-26
 */
public class BaseAnalysisController {

    private static final int MONTHNUMBER = 12  ;

    public Map<String, List<Object>> formatByDateTime(String year, List<Object> time, List<Object> sum) {

        List<Object> timeResult = new ArrayList<>() ;
        List<Object> sumResult = new ArrayList<>() ;
        Map<String, List<Object>> result = new HashMap<>() ;

        int j = 0 ;
        for (int i = 0 ; i < MONTHNUMBER ;i ++) {
            if ((i+1)>=10){
                timeResult.add(year+ "-" + (i+1)) ;
            }else {
                timeResult.add(year+ "-0" + (i+1)) ;
            }

            // 如果当前时间存在记录，则在对应下标记录总和值，不存在默认总和值为0
            if (time.size()>0&&time.size()>j){
                if (time.get(j).equals(timeResult.get(i))){
                    sumResult.add(sum.get(j)) ;
                    j++;
                }else{
                    sumResult.add(0) ;
                }
            }else{
                sumResult.add(0) ;
            }
        }

        result.put("timeResult",timeResult) ;
        result.put("sumResult",sumResult) ;

        return result ;
    }
}
