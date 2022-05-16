package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件对象 file
 * 
 * @author ruoyi
 * @date 2022-04-19
 */
public class File extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 文件地址 */
    @Excel(name = "文件地址")
    private String filePath;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long hasDelete;

    /** 使用的地方 */
    @Excel(name = "使用的地方")
    private String usePlace;

    /** 使用类型 */
    @Excel(name = "使用类型")
    private String usageType;

    /** 文件大小*/
    @Excel(name = "文件大小")
    private Long totalSize;

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
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
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setHasDelete(Long hasDelete) 
    {
        this.hasDelete = hasDelete;
    }

    public Long getHasDelete() 
    {
        return hasDelete;
    }
    public void setUsePlace(String usePlace) 
    {
        this.usePlace = usePlace;
    }

    public String getUsePlace() 
    {
        return usePlace;
    }
    public void setUsageType(String usageType) 
    {
        this.usageType = usageType;
    }

    public String getUsageType() 
    {
        return usageType;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", userId=" + userId +
                ", filePath='" + filePath + '\'' +
                ", hasDelete=" + hasDelete +
                ", usePlace='" + usePlace + '\'' +
                ", usageType='" + usageType + '\'' +
                ", totalSize=" + totalSize +
                '}';
    }
}
