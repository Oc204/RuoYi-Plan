package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TomatoSettingMapper;
import com.ruoyi.system.domain.TomatoSetting;
import com.ruoyi.system.service.ITomatoSettingService;

/**
 * 番茄Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-20
 */
@Service
public class TomatoSettingServiceImpl implements ITomatoSettingService 
{
    @Autowired
    private TomatoSettingMapper tomatoSettingMapper;

    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    @Override
    public TomatoSetting selectTomatoSettingById(Long id)
    {
        return tomatoSettingMapper.selectTomatoSettingById(id);
    }

    @Override
    public TomatoSetting selectTomatoSettingByUserId() {

        return tomatoSettingMapper.selectTomatoSettingByUserId(SecurityUtils.getUserId()) ;
    }

    /**
     * 查询番茄列表
     * 
     * @param tomatoSetting 番茄
     * @return 番茄
     */
    @Override
    public List<TomatoSetting> selectTomatoSettingList(TomatoSetting tomatoSetting)
    {
        return tomatoSettingMapper.selectTomatoSettingList(tomatoSetting);
    }

    /**
     * 新增番茄
     * 
     * @param tomatoSetting 番茄
     * @return 结果
     */
    @Override
    public int insertTomatoSetting(TomatoSetting tomatoSetting)
    {
        tomatoSetting.setCreateTime(DateUtils.getNowDate());
        tomatoSetting.setUserId(SecurityUtils.getUserId());
        tomatoSetting.setCreateBy(SecurityUtils.getUsername());
        return tomatoSettingMapper.insertTomatoSetting(tomatoSetting);
    }

    /**
     * 修改番茄
     * 
     * @param tomatoSetting 番茄
     * @return 结果
     */
    @Override
    public int updateTomatoSetting(TomatoSetting tomatoSetting)
    {
        tomatoSetting.setUpdateTime(DateUtils.getNowDate());
        tomatoSetting.setUpdateBy(SecurityUtils.getUsername());
        return tomatoSettingMapper.updateTomatoSetting(tomatoSetting);
    }

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的番茄主键
     * @return 结果
     */
    @Override
    public int deleteTomatoSettingByIds(Long[] ids)
    {
        return tomatoSettingMapper.deleteTomatoSettingByIds(ids);
    }

    /**
     * 删除番茄信息
     * 
     * @param id 番茄主键
     * @return 结果
     */
    @Override
    public int deleteTomatoSettingById(Long id)
    {
        return tomatoSettingMapper.deleteTomatoSettingById(id);
    }
}
