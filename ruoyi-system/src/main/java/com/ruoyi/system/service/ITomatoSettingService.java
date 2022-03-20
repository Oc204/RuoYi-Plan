package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TomatoSetting;

/**
 * 番茄Service接口
 * 
 * @author ruoyi
 * @date 2022-03-20
 */
public interface ITomatoSettingService 
{
    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    public TomatoSetting selectTomatoSettingById(Long id);

    public TomatoSetting selectTomatoSettingByUserId();

    /**
     * 查询番茄列表
     * 
     * @param tomatoSetting 番茄
     * @return 番茄集合
     */
    public List<TomatoSetting> selectTomatoSettingList(TomatoSetting tomatoSetting);

    /**
     * 新增番茄
     * 
     * @param tomatoSetting 番茄
     * @return 结果
     */
    public int insertTomatoSetting(TomatoSetting tomatoSetting);

    /**
     * 修改番茄
     * 
     * @param tomatoSetting 番茄
     * @return 结果
     */
    public int updateTomatoSetting(TomatoSetting tomatoSetting);

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的番茄主键集合
     * @return 结果
     */
    public int deleteTomatoSettingByIds(Long[] ids);

    /**
     * 删除番茄信息
     * 
     * @param id 番茄主键
     * @return 结果
     */
    public int deleteTomatoSettingById(Long id);
}
