package com.cn.ttz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_bill_ordersDao;
import com.cn.ttz.dao.Ttz_goodsDao;
import com.cn.ttz.dao.Ttz_ordersDao;
import com.cn.ttz.dao.Ttz_tuantuanDao;
import com.cn.ttz.pojo.Jihes_goods;
import com.cn.ttz.pojo.Ttz_goods;
import com.cn.ttz.service.Ttz_goodsService;
@Service("Ttz_GoodsService")
public class Ttz_goodsServiceImpl implements Ttz_goodsService{
	@Resource
	private Ttz_goodsDao ttz_goodsDao;
	@Resource
	private Ttz_tuantuanDao ttz_tuantuanDao;
	public List<Jihes_goods> selectPage(Map<String, Integer> map) {
		return ttz_goodsDao.selectPage(map);
	}
	@Resource
	private Ttz_bill_ordersDao ttz_bill_ordersDao;
	public int selectByGoodId(Map<String,Object> map) {
		return ttz_bill_ordersDao.selectByGoodId(map);
	}
	@Resource
	private Ttz_ordersDao ttz_ordersDao;
	public int selectTuanNum(Map<String, Object> map) {
		return ttz_ordersDao.selectTuanNum(map);
	}
	public List<Ttz_goods> selectTtzGoodsId(Map<String, Object> map) {
		return ttz_goodsDao.selectTtzGoodsId(map);
	}
	@Override
	public int updateExpireTuanStatus(Map<String, Object> map) {
		return ttz_tuantuanDao.updateExpireTuanStatus(map);
	}

}
