package com.ruoyi.activity.service.impl;

import com.ruoyi.activity.service.ITaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITaskServiceImpl implements ITaskService {

    @Autowired
    private TaskService taskService ;

    @Override
    public Task complite(String processInstanceId , String nextAssginId) {

        Task task  = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult() ;

        taskService.complete(task.getId());

        Task nextTask = null;
        
        if(null != nextAssginId) {
            nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult() ;
            taskService.setAssignee(nextTask.getId(), nextAssginId);
            
        }
        
        return nextTask ;
    }
}
