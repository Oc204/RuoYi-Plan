package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlistMapper;
import com.ruoyi.system.domain.Plist;
import com.ruoyi.system.service.IPlistService;

/**
 * 清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class PlistServiceImpl implements IPlistService 
{
    @Autowired
    private PlistMapper plistMapper;

    /**
     * 查询清单
     * 
     * @param id 清单主键
     * @return 清单
     */
    @Override
    public Plist selectPlistById(Long id)
    {
        return plistMapper.selectPlistById(id);
    }

    /**
     * 查询清单列表
     * 
     * @param plist 清单
     * @return 清单
     */
    @Override
    public List<Plist> selectPlistList(Plist plist)
    {
        return plistMapper.selectPlistList(plist);
    }

    /**
     * 新增清单
     * 
     * @param plist 清单
     * @return 结果
     */
    @Override
    public int insertPlist(Plist plist)
    {
        plist.setCreateTime(DateUtils.getNowDate());
        return plistMapper.insertPlist(plist);
    }

    /**
     * 修改清单
     * 
     * @param plist 清单
     * @return 结果
     */
    @Override
    public int updatePlist(Plist plist)
    {
        plist.setUpdateTime(DateUtils.getNowDate());
        return plistMapper.updatePlist(plist);
    }

    /**
     * 批量删除清单
     * 
     * @param ids 需要删除的清单主键
     * @return 结果
     */
    @Override
    public int deletePlistByIds(Long[] ids)
    {
        return plistMapper.deletePlistByIds(ids);
    }

    /**
     * 删除清单信息
     * 
     * @param id 清单主键
     * @return 结果
     */
    @Override
    public int deletePlistById(Long id)
    {
        return plistMapper.deletePlistById(id);
    }
}
