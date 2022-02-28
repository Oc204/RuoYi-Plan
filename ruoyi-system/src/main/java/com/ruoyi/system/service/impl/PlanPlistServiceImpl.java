package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlanPlistMapper;
import com.ruoyi.system.domain.PlanPlist;
import com.ruoyi.system.service.IPlanPlistService;

/**
 * 计划清单关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class PlanPlistServiceImpl implements IPlanPlistService 
{
    @Autowired
    private PlanPlistMapper planPlistMapper;

    /**
     * 查询计划清单关联
     * 
     * @param id 计划清单关联主键
     * @return 计划清单关联
     */
    @Override
    public PlanPlist selectPlanPlistById(Long id)
    {
        return planPlistMapper.selectPlanPlistById(id);
    }

    /**
     * 查询计划清单关联列表
     * 
     * @param planPlist 计划清单关联
     * @return 计划清单关联
     */
    @Override
    public List<PlanPlist> selectPlanPlistList(PlanPlist planPlist)
    {
        return planPlistMapper.selectPlanPlistList(planPlist);
    }

    /**
     * 新增计划清单关联
     * 
     * @param planPlist 计划清单关联
     * @return 结果
     */
    @Override
    public int insertPlanPlist(PlanPlist planPlist)
    {
        planPlist.setCreateTime(DateUtils.getNowDate());
        return planPlistMapper.insertPlanPlist(planPlist);
    }

    /**
     * 修改计划清单关联
     * 
     * @param planPlist 计划清单关联
     * @return 结果
     */
    @Override
    public int updatePlanPlist(PlanPlist planPlist)
    {
        return planPlistMapper.updatePlanPlist(planPlist);
    }

    /**
     * 批量删除计划清单关联
     * 
     * @param ids 需要删除的计划清单关联主键
     * @return 结果
     */
    @Override
    public int deletePlanPlistByIds(Long[] ids)
    {
        return planPlistMapper.deletePlanPlistByIds(ids);
    }

    /**
     * 删除计划清单关联信息
     * 
     * @param id 计划清单关联主键
     * @return 结果
     */
    @Override
    public int deletePlanPlistById(Long id)
    {
        return planPlistMapper.deletePlanPlistById(id);
    }
}
