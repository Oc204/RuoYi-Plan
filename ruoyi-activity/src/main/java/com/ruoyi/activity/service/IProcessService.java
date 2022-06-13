package com.ruoyi.activity.service;

import org.activiti.engine.task.Task;

public interface IProcessService {

    public Task startProcess(String processKey) ;

}
