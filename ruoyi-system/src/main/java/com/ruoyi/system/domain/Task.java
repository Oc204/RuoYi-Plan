package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务对象 task
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public class Task extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 清单名称 */
    @Excel(name = "清单名称")
    private String taskName;

    /** 番茄数 */
    @Excel(name = "番茄数")
    private String tomatoNumber;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 优先级 */
    @Excel(name = "优先级")
    private String level;

    /** 是否完成 */
    @Excel(name = "是否完成")
    private String finish;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 清单id */
    @Excel(name = "清单id")
    private Long plistId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long weight;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long parentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setTomatoNumber(String tomatoNumber) 
    {
        this.tomatoNumber = tomatoNumber;
    }

    public String getTomatoNumber() 
    {
        return tomatoNumber;
    }
    public void setDueDate(Date dueDate) 
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate() 
    {
        return dueDate;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setFinish(String finish) 
    {
        this.finish = finish;
    }

    public String getFinish() 
    {
        return finish;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPlistId(Long plistId) 
    {
        this.plistId = plistId;
    }

    public Long getPlistId() 
    {
        return plistId;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("tomatoNumber", getTomatoNumber())
            .append("dueDate", getDueDate())
            .append("level", getLevel())
            .append("finish", getFinish())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("plistId", getPlistId())
            .append("weight", getWeight())
            .append("parentId", getParentId())
            .toString();
    }
}
