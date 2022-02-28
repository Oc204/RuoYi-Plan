package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Plist;

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
}
