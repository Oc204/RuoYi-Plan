package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Tomato;

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
}
