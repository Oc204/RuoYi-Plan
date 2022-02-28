package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TaskSubtaskMapper;
import com.ruoyi.system.domain.TaskSubtask;
import com.ruoyi.system.service.ITaskSubtaskService;

/**
 * 任务子任务关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class TaskSubtaskServiceImpl implements ITaskSubtaskService 
{
    @Autowired
    private TaskSubtaskMapper taskSubtaskMapper;

    /**
     * 查询任务子任务关联
     * 
     * @param id 任务子任务关联主键
     * @return 任务子任务关联
     */
    @Override
    public TaskSubtask selectTaskSubtaskById(Long id)
    {
        return taskSubtaskMapper.selectTaskSubtaskById(id);
    }

    /**
     * 查询任务子任务关联列表
     * 
     * @param taskSubtask 任务子任务关联
     * @return 任务子任务关联
     */
    @Override
    public List<TaskSubtask> selectTaskSubtaskList(TaskSubtask taskSubtask)
    {
        return taskSubtaskMapper.selectTaskSubtaskList(taskSubtask);
    }

    /**
     * 新增任务子任务关联
     * 
     * @param taskSubtask 任务子任务关联
     * @return 结果
     */
    @Override
    public int insertTaskSubtask(TaskSubtask taskSubtask)
    {
        taskSubtask.setCreateTime(DateUtils.getNowDate());
        return taskSubtaskMapper.insertTaskSubtask(taskSubtask);
    }

    /**
     * 修改任务子任务关联
     * 
     * @param taskSubtask 任务子任务关联
     * @return 结果
     */
    @Override
    public int updateTaskSubtask(TaskSubtask taskSubtask)
    {
        return taskSubtaskMapper.updateTaskSubtask(taskSubtask);
    }

    /**
     * 批量删除任务子任务关联
     * 
     * @param ids 需要删除的任务子任务关联主键
     * @return 结果
     */
    @Override
    public int deleteTaskSubtaskByIds(Long[] ids)
    {
        return taskSubtaskMapper.deleteTaskSubtaskByIds(ids);
    }

    /**
     * 删除任务子任务关联信息
     * 
     * @param id 任务子任务关联主键
     * @return 结果
     */
    @Override
    public int deleteTaskSubtaskById(Long id)
    {
        return taskSubtaskMapper.deleteTaskSubtaskById(id);
    }
}
