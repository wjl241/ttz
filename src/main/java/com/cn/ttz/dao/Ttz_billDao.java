package com.cn.ttz.dao;

import com.cn.ttz.pojo.Ttz_bill;

public interface Ttz_billDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_bill record);

    int insertSelective(Ttz_bill record);

    Ttz_bill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_bill record);

    int updateByPrimaryKey(Ttz_bill record);
    
}