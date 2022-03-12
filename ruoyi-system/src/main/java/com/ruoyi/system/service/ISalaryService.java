package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.Salary;

/**
 * 计划Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface ISalaryService 
{
    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    public Salary selectSalaryById(Long id);

    /**
     * 查询计划列表
     * 
     * @param salary 计划
     * @return 计划集合
     */
    public List<Salary> selectSalaryList(Salary salary);

    /**
     * 新增计划
     * 
     * @param salary 计划
     * @return 结果
     */
    public int insertSalary(Salary salary);

    /**
     * 修改计划
     * 
     * @param salary 计划
     * @return 结果
     */
    public int updateSalary(Salary salary);

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的计划主键集合
     * @return 结果
     */
    public int deleteSalaryByIds(Long[] ids);

    /**
     * 删除计划信息
     * 
     * @param id 计划主键
     * @return 结果
     */
    public int deleteSalaryById(Long id);

    /**
     * 按年分析收入情况
     * @param year
     * @return
     */
    Map<String ,Object> anaylysisByYear(String year);

    /**
     * 按月分析收入情况
     * @param salary
     * @return
     */
    String anaylysisByMonth(Salary salary);
}
