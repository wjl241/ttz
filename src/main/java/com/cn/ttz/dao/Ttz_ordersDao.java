package com.cn.ttz.dao;

import java.util.Map;

import com.cn.ttz.pojo.Ttz_orders;

public interface Ttz_ordersDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_orders record);

    int insertSelective(Ttz_orders record);

    Ttz_orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_orders record);

    int updateByPrimaryKey(Ttz_orders record);
    
    /**
     * 批量更新orders
     * @param list
     * @return
     */
    int updateOrders(Map<String,Object> map);
    
    /**
     * 首页 参团人数
     * @param map
     * @return
     */
    int selectTuanNum(Map<String,Object> map);
}