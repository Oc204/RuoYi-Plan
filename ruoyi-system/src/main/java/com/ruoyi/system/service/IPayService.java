package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.Pay;

/**
 * 支出Service接口
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
public interface IPayService 
{
    /**
     * 查询支出
     * 
     * @param id 支出主键
     * @return 支出
     */
    public Pay selectPayById(Long id);

    /**
     * 查询支出列表
     * 
     * @param pay 支出
     * @return 支出集合
     */
    public List<Pay> selectPayList(Pay pay);

    /**
     * 根据时间段查询支出记录
     * @param year 所属年份
     * @param month 所属月份
     * @return
     */
    public List<Pay> selectPayListByTime(String year,String month);

    /**
     * 新增支出
     * 
     * @param pay 支出
     * @return 结果
     */
    public int insertPay(Pay pay);

    /**
     * 修改支出
     * 
     * @param pay 支出
     * @return 结果
     */
    public int updatePay(Pay pay);

    /**
     * 批量删除支出
     * 
     * @param ids 需要删除的支出主键集合
     * @return 结果
     */
    public int deletePayByIds(Long[] ids);

    /**
     * 删除支出信息
     * 
     * @param id 支出主键
     * @return 结果
     */
    public int deletePayById(Long id);

    /**
     * 按年分析支出情况
     * @param year
     * @return
     */
    List<Map<String ,Object>> anaylysisByYear(String year);
}
