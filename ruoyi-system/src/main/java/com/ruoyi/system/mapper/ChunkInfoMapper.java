package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.dto.ChunkInfo;

import java.util.List;

/**
 * 文件切片Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-16
 */
public interface ChunkInfoMapper 
{
    /**
     * 查询文件切片
     * 
     * @param id 文件切片主键
     * @return 文件切片
     */
    public ChunkInfo selectChunkInfoById(String id);

    /**
     * 查询文件切片列表
     * 
     * @param chunkInfo 文件切片
     * @return 文件切片集合
     */
    public List<ChunkInfo> selectChunkInfoList(ChunkInfo chunkInfo);

    /**
     * 新增文件切片
     * 
     * @param chunkInfo 文件切片
     * @return 结果
     */
    public int insertChunkInfo(ChunkInfo chunkInfo);

    /**
     * 修改文件切片
     * 
     * @param chunkInfo 文件切片
     * @return 结果
     */
    public int updateChunkInfo(ChunkInfo chunkInfo);

    /**
     * 删除文件切片
     * 
     * @param id 文件切片主键
     * @return 结果
     */
    public int deleteChunkInfoById(String id);

    /**
     * 批量删除文件切片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChunkInfoByIds(String[] ids);
}
