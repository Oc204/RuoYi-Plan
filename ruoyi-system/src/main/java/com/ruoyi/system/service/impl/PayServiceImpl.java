package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PayMapper;
import com.ruoyi.system.domain.Pay;
import com.ruoyi.system.service.IPayService;

/**
 * 计划Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
@Service
public class PayServiceImpl implements IPayService 
{
    @Autowired
    private PayMapper payMapper;

    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    @Override
    public Pay selectPayById(Long id)
    {
        return payMapper.selectPayById(id);
    }

    /**
     * 查询计划列表
     * 
     * @param pay 计划
     * @return 计划
     */
    @Override
    public List<Pay> selectPayList(Pay pay)
    {
        return payMapper.selectPayList(pay);
    }

    /**
     * 新增计划
     * 
     * @param pay 计划
     * @return 结果
     */
    @Override
    public int insertPay(Pay pay)
    {
        pay.setCreateTime(DateUtils.getNowDate());
        pay.setUserId(SecurityUtils.getUserId());
        return payMapper.insertPay(pay);
    }

    /**
     * 修改计划
     * 
     * @param pay 计划
     * @return 结果
     */
    @Override
    public int updatePay(Pay pay)
    {
        pay.setUpdateTime(DateUtils.getNowDate());
        return payMapper.updatePay(pay);
    }

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的计划主键
     * @return 结果
     */
    @Override
    public int deletePayByIds(Long[] ids)
    {
        return payMapper.deletePayByIds(ids);
    }

    /**
     * 删除计划信息
     * 
     * @param id 计划主键
     * @return 结果
     */
    @Override
    public int deletePayById(Long id)
    {
        return payMapper.deletePayById(id);
    }
}
