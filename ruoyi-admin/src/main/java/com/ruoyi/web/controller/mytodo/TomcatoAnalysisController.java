package com.ruoyi.web.controller.mytodo;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ITomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tomato/analysis")
public class TomcatoAnalysisController {

    private static final String YEAR = "YEAR"  ;
    private static final String MONTH = "MONTH"  ;
    private static final String WEEK = "WEEK"  ;
    private static final String DAY = "DAY"  ;

    @Autowired
    private ITomatoService tomatoService ;

    @GetMapping("/year/{year}/{flag}")
    public AjaxResult tomatoLine(@PathVariable("year") String year,@PathVariable("flag") String flag){

        if("undefined".equals(year)){
            Calendar cal = Calendar.getInstance();
            year = String.valueOf(cal.get(Calendar.YEAR));
        }

        String showYear = null ;
        String showMonth = null ;
        if(flag.equals(YEAR)){
            showYear = YEAR ;
        } else {
            showMonth = MONTH ;
        }

        List<Map<String,Object>> list = tomatoService.getTomatoLineByYear(year, showYear, showMonth) ;

        List<Object> time = new ArrayList<>() ;
        List<Object> sum = new ArrayList<>() ;

        for(Map<String,Object> item : list){
            time.add(item.get("time")) ;
            sum.add(item.get("sum")) ;
        }

         return AjaxResult.success(setChartsData(year, time, sum, flag)) ;
    }


    @GetMapping(value = "/index/pieCharts/{flag}")
    public AjaxResult getPieChartsData(@PathVariable("flag") String flag) {

        String showDay = null ;
        String showWeek = null ;
        String showMonth = null ;
        if(flag.equals(MONTH)){
            showMonth = MONTH ;
        } else if (flag.equals(WEEK)){
            showWeek = WEEK ;
        } else {
            showDay = DAY ;
        }

        return AjaxResult.success(tomatoService.getTomatoPieChartsData(showDay, showWeek, showMonth)) ;
    }

    /**
     * 根据现有数据构造echarts数据
     * @param year 年份
     * @param time 时间值
     * @param sum  总和
     * @param flag 时间标志
     * @return
     */
    public Map<String, Object[]> setChartsData(String year, List<Object> time, List<Object> sum, String flag) {
        Map<String, Object[]> result = new HashMap<>() ;

        if(flag.equals(YEAR)){
            result = dataToYear(year,time, sum) ;
        } else if(flag.equals(MONTH)){
            result = dataToMonth(year, time, sum) ;
        }

        return result;
    }

    /**
     * 按12个月统计
     * @param year
     * @param time
     * @param sum
     * @return
     */
    public Map<String, Object[]> dataToMonth(String year, List<Object> time, List<Object> sum) {
        List<String> num = new ArrayList<>() ;
        List<Object> targetSum = new ArrayList<>() ;
        Map<String, Object[]> result = new HashMap<>() ;

        int count = 12 ;
        int j = 0 ;
        for (int i = 0 ; i < count ;i ++) {
            if ((i+1)>=10){
                num.add(year+ "-" + (i+1)) ;
            }else {
                num.add(year+ "-0" + (i+1)) ;
            }

            // 如果当前时间存在记录，则在对应下标记录总和值，不存在默认总和值为0
            if (time.size()>0&&time.size()>j){
                if (time.get(j).equals(num.get(i))){
                    targetSum.add(sum.get(j)) ;
                    j++;
                }else{
                    targetSum.add(0) ;
                }
            }else{
                targetSum.add(0) ;
            }
        }

        result.put("time",num.toArray(new Object[num.size()])) ;
        result.put("sum",targetSum.toArray(new Object[targetSum.size()])) ;

        return result ;
    }

    /**
     * 按一年365天统计
     * @param time
     * @param sum
     * @return
     */
    public Map<String, Object[]> dataToYear(String year, List<Object> time, List<Object> sum) {
        List<String> num = new ArrayList<>() ;
        List<Object> targetSum = new ArrayList<>() ;
        Map<String, Object[]> result = new HashMap<>() ;

        for(int i = 0 ; i < 12 ; i++){
            num.addAll(getMonthFullDay(Integer.valueOf(year), i)) ;
        }

        // 一年365天
        int count = 365 ;
        int j = 0 ;
        for (int i = 0 ; i < count ;i ++) {
            // 如果当前时间存在记录，则在对应下标记录总和值，不存在默认总和值为0
            if (time.size()>0&&time.size()>j){
                if (time.get(j).equals(num.get(i))){
                    targetSum.add(sum.get(j)) ;
                    j++;
                }else{
                    targetSum.add(0) ;
                }
            }else{
                targetSum.add(0) ;
            }
        }

        result.put("time",num.toArray(new Object[num.size()])) ;
        result.put("sum",targetSum.toArray(new Object[targetSum.size()])) ;

        return result ;
    }

    /**
     *  java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）
     * @param year
     * @param month
     * @return
     */
    public List<String> getMonthFullDay(int year , int month){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }
}
