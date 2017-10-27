package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_user_relation;

public interface Ttz_user_relationDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_user_relation record);

    int insertSelective(Ttz_user_relation record);

    Ttz_user_relation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_user_relation record);

    int updateByPrimaryKey(Ttz_user_relation record);
    
    /**
     * 获取当日此user_id的所有父类
     * @param map
     * @return
     */
    List<Ttz_user_relation> selectRelations(Map<String, Object> map);
}