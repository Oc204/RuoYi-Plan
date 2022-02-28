package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PlanPlist;

/**
 * 计划清单关联Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface IPlanPlistService 
{
    /**
     * 查询计划清单关联
     * 
     * @param id 计划清单关联主键
     * @return 计划清单关联
     */
    public PlanPlist selectPlanPlistById(Long id);

    /**
     * 查询计划清单关联列表
     * 
     * @param planPlist 计划清单关联
     * @return 计划清单关联集合
     */
    public List<PlanPlist> selectPlanPlistList(PlanPlist planPlist);

    /**
     * 新增计划清单关联
     * 
     * @param planPlist 计划清单关联
     * @return 结果
     */
    public int insertPlanPlist(PlanPlist planPlist);

    /**
     * 修改计划清单关联
     * 
     * @param planPlist 计划清单关联
     * @return 结果
     */
    public int updatePlanPlist(PlanPlist planPlist);

    /**
     * 批量删除计划清单关联
     * 
     * @param ids 需要删除的计划清单关联主键集合
     * @return 结果
     */
    public int deletePlanPlistByIds(Long[] ids);

    /**
     * 删除计划清单关联信息
     * 
     * @param id 计划清单关联主键
     * @return 结果
     */
    public int deletePlanPlistById(Long id);
}
