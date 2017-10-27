package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Jihes_goods;
import com.cn.ttz.pojo.Ttz_goods;

public interface Ttz_goodsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_goods record);

    int insertSelective(Ttz_goods record);

    Ttz_goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_goods record);

    int updateByPrimaryKey(Ttz_goods record);
    
    List<Jihes_goods> selectPage(Map<String,Integer> map) ;
    
    List<Ttz_goods> selectTtzGoodsId(Map<String,Object> map);
    
    /**
     * 根据id批量查询
     * @param map
     * @return
     */
    List<Ttz_goods> selectGoodPerson(Map<String,Object> map);
}