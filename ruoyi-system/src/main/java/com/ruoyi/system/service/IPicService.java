package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Pic;

/**
 * 图片Service接口
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
public interface IPicService 
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
     * 批量删除图片
     * 
     * @param ids 需要删除的图片主键集合
     * @return 结果
     */
    public int deletePicByIds(Long[] ids);

    /**
     * 删除图片信息
     * 
     * @param id 图片主键
     * @return 结果
     */
    public int deletePicById(Long id);

    /**
     * 获取公开的图片列表
     * @param pic
     * @return
     */
    List<Pic> selectPicPublicList(Pic pic);
}
