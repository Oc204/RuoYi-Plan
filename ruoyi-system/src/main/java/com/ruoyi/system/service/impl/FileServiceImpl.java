package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.FileMapper;
import com.ruoyi.system.domain.File;
import com.ruoyi.system.service.IFileService;

/**
 * 文件Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-19
 */
@Service
public class FileServiceImpl implements IFileService 
{
    @Autowired
    private FileMapper fileMapper;

    /**
     * 查询文件
     * 
     * @param id 文件主键
     * @return 文件
     */
    @Override
    public File selectFileById(Long id)
    {
        return fileMapper.selectFileById(id);
    }

    /**
     * 查询文件列表
     * 
     * @param file 文件
     * @return 文件
     */
    @Override
    public List<File> selectFileList(File file)
    {
        return fileMapper.selectFileList(file);
    }

    /**
     * 新增文件
     * 
     * @param file 文件
     * @return 结果
     */
    @Override
    public int insertFile(File file)
    {
        file.setCreateTime(DateUtils.getNowDate());
        return fileMapper.insertFile(file);
    }

    /**
     * 修改文件
     * 
     * @param file 文件
     * @return 结果
     */
    @Override
    public int updateFile(File file)
    {
        file.setUpdateTime(DateUtils.getNowDate());
        return fileMapper.updateFile(file);
    }

    /**
     * 批量删除文件
     * 
     * @param ids 需要删除的文件主键
     * @return 结果
     */
    @Override
    public int deleteFileByIds(Long[] ids)
    {
        return fileMapper.deleteFileByIds(ids);
    }

    /**
     * 删除文件信息
     * 
     * @param id 文件主键
     * @return 结果
     */
    @Override
    public int deleteFileById(Long id)
    {
        return fileMapper.deleteFileById(id);
    }
}
