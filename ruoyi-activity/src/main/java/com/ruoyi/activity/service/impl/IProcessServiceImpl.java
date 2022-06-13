package com.ruoyi.activity.service.impl;

import com.ruoyi.activity.service.IProcessService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IProcessServiceImpl implements IProcessService {

    @Autowired
    private TaskService taskService ;

    @Autowired
    private RuntimeService runtimeService ;

    @Override
    public Task startProcess(String processKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey) ;

        return taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult() ;
    }
}
