package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlistTaskMapper;
import com.ruoyi.system.domain.PlistTask;
import com.ruoyi.system.service.IPlistTaskService;

/**
 * 清单任务关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class PlistTaskServiceImpl implements IPlistTaskService 
{
    @Autowired
    private PlistTaskMapper plistTaskMapper;

    /**
     * 查询清单任务关联
     * 
     * @param id 清单任务关联主键
     * @return 清单任务关联
     */
    @Override
    public PlistTask selectPlistTaskById(Long id)
    {
        return plistTaskMapper.selectPlistTaskById(id);
    }

    /**
     * 查询清单任务关联列表
     * 
     * @param plistTask 清单任务关联
     * @return 清单任务关联
     */
    @Override
    public List<PlistTask> selectPlistTaskList(PlistTask plistTask)
    {
        return plistTaskMapper.selectPlistTaskList(plistTask);
    }

    /**
     * 新增清单任务关联
     * 
     * @param plistTask 清单任务关联
     * @return 结果
     */
    @Override
    public int insertPlistTask(PlistTask plistTask)
    {
        plistTask.setCreateTime(DateUtils.getNowDate());
        return plistTaskMapper.insertPlistTask(plistTask);
    }

    /**
     * 修改清单任务关联
     * 
     * @param plistTask 清单任务关联
     * @return 结果
     */
    @Override
    public int updatePlistTask(PlistTask plistTask)
    {
        return plistTaskMapper.updatePlistTask(plistTask);
    }

    /**
     * 批量删除清单任务关联
     * 
     * @param ids 需要删除的清单任务关联主键
     * @return 结果
     */
    @Override
    public int deletePlistTaskByIds(Long[] ids)
    {
        return plistTaskMapper.deletePlistTaskByIds(ids);
    }

    /**
     * 删除清单任务关联信息
     * 
     * @param id 清单任务关联主键
     * @return 结果
     */
    @Override
    public int deletePlistTaskById(Long id)
    {
        return plistTaskMapper.deletePlistTaskById(id);
    }
}
