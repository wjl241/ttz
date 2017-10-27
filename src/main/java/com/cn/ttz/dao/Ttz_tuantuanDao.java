package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_tuantuan;

public interface Ttz_tuantuanDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_tuantuan record);

    int insertSelective(Ttz_tuantuan record);

    Ttz_tuantuan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_tuantuan record);

    int updateByPrimaryKey(Ttz_tuantuan record);
    
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
     * 根据goodId查询，倒序显示,用于红包发放
     * @param map
     * @return
     */
    List<Ttz_tuantuan> selectByGoodId(Map<String,Object> map);
    
    /**
     * 查询此用户是否参团成功
     * @param userId
     * @return
     */
    Integer  selectIsTuan(Integer userId);
    
   
}