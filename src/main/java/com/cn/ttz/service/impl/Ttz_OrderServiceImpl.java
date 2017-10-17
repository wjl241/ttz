package com.cn.ttz.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_ordersDao;
import com.cn.ttz.service.Ttz_OrderService;

@Service("Ttz_OrderService")
public class Ttz_OrderServiceImpl implements Ttz_OrderService {
	@Resource
	private Ttz_ordersDao ttz_ordersDao;

	public int updateOrders(Map<String,Object> map) {
		return ttz_ordersDao.updateOrders(map);
	}
}
