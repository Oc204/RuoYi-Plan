package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图片对象 pic
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
public class Pic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 权重 */
    @Excel(name = "权重")
    private Long weight;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String picPath;

    /** 是否删除 1未删除 0已删除*/
    @Excel(name = "是否删除")
    private Long hasDelete;

    /** 是否审核 1已审核 0未审核*/
    @Excel(name = "是否审批")
    private Long approve;

    /** 下载次数 */
    @Excel(name = "下载次数")
    private Long downloadTimes;

    /** 点击次数 */
    @Excel(name = "点击次数")
    private Long clickTimes;

    /** 是否审核通过 1通过 0未通过*/
    @Excel(name = "是否审核通过")
    private Long isPass;

    public Long getIsPass() {
        return isPass;
    }

    public void setIsPass(Long isPass) {
        this.isPass = isPass;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setPicPath(String picPath) 
    {
        this.picPath = picPath;
    }

    public String getPicPath() 
    {
        return picPath;
    }
    public void setHasDelete(Long hasDelete)
    {
        this.hasDelete = hasDelete;
    }

    public Long getHasDelete()
    {
        return hasDelete;
    }
    public void setApprove(Long approve)
    {
        this.approve = approve;
    }

    public Long getApprove()
    {
        return approve;
    }
    public void setDownloadTimes(Long downloadTimes)
    {
        this.downloadTimes = downloadTimes;
    }

    public Long getClickTimes() {
        return clickTimes;
    }

    public void setClickTimes(Long clickTimes) {
        this.clickTimes = clickTimes;
    }

    public Long getDownloadTimes()
    {
        return downloadTimes;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "id=" + id +
                ", userId=" + userId +
                ", weight=" + weight +
                ", picPath='" + picPath + '\'' +
                ", hasDelete=" + hasDelete +
                ", approve=" + approve +
                ", downloadTimes=" + downloadTimes +
                ", isPass=" + isPass +
                '}';
    }
}
