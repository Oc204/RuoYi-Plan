package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.dto.ChunkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ChunkInfoMapper;
import com.ruoyi.system.service.IChunkInfoService;

/**
 * 文件切片Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-16
 */
@Service
public class ChunkInfoServiceImpl implements IChunkInfoService 
{
    @Autowired
    private ChunkInfoMapper chunkInfoMapper;

    /**
     * 查询文件切片
     * 
     * @param id 文件切片主键
     * @return 文件切片
     */
    @Override
    public ChunkInfo selectChunkInfoById(String id)
    {
        return chunkInfoMapper.selectChunkInfoById(id);
    }

    /**
     * 查询文件切片列表
     * 
     * @param chunkInfo 文件切片
     * @return 文件切片
     */
    @Override
    public List<ChunkInfo> selectChunkInfoList(ChunkInfo chunkInfo)
    {
        return chunkInfoMapper.selectChunkInfoList(chunkInfo);
    }

    /**
     * 新增文件切片
     * 
     * @param chunkInfo 文件切片
     * @return 结果
     */
    @Override
    public int insertChunkInfo(ChunkInfo chunkInfo)
    {
        return chunkInfoMapper.insertChunkInfo(chunkInfo);
    }

    /**
     * 修改文件切片
     * 
     * @param chunkInfo 文件切片
     * @return 结果
     */
    @Override
    public int updateChunkInfo(ChunkInfo chunkInfo)
    {
        return chunkInfoMapper.updateChunkInfo(chunkInfo);
    }

    /**
     * 批量删除文件切片
     * 
     * @param ids 需要删除的文件切片主键
     * @return 结果
     */
    @Override
    public int deleteChunkInfoByIds(String[] ids)
    {
        return chunkInfoMapper.deleteChunkInfoByIds(ids);
    }

    /**
     * 删除文件切片信息
     * 
     * @param id 文件切片主键
     * @return 结果
     */
    @Override
    public int deleteChunkInfoById(String id)
    {
        return chunkInfoMapper.deleteChunkInfoById(id);
    }
}
