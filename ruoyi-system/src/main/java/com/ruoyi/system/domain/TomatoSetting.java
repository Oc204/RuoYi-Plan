package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 番茄对象 tomato_setting
 * 
 * @author ruoyi
 * @date 2022-03-20
 */
public class TomatoSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 番茄时长(最大为60分钟) */
    @Excel(name = "番茄时长(最大为60分钟)")
    private Long tomatoTime;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 是否激活 */
    @Excel(name = "是否激活")
    private Long active;

    /** 番茄钟类别：番茄时长\短时休息\长时休息（默认为25分钟） */
    @Excel(name = "番茄钟类别：番茄时长/短时休息/长时休息", readConverterExp = "默=认为25分钟")
    private Long flag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTomatoTime(Long tomatoTime) 
    {
        this.tomatoTime = tomatoTime;
    }

    public Long getTomatoTime() 
    {
        return tomatoTime;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setActive(Long active) 
    {
        this.active = active;
    }

    public Long getActive() 
    {
        return active;
    }
    public void setFlag(Long flag) 
    {
        this.flag = flag;
    }

    public Long getFlag() 
    {
        return flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tomatoTime", getTomatoTime())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("active", getActive())
            .append("flag", getFlag())
            .toString();
    }
}
