package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Pic;

/**
 * 图片Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
public interface PicMapper 
{
    /**
     * 查询图片
     * 
     * @param id 图片主键
     * @return 图片
     */
    public Pic selectPicById(Long id);

    /**
     * 查询图片列表
     * 
     * @param pic 图片
     * @return 图片集合
     */
    public List<Pic> selectPicList(Pic pic);

    /**
     * 新增图片
     * 
     * @param pic 图片
     * @return 结果
     */
    public int insertPic(Pic pic);

    /**
     * 修改图片
     * 
     * @param pic 图片
     * @return 结果
     */
    public int updatePic(Pic pic);

    /**
     * 删除图片
     * 
     * @param id 图片主键
     * @return 结果
     */
    public int deletePicById(Long id);

    /**
     * 批量删除图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePicByIds(Long[] ids);

    /**
     * 获取公开的图片列表
     * @param pic
     * @return
     */
    List<Pic> selectPicPublicList(Pic pic);

    /**
     * 批量通过审核
     * @param ids 审核通过的主键集合
     * @return
     */
    int approvePassByIds(Long[] ids);

    /**
     * 批量不通过审核
     * @param ids 审核不通过的图片主键集合
     * @return
     */
    int approveNoPassByIds(Long[] ids);

    /**
     * 图片增加点击数
     * @param id pic id
     */
    void addPicClickNum(Long id);
}
