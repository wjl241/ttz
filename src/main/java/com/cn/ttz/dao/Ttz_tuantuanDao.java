package com.cn.ttz.dao;

import com.cn.ttz.pojo.Ttz_tuantuan;

public interface Ttz_tuantuanDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_tuantuan record);

    int insertSelective(Ttz_tuantuan record);

    Ttz_tuantuan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_tuantuan record);

    int updateByPrimaryKey(Ttz_tuantuan record);
}