package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_unfreeze;

public interface Ttz_unfreezeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_unfreeze record);

    int insertSelective(Ttz_unfreeze record);

    Ttz_unfreeze selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_unfreeze record);

    int updateByPrimaryKey(Ttz_unfreeze record);
    
    /**
     * 获取userid的已提现金额总数
     * @param userId
     * @return
     */
    Double selectAmountByUserId(Integer userId);
    
    /**
     * 获取 解冻信息,时间，rate,user_id
     * @param map
     * @return
     */
    List<Ttz_unfreeze> selectFreezeInfo(Map<String,Object> map);
    
    /**
     * 获取从未解冻过的解冻表
     * @param map
     * @return
     */
    List<Ttz_unfreeze> getNotunFreezeInfo2(Map<String,Object> map);
    
    /**
     * 批量插入ttz_unfreeze，用于解冻操作
     * @param ttz_unfreezes
     * @return
     */
    int insertUnfreezes(List<Ttz_unfreeze> ttz_unfreezes);
    
    
    /**
     * 对selectFreezeInfo 的补充，获取所有购买过的时间集合，然后过滤掉有解冻信息的情况，rate默认为0
     * @param map
     * @return
     */
    List<Ttz_unfreeze> selectFreezeInfoNoFreeze(Map<String,Object> map);
    
    /**
     * 获取已解冻的金额和
     * @param map
     * @return
     */
    double getYLQAmount(Map<String,Object> map);
}