package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PlistTask;

/**
 * 清单任务关联Service接口
 * 
 * @author ruoyi
 * @date 2022-03-01
 */
public interface IPlistTaskService 
{
    /**
     * 查询清单任务关联
     * 
     * @param id 清单任务关联主键
     * @return 清单任务关联
     */
    public PlistTask selectPlistTaskById(Long id);

    /**
     * 查询清单任务关联列表
     * 
     * @param plistTask 清单任务关联
     * @return 清单任务关联集合
     */
    public List<PlistTask> selectPlistTaskList(PlistTask plistTask);

    /**
     * 新增清单任务关联
     * 
     * @param plistTask 清单任务关联
     * @return 结果
     */
    public int insertPlistTask(PlistTask plistTask);

    /**
     * 修改清单任务关联
     * 
     * @param plistTask 清单任务关联
     * @return 结果
     */
    public int updatePlistTask(PlistTask plistTask);

    /**
     * 批量删除清单任务关联
     * 
     * @param ids 需要删除的清单任务关联主键集合
     * @return 结果
     */
    public int deletePlistTaskByIds(Long[] ids);

    /**
     * 删除清单任务关联信息
     * 
     * @param id 清单任务关联主键
     * @return 结果
     */
    public int deletePlistTaskById(Long id);
}
