package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TomatoSetting;

/**
 * 番茄Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-20
 */
public interface TomatoSettingMapper 
{
    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    public TomatoSetting selectTomatoSettingById(Long id);

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
     * 删除番茄
     * 
     * @param id 番茄主键
     * @return 结果
     */
    public int deleteTomatoSettingById(Long id);

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTomatoSettingByIds(Long[] ids);

    /**
     * 根据用户查询番茄设置
     * @param userId 用户id
     * @return
     */
    public TomatoSetting selectTomatoSettingByUserId(Long userId);
}
