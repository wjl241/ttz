package com.cn.ttz.service;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Jihes_goods;
import com.cn.ttz.pojo.Ttz_goods;

public interface Ttz_goodsService {
	   List<Jihes_goods> selectPage(Map<String,Integer> map) ;
	   /**
	    * 首页红包发放数
	    * @param good_id
	    * @return
	    */
	   int selectByGoodId(Map<String,Object> map);
	   /**
	    * 首页参团人数
	    * @param map
	    * @return
	    */
	   int selectTuanNum(Map<String,Object> map);
	   
	   	/**
	   	 * 定时获取订单时 获取ttz_goods的id与itemid
	   	 * @param map
	   	 * @return
	   	 */
	   List<Ttz_goods> selectTtzGoodsId(Map<String,Object> map);

}
