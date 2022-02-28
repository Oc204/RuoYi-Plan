package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SalaryMapper;
import com.ruoyi.system.domain.Salary;
import com.ruoyi.system.service.ISalaryService;

/**
 * 计划Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
@Service
public class SalaryServiceImpl implements ISalaryService 
{
    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    @Override
    public Salary selectSalaryById(Long id)
    {
        return salaryMapper.selectSalaryById(id);
    }

    /**
     * 查询计划列表
     * 
     * @param salary 计划
     * @return 计划
     */
    @Override
    public List<Salary> selectSalaryList(Salary salary)
    {
        return salaryMapper.selectSalaryList(salary);
    }

    /**
     * 新增计划
     * 
     * @param salary 计划
     * @return 结果
     */
    @Override
    public int insertSalary(Salary salary)
    {
        salary.setCreateTime(DateUtils.getNowDate());
        return salaryMapper.insertSalary(salary);
    }

    /**
     * 修改计划
     * 
     * @param salary 计划
     * @return 结果
     */
    @Override
    public int updateSalary(Salary salary)
    {
        salary.setUpdateTime(DateUtils.getNowDate());
        return salaryMapper.updateSalary(salary);
    }

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的计划主键
     * @return 结果
     */
    @Override
    public int deleteSalaryByIds(Long[] ids)
    {
        return salaryMapper.deleteSalaryByIds(ids);
    }

    /**
     * 删除计划信息
     * 
     * @param id 计划主键
     * @return 结果
     */
    @Override
    public int deleteSalaryById(Long id)
    {
        return salaryMapper.deleteSalaryById(id);
    }
}
