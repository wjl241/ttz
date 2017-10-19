package com.cn.ttz.service;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_unfreeze;

public interface Ttz_bill_ordersService {
	/**
	 * 查询已领取红包总数 status =2
	 * @param map
	 * @return
	 */
	double selectBillOrderAmout(Map<String,Object> map);
	
	/**
	 * 查询待领取红包数 status =1
	 * @param map
	 * @return
	 */
	int selectBillOrderCount(Map<String,Object> map);
	
	/**
     * 获取userid的已提现金额总数
     * @param userId
     * @return
     */
	Double selectAmountByUserId(Integer userId);
	
	/**
	 * 获取最大的六个金额得主
	 * @return
	 */
	List<Ttz_bill_orders> selectMaxAmounts();
	
	 /**
     * 获取 解冻信息,时间，rate,user_id
     * @param map
     * @return
     */
    List<Ttz_unfreeze> selectFreezeInfo(Map<String,Object> map);
    
    /**
     * 领红包
     * @param map
     * @return
     */
    List<Ttz_bill_orders> getRedPacket(Map<String, Object> map);
    
    /**
     * 批量更新红包状态为已领取
     * @param map
     * @return
     */
    int updateRedPacket(Map<String, Object> map);
    
    /**
     * 获取未解冻红包信息
     * @param map
     * @return
     */
    List<Ttz_unfreeze> getNotunFreezeInfo(Map<String,Object> map);
    
    /**
     * 批量插入ttz_unfreeze，用于解冻操作
     * @param ttz_unfreezes
     * @return
     */
    int insertUnfreezes(List<Ttz_unfreeze> ttz_unfreezes);
    
    /**
     * 获取第一个红包
     * @param user_id
     * @return
     */
    List<Ttz_bill_orders> selectFirstBill(Map<String, Object> map);
}
