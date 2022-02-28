package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SubtaskMapper;
import com.ruoyi.system.domain.Subtask;
import com.ruoyi.system.service.ISubtaskService;

/**
 * 子任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class SubtaskServiceImpl implements ISubtaskService 
{
    @Autowired
    private SubtaskMapper subtaskMapper;

    /**
     * 查询子任务
     * 
     * @param id 子任务主键
     * @return 子任务
     */
    @Override
    public Subtask selectSubtaskById(Long id)
    {
        return subtaskMapper.selectSubtaskById(id);
    }

    /**
     * 查询子任务列表
     * 
     * @param subtask 子任务
     * @return 子任务
     */
    @Override
    public List<Subtask> selectSubtaskList(Subtask subtask)
    {
        return subtaskMapper.selectSubtaskList(subtask);
    }

    /**
     * 新增子任务
     * 
     * @param subtask 子任务
     * @return 结果
     */
    @Override
    public int insertSubtask(Subtask subtask)
    {
        subtask.setCreateTime(DateUtils.getNowDate());
        return subtaskMapper.insertSubtask(subtask);
    }

    /**
     * 修改子任务
     * 
     * @param subtask 子任务
     * @return 结果
     */
    @Override
    public int updateSubtask(Subtask subtask)
    {
        subtask.setUpdateTime(DateUtils.getNowDate());
        return subtaskMapper.updateSubtask(subtask);
    }

    /**
     * 批量删除子任务
     * 
     * @param ids 需要删除的子任务主键
     * @return 结果
     */
    @Override
    public int deleteSubtaskByIds(Long[] ids)
    {
        return subtaskMapper.deleteSubtaskByIds(ids);
    }

    /**
     * 删除子任务信息
     * 
     * @param id 子任务主键
     * @return 结果
     */
    @Override
    public int deleteSubtaskById(Long id)
    {
        return subtaskMapper.deleteSubtaskById(id);
    }
}
