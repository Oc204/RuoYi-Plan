package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务子任务关联对象 task_subtask
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public class TaskSubtask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;

    /** 子任务单id */
    @Excel(name = "子任务单id")
    private Long subtaskId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setSubtaskId(Long subtaskId) 
    {
        this.subtaskId = subtaskId;
    }

    public Long getSubtaskId() 
    {
        return subtaskId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("subtaskId", getSubtaskId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
