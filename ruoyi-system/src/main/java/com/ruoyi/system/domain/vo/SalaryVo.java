package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 计划对象 salary
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public class SalaryVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工资名称 */
    @Excel(name = "工资名称")
    private String salaryName;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long weight;

    /** 发薪日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发薪日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salaryDate;

    /** 发薪明细
    */
    @Excel(name = "发薪明细 ")
    private BigDecimal salaryDetail;

    /** 关联图片 */
    @Excel(name = "关联图片")
    private List<String> salaryPic;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSalaryName(String salaryName) 
    {
        this.salaryName = salaryName;
    }

    public String getSalaryName() 
    {
        return salaryName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setSalaryDate(Date salaryDate) 
    {
        this.salaryDate = salaryDate;
    }

    public Date getSalaryDate() 
    {
        return salaryDate;
    }
    public void setSalaryDetail(BigDecimal salaryDetail)
    {
        this.salaryDetail = salaryDetail;
    }

    public BigDecimal getSalaryDetail()
    {
        return salaryDetail;
    }
    public void setSalaryPic(List<String> salaryPic)
    {
        this.salaryPic = salaryPic;
    }

    public List<String> getSalaryPic()
    {
        return salaryPic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salaryName", getSalaryName())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("weight", getWeight())
            .append("salaryDate", getSalaryDate())
            .append("salaryDetail", getSalaryDetail())
            .append("salaryPic", getSalaryPic())
            .toString();
    }
}
