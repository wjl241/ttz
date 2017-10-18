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
}