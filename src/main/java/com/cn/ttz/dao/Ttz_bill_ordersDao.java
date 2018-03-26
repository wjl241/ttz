package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_bill_orders;

public interface Ttz_bill_ordersDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_bill_orders record);

    int insertSelective(Ttz_bill_orders record);

    Ttz_bill_orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_bill_orders record);

    int updateByPrimaryKey(Ttz_bill_orders record);
    
    int selectByGoodId(Map<String,Object> map);
    
    double selectBillOrderAmout(Map<String,Object> map);
    
    int selectBillOrderCount(Map<String, Object> map);
    
    List<Ttz_bill_orders> selectMaxAmounts();
    
    List<Ttz_bill_orders> getRedPacket(Map<String, Object> map);
    
    int updateRedPacket(Map<String, Object> map);
    
    List<Ttz_bill_orders> getNotunFreezeInfo(Map<String,Object> map);
    
    List<Ttz_bill_orders> selectFirstBill(Map<String, Object> map);
    
    /**
     * 批量生成红包
     * @return
     */
    int insertRedPackages( List<Ttz_bill_orders> list);
    
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
     * 获取即将实效的红包
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
     * 查询
     * @param userId
     * @return
     */
    Integer selectYLQRedPacket(Integer userId);
    
    /**
     * 根据 order_id批量修改红包未失效
     * @param map
     * update_time
     * ids list   order_ids
     * @return
     */
    Integer dealExpireRedPackets(Map<String,Object> map);
    
    
  
}