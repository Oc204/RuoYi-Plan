package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.Plist;

/**
 * 清单Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface IPlistService 
{
    /**
     * 查询清单
     * 
     * @param id 清单主键
     * @return 清单
     */
    public Plist selectPlistById(Long id);

    /**
     * 查询清单列表
     * 
     * @param plist 清单
     * @return 清单集合
     */
    public List<Plist> selectPlistList(Plist plist);

    /**
     * 新增清单
     * 
     * @param plist 清单
     * @return 结果
     */
    public int insertPlist(Plist plist);

    /**
     * 修改清单
     * 
     * @param plist 清单
     * @return 结果
     */
    public int updatePlist(Plist plist);

    /**
     * 批量删除清单
     * 
     * @param ids 需要删除的清单主键集合
     * @return 结果
     */
    public int deletePlistByIds(Long[] ids);

    /**
     * 删除清单信息
     * 
     * @param id 清单主键
     * @return 结果
     */
    public int deletePlistById(Long id);

    /**
     * 构建前端所需要树结构
     *
     * @param plists 清单列表
     * @return 树结构列表
     */
    public List<Plist> buildPlistTree(List<Plist> plists);

    /**
     * 构建前端树形选择组件的数据
     * @param result 结果
     * @return
     */
    List<TreeSelect> buildPlistTreeSelect(List<Plist> result);
}
