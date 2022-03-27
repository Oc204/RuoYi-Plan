package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.Tomato;
import org.apache.ibatis.annotations.Param;

/**
 * 番茄Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
public interface TomatoMapper 
{
    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    public Tomato selectTomatoById(Long id);

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
     * 删除番茄
     * 
     * @param id 番茄主键
     * @return 结果
     */
    public int deleteTomatoById(Long id);

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTomatoByIds(Long[] ids);

    /**
     * 查询系统首页番茄统计数据
     * @param userId
     * @return
     */
    Map<String, Object> selectTomatoStatisticsByUserId(Long userId);

    /**
     * 根据年份获取番茄曲线
     * @param showYear 年显示标识
     * @param showMonth 月显示标识
     * @param userId 用户id
     * @param year 年份值
     * @return
     */
    List<Map<String, Object>> getTomatoLineByYear(@Param("showYear")String showYear, @Param("showMonth")String showMonth, @Param("userId")Long userId, @Param("year")String year);

    /**
     * 根据时间获取饼图番茄数据
     * @param showDay   日显示标识
     * @param showWeek  周显示标识
     * @param showMonth 月显示标识
     * @param userId    用户id
     * @return
     */
    List<Map<String, Object>> getTomatoPieChartsData(@Param("showDay")String showDay, @Param("showWeek")String showWeek, @Param("showMonth")String showMonth, @Param("userId")Long userId);
}
