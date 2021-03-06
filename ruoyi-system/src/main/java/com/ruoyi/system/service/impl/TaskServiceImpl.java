package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.Plist;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Tomato;
import com.ruoyi.system.service.ITomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.service.ITaskService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ITomatoService tomatoService ;

    /**
     * 查询任务
     *
     * @param id 任务主键
     * @return 任务
     */
    @Override
    public Task selectTaskById(Long id)
    {
        return taskMapper.selectTaskById(id);
    }

    /**
     * 查询任务列表
     * 
     * @param task 任务
     * @return 任务
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 新增任务
     * 
     * @param task 任务
     * @return 结果
     */
    @Override
    public int insertTask(Task task)
    {
        task.setCreateTime(DateUtils.getNowDate());
        task.setUserId(SecurityUtils.getUserId());
        task.setTomatoNumber("0");
        return taskMapper.insertTask(task);
    }

    /**
     * 修改任务
     * 
     * @param task 任务
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除任务
     * 
     * @param ids 需要删除的任务主键
     * @return 结果
     */
    @Override
    public int deleteTaskByIds(Long[] ids)
    {
        return taskMapper.deleteTaskByIds(ids);
    }

    /**
     * 删除任务信息
     * 
     * @param id 任务主键
     * @return 结果
     */
    @Override
    public int deleteTaskById(Long id)
    {
        return taskMapper.deleteTaskById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTomato(Long taskId, Long timeLong) {

        Task task = this.selectTaskById(taskId) ;

        int tomatoNum = 1 ;
        if(!("").equals(task.getTomatoNumber())) {
            tomatoNum = Integer.parseInt(task.getTomatoNumber()) + 1 ;
        }
        task.setTomatoNumber(String.valueOf(tomatoNum));

        Tomato tomato = new Tomato() ;
        tomato.setTaskId(taskId);
        tomato.setTimeLong(timeLong);
        tomatoService.insertTomato(tomato) ;

        this.updateTask(task) ;

        return this.updateTask(task);
    }

    @Override
    public String checkTaskNameUnique(Task task) {

        Long id = StringUtils.isNull(task.getId()) ? -1L : task.getId();
        Task info = taskMapper.checkTaskNameUnique(task.getTaskName(), task.getParentId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
