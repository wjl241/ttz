package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_team;

public interface Ttz_teamDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_team record);

    int insertSelective(Ttz_team record);

    Ttz_team selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_team record);

    int updateByPrimaryKey(Ttz_team record);
    
    /**
     * 获取某个活动商品的所有组队成功队伍
     * @param map
     * ttz_goods_id 商品活动id
     * start_time 开始时间
     * end_time  结束时间
     * status   队伍状态0 开始组队 1 成功 2 红包发放 3失败
     * @return
     */
    List<Ttz_team> getSuccessTeam(Map<String,Object> map);
    
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