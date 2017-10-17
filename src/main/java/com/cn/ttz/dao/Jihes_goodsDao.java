package com.cn.ttz.dao;

import com.cn.ttz.pojo.Jihes_goods;

public interface Jihes_goodsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_goods record);

    int insertSelective(Jihes_goods record);

    Jihes_goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_goods record);

    int updateByPrimaryKey(Jihes_goods record);
}