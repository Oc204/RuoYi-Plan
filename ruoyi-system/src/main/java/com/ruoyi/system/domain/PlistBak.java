package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 清单对象 plist
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public class PlistBak extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 清单名称 */
    @Excel(name = "清单名称")
    private String listName;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 计划id */
    @Excel(name = "计划id")
    private Long planId;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long weight;

    /** 父节点id */
    @Excel(name = "父节点id")
    private Long parentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setListName(String listName) 
    {
        this.listName = listName;
    }

    public String getListName() 
    {
        return listName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
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
            .append("listName", getListName())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("planId", getPlanId())
            .append("weight", getWeight())
            .append("parentId", getParentId())
            .toString();
    }
}
