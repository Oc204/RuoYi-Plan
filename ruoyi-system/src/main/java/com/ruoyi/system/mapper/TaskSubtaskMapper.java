package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TaskSubtask;

/**
 * 任务子任务关联Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface TaskSubtaskMapper 
{
    /**
     * 查询任务子任务关联
     * 
     * @param id 任务子任务关联主键
     * @return 任务子任务关联
     */
    public TaskSubtask selectTaskSubtaskById(Long id);

    /**
     * 查询任务子任务关联列表
     * 
     * @param taskSubtask 任务子任务关联
     * @return 任务子任务关联集合
     */
    public List<TaskSubtask> selectTaskSubtaskList(TaskSubtask taskSubtask);

    /**
     * 新增任务子任务关联
     * 
     * @param taskSubtask 任务子任务关联
     * @return 结果
     */
    public int insertTaskSubtask(TaskSubtask taskSubtask);

    /**
     * 修改任务子任务关联
     * 
     * @param taskSubtask 任务子任务关联
     * @return 结果
     */
    public int updateTaskSubtask(TaskSubtask taskSubtask);

    /**
     * 删除任务子任务关联
     * 
     * @param id 任务子任务关联主键
     * @return 结果
     */
    public int deleteTaskSubtaskById(Long id);

    /**
     * 批量删除任务子任务关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskSubtaskByIds(Long[] ids);
}
