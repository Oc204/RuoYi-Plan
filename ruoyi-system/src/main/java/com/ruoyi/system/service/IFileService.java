package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.File;

/**
 * 文件Service接口
 * 
 * @author ruoyi
 * @date 2022-04-19
 */
public interface IFileService 
{
    /**
     * 查询文件
     * 
     * @param id 文件主键
     * @return 文件
     */
    public File selectFileById(Long id);

    /**
     * 查询文件列表
     * 
     * @param file 文件
     * @return 文件集合
     */
    public List<File> selectFileList(File file);

    /**
     * 新增文件
     * 
     * @param file 文件
     * @return 结果
     */
    public int insertFile(File file);

    /**
     * 修改文件
     * 
     * @param file 文件
     * @return 结果
     */
    public int updateFile(File file);

    /**
     * 批量删除文件
     * 
     * @param ids 需要删除的文件主键集合
     * @return 结果
     */
    public int deleteFileByIds(Long[] ids);

    /**
     * 删除文件信息
     * 
     * @param id 文件主键
     * @return 结果
     */
    public int deleteFileById(Long id);
}
