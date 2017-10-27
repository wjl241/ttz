package com.cn.ttz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Jihes_userDao;
import com.cn.ttz.dao.Ttz_goodsDao;
import com.cn.ttz.dao.Ttz_ordersDao;
import com.cn.ttz.dao.Ttz_tuantuanDao;
import com.cn.ttz.dao.Ttz_user_relationDao;
import com.cn.ttz.pojo.Jihes_user;
import com.cn.ttz.pojo.Ttz_goods;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_tuantuan;
import com.cn.ttz.pojo.Ttz_user_relation;
import com.cn.ttz.service.Ttz_OrderService;

@Service("Ttz_OrderService")
public class Ttz_OrderServiceImpl implements Ttz_OrderService {
	@Resource
	private Ttz_ordersDao ttz_ordersDao;
	@Resource
	private Ttz_goodsDao ttz_goodsDao;
	@Resource
	private Ttz_tuantuanDao ttz_tuantuanDao;
	@Resource
	private Ttz_user_relationDao ttz_user_relationDao;
	@Resource
	private Jihes_userDao jihes_userDao;

	public int updateOrders(Map<String,Object> map) {
		return ttz_ordersDao.updateOrders(map);
	}

	public List<Ttz_orders> selectGoodsNum(Map<String, Object> map) {
		return ttz_ordersDao.selectGoodsNum(map);
	}

	public List<Ttz_goods> selectGoodPerson(Map<String, Object> map) {
		return ttz_goodsDao.selectGoodPerson(map);
	}

	public int updateTuanStatus(Map<String,Object> map) {
		return ttz_tuantuanDao.updateTuanStatus(map);
	}

	public List<Ttz_tuantuan> selectByUser_id(Map<String, Object> map) {
		return ttz_tuantuanDao.selectByUser_id(map);
	}

	public int updateByPrimaryKeySelective(Ttz_tuantuan record) {
		return ttz_tuantuanDao.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Ttz_goods record) {
		return ttz_goodsDao.updateByPrimaryKeySelective(record);
	}

	public List<Ttz_tuantuan> selectByGoodId(Map<String, Object> map) {
		return ttz_tuantuanDao.selectByGoodId(map);
	}

	public List<Ttz_orders> getOrdersByGoodsId(Integer ttz_goods_id) {
		return ttz_ordersDao.getOrdersByGoodsId(ttz_goods_id);
	}

	public List<Ttz_user_relation> selectRelations(Map<String, Object> map) {
		return ttz_user_relationDao.selectRelations(map);
	}

	@Override
	public List<Ttz_orders> getValuesOrders(Map<String, Object> map) {
		return ttz_ordersDao.getValuesOrders(map);
	}

	@Override
	public Jihes_user selectNPCInfo(Integer count) {
		return jihes_userDao.selectNPCInfo(count);
	}

	@Override
	public List<Ttz_orders> getNPCOrderNumber(Map<String, Object> map) {
		return ttz_ordersDao.getNPCOrderNumber(map);
	}
}
