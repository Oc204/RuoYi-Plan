package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.Plist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 清单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface PlistMapper 
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
     * 根据ID查询所有清单
     *
     * @param id 清单ID
     * @return 清单列表
     */
    public List<Plist> selectChildrenPlistById(Long id);

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
     * 删除清单
     * 
     * @param id 清单主键
     * @return 结果
     */
    public int deletePlistById(Long id);

    /**
     * 批量删除清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlistByIds(Long[] ids);

    /**
     * 修改子元素关系
     * @param children
     * @return
     */
    public int updatePlistChildren(@Param("plist") List<Plist> children);
}
