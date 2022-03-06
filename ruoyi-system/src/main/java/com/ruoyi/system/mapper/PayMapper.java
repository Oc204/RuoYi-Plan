package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Pay;

/**
 * 计划Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
public interface PayMapper 
{
    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    public Pay selectPayById(Long id);

    /**
     * 查询计划列表
     * 
     * @param pay 计划
     * @return 计划集合
     */
    public List<Pay> selectPayList(Pay pay);

    /**
     * 新增计划
     * 
     * @param pay 计划
     * @return 结果
     */
    public int insertPay(Pay pay);

    /**
     * 修改计划
     * 
     * @param pay 计划
     * @return 结果
     */
    public int updatePay(Pay pay);

    /**
     * 删除计划
     * 
     * @param id 计划主键
     * @return 结果
     */
    public int deletePayById(Long id);

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayByIds(Long[] ids);
}
