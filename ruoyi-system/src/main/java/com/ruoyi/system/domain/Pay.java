package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 计划对象 pay
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
public class Pay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 支出名称 */
    @Excel(name = "支出名称")
    private String payName;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long weight;

    /** 支出日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支出日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /** 支出明细 */
    @Excel(name = "支出明细")
    private BigDecimal payDetail;

    /** 关联图片 */
    @Excel(name = "关联图片")
    private String payPic;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPayName(String payName) 
    {
        this.payName = payName;
    }

    public String getPayName() 
    {
        return payName;
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
    public void setPayDate(Date payDate) 
    {
        this.payDate = payDate;
    }

    public Date getPayDate() 
    {
        return payDate;
    }
    public void setPayDetail(BigDecimal payDetail) 
    {
        this.payDetail = payDetail;
    }

    public BigDecimal getPayDetail() 
    {
        return payDetail;
    }
    public void setPayPic(String payPic) 
    {
        this.payPic = payPic;
    }

    public String getPayPic() 
    {
        return payPic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("payName", getPayName())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("weight", getWeight())
            .append("payDate", getPayDate())
            .append("payDetail", getPayDetail())
            .append("payPic", getPayPic())
            .toString();
    }
}
