package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.Plist;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlistMapper;
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
        plist.setUserId(SecurityUtils.getUserId());
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

    @Override
    public List<Plist> buildPlistTree(List<Plist> plists) {

        List<Plist> returnList = new ArrayList<Plist>();
        List<Long> tempList = new ArrayList<Long>();
        for (Plist plist : plists)
        {
            tempList.add(plist.getId());
        }
        for (Plist plist : plists)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(plist.getParentId()))
            {
                recursionFn(plists, plist);
                returnList.add(plist);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = plists;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildPlistTreeSelect(List<Plist> result) {

        List<Plist> deptTrees = buildPlistTree(result);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Plist> list, Plist t)
    {
        // 得到子节点列表
        List<Plist> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Plist tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Plist> getChildList(List<Plist> list, Plist t)
    {
        List<Plist> tlist = new ArrayList<Plist>();
        Iterator<Plist> it = list.iterator();
        while (it.hasNext())
        {
            Plist n = (Plist) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Plist> list, Plist t)
    {
        return getChildList(list, t).size() > 0;
    }
}
