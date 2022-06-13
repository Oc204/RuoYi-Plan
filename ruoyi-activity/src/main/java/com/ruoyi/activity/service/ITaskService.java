package com.ruoyi.activity.service;

import org.activiti.engine.task.Task;

public interface ITaskService {

    public Task complite(String processInstanceId, String nextAssginId) ;

}
