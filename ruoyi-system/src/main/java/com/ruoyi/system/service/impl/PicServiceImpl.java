package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PicMapper;
import com.ruoyi.system.domain.Pic;
import com.ruoyi.system.service.IPicService;

/**
 * 图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
@Service
public class PicServiceImpl implements IPicService 
{
    @Autowired
    private PicMapper picMapper;

    /**
     * 查询图片
     * 
     * @param id 图片主键
     * @return 图片
     */
    @Override
    public Pic selectPicById(Long id)
    {
        return picMapper.selectPicById(id);
    }

    /**
     * 查询图片列表
     * 
     * @param pic 图片
     * @return 图片
     */
    @Override
    public List<Pic> selectPicList(Pic pic)
    {
        return picMapper.selectPicList(pic);
    }

    /**
     * 新增图片
     * 
     * @param pic 图片
     * @return 结果
     */
    @Override
    public int insertPic(Pic pic)
    {
        pic.setCreateTime(DateUtils.getNowDate());
        return picMapper.insertPic(pic);
    }

    /**
     * 修改图片
     * 
     * @param pic 图片
     * @return 结果
     */
    @Override
    public int updatePic(Pic pic)
    {
        pic.setUpdateTime(DateUtils.getNowDate());
        return picMapper.updatePic(pic);
    }

    /**
     * 批量删除图片
     * 
     * @param ids 需要删除的图片主键
     * @return 结果
     */
    @Override
    public int deletePicByIds(Long[] ids)
    {
        return picMapper.deletePicByIds(ids);
    }

    /**
     * 删除图片信息
     * 
     * @param id 图片主键
     * @return 结果
     */
    @Override
    public int deletePicById(Long id)
    {
        return picMapper.deletePicById(id);
    }
}
