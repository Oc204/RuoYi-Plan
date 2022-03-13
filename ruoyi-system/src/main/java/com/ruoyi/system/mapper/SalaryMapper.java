package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.Salary;
import org.apache.ibatis.annotations.Param;

/**
 * 计划Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface SalaryMapper 
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
     * 删除计划
     * 
     * @param id 计划主键
     * @return 结果
     */
    public int deleteSalaryById(Long id);

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryByIds(Long[] ids);

    String anaylysisByMonth(Salary salary);

    List<Map<String ,Object>> anaylysisByYear(@Param("userId")Long userId, @Param("year")String year);
}
