package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.Tomato;
import org.apache.ibatis.annotations.Param;

/**
 * 番茄Service接口
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
public interface ITomatoService 
{
    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    public Tomato selectTomatoById(Long id);

    /**
     * 查询系统首页番茄统计数据
     * @return
     */
    public Map<String ,Object> selectTomatoStatisticsByUserId();

    /**
     * 查询番茄列表
     * 
     * @param tomato 番茄
     * @return 番茄集合
     */
    public List<Tomato> selectTomatoList(Tomato tomato);

    /**
     * 新增番茄
     * 
     * @param tomato 番茄
     * @return 结果
     */
    public int insertTomato(Tomato tomato);

    /**
     * 修改番茄
     * 
     * @param tomato 番茄
     * @return 结果
     */
    public int updateTomato(Tomato tomato);

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的番茄主键集合
     * @return 结果
     */
    public int deleteTomatoByIds(Long[] ids);

    /**
     * 删除番茄信息
     * 
     * @param id 番茄主键
     * @return 结果
     */
    public int deleteTomatoById(Long id);

    /**
     * 根据年份获取番茄曲线
     * @param year
     * @return
     */
    List<Map<String, Object>> getTomatoLineByYear(String year, String showYear, String showMonth);

    /**
     * 根据选择的日期标识获取对应的番茄时间分析数据
     * @param showDay
     * @param showWeek
     * @param showMonth
     * @return
     */
    List<Map<String, Object>> getTomatoPieChartsData(String showDay, String showWeek, String showMonth);
}
