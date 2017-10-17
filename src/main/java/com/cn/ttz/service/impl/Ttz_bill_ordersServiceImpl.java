package com.cn.ttz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_bill_ordersDao;
import com.cn.ttz.dao.Ttz_unfreezeDao;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_unfreeze;
import com.cn.ttz.service.Ttz_bill_ordersService;

@Service("Ttz_Bill_OrderService")
public class Ttz_bill_ordersServiceImpl implements Ttz_bill_ordersService{
	@Resource
	private Ttz_bill_ordersDao ttz_bill_ordersDao;
	
	@Resource
	private Ttz_unfreezeDao ttz_unfreezeDao;

	public double selectBillOrderAmout(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectBillOrderAmout(map);
	}

	public int selectBillOrderCount(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectBillOrderCount(map);
	}

	public Double selectAmountByUserId(Integer userId) {
		return ttz_unfreezeDao.selectAmountByUserId(userId);
	}

	public List<Ttz_bill_orders> selectMaxAmounts() {
		return ttz_bill_ordersDao.selectMaxAmounts();
	}

	public List<Ttz_unfreeze> selectFreezeInfo(Map<String, Object> map) {
		return ttz_unfreezeDao.selectFreezeInfo(map);
	}
}
