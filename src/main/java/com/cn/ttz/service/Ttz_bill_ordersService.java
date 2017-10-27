package com.cn.ttz.service;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Jihes_sys_notification;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_orders;
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
    
    /**
     * 批量生成红包
     * @return
     */
    int insertRedPackages( List<Ttz_bill_orders> list);
    
    
    /**
     * 查询此用户是否参团成功
     * @param userId
     * @return
     */
    Integer  selectIsTuan(Integer userId);
    
    
    /**
     * 每日零点15处理失效红包
     * @param updateTime
     * @return
     */
    int dealExpireRedPacket(Integer updateTime);
    
    
    /**
     * 获取所有未领取的红包
     * @param expireTime
     * @return
     */
    List<Integer> selectWlqRedPacket(Integer expireTime);
    
    /**
     * 批量插入红包提示信息
     * @param records
     * @return
     */
    int insertNotifications(List<Jihes_sys_notification> records);
   
    
    
    /**
     * 获取即将失效的红包
     * @param expireTime
     * @return
     */
    List<Integer> selectWlqRedPacket2(Integer expireTime);
    
    /**
     * 获取失效的订单对应的红包
     * @param createTime
     * @return
     */
    List<Ttz_bill_orders> selectExpireRedPacket(Integer createTime);
    
    
    /**
     * 获取有效的订单数量
     * @param ttz_goods_id
     * @return
     */
    Integer  getValidOrderCount(Integer userId);
    
    
    /**
     * 查询
     * @param userId
     * @return
     */
    Integer selectYLQRedPacket(Integer userId);
    
    
    /**
     * 查询创建时间
     * @param map
     * @return
     */
    int  selectCreateTime(Map<String,Object> map);
}
