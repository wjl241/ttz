package com.cn.ttz.dao;

import com.cn.ttz.pojo.Ttz_user_relation;

public interface Ttz_user_relationDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_user_relation record);

    int insertSelective(Ttz_user_relation record);

    Ttz_user_relation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_user_relation record);

    int updateByPrimaryKey(Ttz_user_relation record);
}