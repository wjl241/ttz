package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_tuantuan;

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
    
    /**
     * 获取各活动当日的参团人数
     * start_time 当日0点
     * end_time 当日24点
     * @param map
     * @return
     */
    List<Ttz_orders> selectGoodsNum(Map<String,Object> map);
    
    
    /**
     * 查询当前活动下的所有订单，按淘宝创建时间排序
     * @param ttz_goods_id
     * @return
     */
    List<Ttz_orders>  getOrdersByGoodsId(Integer ttz_goods_id);
    
    /**
     * 获取有效的订单数量
     * @param ttz_goods_id
     * @return
     */
    Integer  getValidOrderCount(Integer userId);
    
    /**
     * 查询今日有有效参团情况的表
     * @param map
     * @return
     */
    List<Ttz_orders> getValuesOrders(Map<String,Object> map);
    
    /**
     * 获取NPC订单数量，ttz_goods_id ,amount(数量)
     * @param map
     * @return
     */
    List<Ttz_orders> getNPCOrderNumber(Map<String,Object> map);
}