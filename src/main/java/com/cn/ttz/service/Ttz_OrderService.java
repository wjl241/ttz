package com.cn.ttz.service;

import java.util.List;
import java.util.Map;

import com.cn.ttz.dao.Ttz_ordersDao;
import com.cn.ttz.pojo.Jihes_user;
import com.cn.ttz.pojo.Ttz_goods;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_team;
import com.cn.ttz.pojo.Ttz_tuantuan;
import com.cn.ttz.pojo.Ttz_user_relation;

public interface Ttz_OrderService {
	public int updateOrders(Map<String,Object> map);
	
	 /**
     * 获取各活动当日的参团人数
     * start_time 当日0点
     * end_time 当日24点
     * @param map
     * @return
     */
    List<Ttz_orders> selectGoodsNum(Map<String,Object> map);
    
    /**
     * 根据id批量查询
     * @param map
     * @return
     */
    List<Ttz_goods> selectGoodPerson(Map<String,Object> map);
    
    
    /**
     * 批量更新团团状态
     * @param map
     * @return
     */
    int updateTuanStatus(Map<String,Object> map);
    
    /**
     * 根据user_id批量查询
     * @param map
     * @return
     */
    List<Ttz_tuantuan> selectByUser_id(Map<String,Object> map);
    
    /**
     * 更新团团
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Ttz_tuantuan record);
    
    /**
     * 更新活动
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Ttz_goods record);
    
    /**
     * 根据goodId查询，倒序显示,用于红包发放
     * @param map
     * @return
     */
    List<Ttz_tuantuan> selectByGoodId(Map<String,Object> map);
    
    /**
     * 查询当前活动下的所有订单，按淘宝创建时间排序
     * @param ttz_goods_id
     * @return
     */
    List<Ttz_orders>  getOrdersByGoodsId(Integer ttz_goods_id);
    
    /**
     * 获取当日此user_id的所有下属团友
     * @param map
     * @return
     */
    List<Ttz_user_relation> selectRelations(Map<String, Object> map);
    
    
    /**
     * 查询今日有有效参团情况的表
     * @param map
     * @return
     */
    List<Ttz_orders> getValuesOrders(Map<String,Object> map);
    
    
    /**
     * 查询NPC信息，phone以110开头的user
     * @param count
     * @return
     */
    Jihes_user selectNPCInfo(Integer count);
    
    /**
     * 获取NPC订单数量，ttz_goods_id ,amount(数量)
     * @param map
     * @return
     */
    List<Ttz_orders> getNPCOrderNumber(Map<String,Object> map);
    
    
    /**
     * 生成NPC订单
     * @param list
     * @return
     */
    Integer insertNPC(List<Ttz_orders> list);
    
    
    /**
     * 获取某个失效订单且状态为成功的队伍
     * order_id ttz_order_id
     * start_time 开始时间
     * end_time  结束时间
     * status   队伍状态0 开始组队 1 成功 2 红包发放 3失败
     * @param map
     * @return
     */
    Ttz_team getFaildOrderTeam(Map<String,Object> map);
}
