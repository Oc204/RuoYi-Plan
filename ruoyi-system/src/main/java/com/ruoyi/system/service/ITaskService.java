package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.Plist;
import com.ruoyi.system.domain.Task;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface ITaskService 
{
    /**
     * 查询任务
     * 
     * @param id 任务主键
     * @return 任务
     */
    public Task selectTaskById(Long id);

    /**
     * 查询任务列表
     * 
     * @param task 任务
     * @return 任务集合
     */
    public List<Task> selectTaskList(Task task);

    /**
     * 新增任务
     * 
     * @param task 任务
     * @return 结果
     */
    public int insertTask(Task task);

    /**
     * 修改任务
     * 
     * @param task 任务
     * @return 结果
     */
    public int updateTask(Task task);

    /**
     * 批量删除任务
     * 
     * @param ids 需要删除的任务主键集合
     * @return 结果
     */
    public int deleteTaskByIds(Long[] ids);

    /**
     * 删除任务信息
     * 
     * @param id 任务主键
     * @return 结果
     */
    public int deleteTaskById(Long id);

    /**
     * 完成任务后更新番茄记录
     * @param taskId 任务id
     * @param timeLong 番茄时长
     * @return
     */
    public int updateTomato(Long taskId , Long timeLong);

    /**
     * 校验任务名称是否唯一
     * @param task 任务实体
     * @return
     */
    String checkTaskNameUnique(Task task);
}
