package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Subtask;

/**
 * 子任务Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface ISubtaskService 
{
    /**
     * 查询子任务
     * 
     * @param id 子任务主键
     * @return 子任务
     */
    public Subtask selectSubtaskById(Long id);

    /**
     * 查询子任务列表
     * 
     * @param subtask 子任务
     * @return 子任务集合
     */
    public List<Subtask> selectSubtaskList(Subtask subtask);

    /**
     * 新增子任务
     * 
     * @param subtask 子任务
     * @return 结果
     */
    public int insertSubtask(Subtask subtask);

    /**
     * 修改子任务
     * 
     * @param subtask 子任务
     * @return 结果
     */
    public int updateSubtask(Subtask subtask);

    /**
     * 批量删除子任务
     * 
     * @param ids 需要删除的子任务主键集合
     * @return 结果
     */
    public int deleteSubtaskByIds(Long[] ids);

    /**
     * 删除子任务信息
     * 
     * @param id 子任务主键
     * @return 结果
     */
    public int deleteSubtaskById(Long id);
}
