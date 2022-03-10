package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TomatoMapper;
import com.ruoyi.system.domain.Tomato;
import com.ruoyi.system.service.ITomatoService;

/**
 * 番茄Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
@Service
public class TomatoServiceImpl implements ITomatoService 
{
    @Autowired
    private TomatoMapper tomatoMapper;

    /**
     * 查询番茄
     * 
     * @param id 番茄主键
     * @return 番茄
     */
    @Override
    public Tomato selectTomatoById(Long id)
    {
        return tomatoMapper.selectTomatoById(id);
    }

    /**
     * 查询番茄列表
     * 
     * @param tomato 番茄
     * @return 番茄
     */
    @Override
    public List<Tomato> selectTomatoList(Tomato tomato)
    {
        return tomatoMapper.selectTomatoList(tomato);
    }

    /**
     * 新增番茄
     * 
     * @param tomato 番茄
     * @return 结果
     */
    @Override
    public int insertTomato(Tomato tomato)
    {
        tomato.setCreateTime(DateUtils.getNowDate());
        tomato.setUserId(SecurityUtils.getUserId());
        return tomatoMapper.insertTomato(tomato);
    }

    /**
     * 修改番茄
     * 
     * @param tomato 番茄
     * @return 结果
     */
    @Override
    public int updateTomato(Tomato tomato)
    {
        tomato.setUpdateTime(DateUtils.getNowDate());
        return tomatoMapper.updateTomato(tomato);
    }

    /**
     * 批量删除番茄
     * 
     * @param ids 需要删除的番茄主键
     * @return 结果
     */
    @Override
    public int deleteTomatoByIds(Long[] ids)
    {
        return tomatoMapper.deleteTomatoByIds(ids);
    }

    /**
     * 删除番茄信息
     * 
     * @param id 番茄主键
     * @return 结果
     */
    @Override
    public int deleteTomatoById(Long id)
    {
        return tomatoMapper.deleteTomatoById(id);
    }
}
