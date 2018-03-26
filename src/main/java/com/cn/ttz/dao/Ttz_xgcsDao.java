package com.cn.ttz.dao;

import com.cn.ttz.pojo.Ttz_xgcs;

public interface Ttz_xgcsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_xgcs record);

    int insertSelective(Ttz_xgcs record);

    Ttz_xgcs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_xgcs record);

    int updateByPrimaryKey(Ttz_xgcs record);
    
    Ttz_xgcs selectByName(String name);
    
    Ttz_xgcs selectByName2(String name);
}