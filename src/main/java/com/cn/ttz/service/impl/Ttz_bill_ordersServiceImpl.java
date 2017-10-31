package com.cn.ttz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Jihes_sys_notificationDao;
import com.cn.ttz.dao.Ttz_bill_ordersDao;
import com.cn.ttz.dao.Ttz_ordersDao;
import com.cn.ttz.dao.Ttz_tuantuanDao;
import com.cn.ttz.dao.Ttz_unfreezeDao;
import com.cn.ttz.pojo.Jihes_sys_notification;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_tuantuan;
import com.cn.ttz.pojo.Ttz_unfreeze;
import com.cn.ttz.service.Ttz_bill_ordersService;

@Service("Ttz_Bill_OrderService")
public class Ttz_bill_ordersServiceImpl implements Ttz_bill_ordersService{
	@Resource
	private Ttz_bill_ordersDao ttz_bill_ordersDao;
	
	@Resource
	private Ttz_unfreezeDao ttz_unfreezeDao;
	
	@Resource
	private Ttz_tuantuanDao ttz_tuantuanDao;
	@Resource
	private Jihes_sys_notificationDao jihes_sys_notificationDao;
	@Resource
	private Ttz_ordersDao ttz_ordersDao;

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

	public List<Ttz_bill_orders> getRedPacket(Map<String, Object> map) {
		return ttz_bill_ordersDao.getRedPacket(map);
	}

	public int updateRedPacket(Map<String, Object> map) {
		return ttz_bill_ordersDao.updateRedPacket(map);
	}

	public List<Ttz_unfreeze> getNotunFreezeInfo(Map<String, Object> map) {
		return ttz_unfreezeDao.getNotunFreezeInfo2(map);
	}

	public int insertUnfreezes(List<Ttz_unfreeze> ttz_unfreezes) {
		return ttz_unfreezeDao.insertUnfreezes(ttz_unfreezes);
	}

	public List<Ttz_bill_orders> selectFirstBill(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectFirstBill(map);
	}

	public int insertRedPackages(List<Ttz_bill_orders> list) {
		return ttz_bill_ordersDao.insertRedPackages(list);
	}

	public Integer selectIsTuan(Integer userId) {
		return ttz_tuantuanDao.selectIsTuan(userId);
	}

	public int dealExpireRedPacket(Integer updateTime) {
		return ttz_bill_ordersDao.dealExpireRedPacket(updateTime);
	}

	public List<Integer> selectWlqRedPacket(Integer expireTime) {
		return ttz_bill_ordersDao.selectWlqRedPacket(expireTime);
	}

	public int insertNotifications(List<Jihes_sys_notification> records) {
		return jihes_sys_notificationDao.insertNotifications(records);
	}

	public List<Integer> selectWlqRedPacket2(Integer expireTime) {
		return ttz_bill_ordersDao.selectWlqRedPacket2(expireTime);
	}

	@Override
	public List<Ttz_bill_orders> selectExpireRedPacket(Integer createTime) {
		return ttz_bill_ordersDao.selectExpireRedPacket(createTime);
	}

	@Override
	public Integer getValidOrderCount(Integer userId) {
		return ttz_ordersDao.getValidOrderCount(userId);
	}

	@Override
	public Integer selectYLQRedPacket(Integer userId) {
		return ttz_bill_ordersDao.selectYLQRedPacket(userId);
	}

	@Override
	public int selectCreateTime(Map<String, Object> map) {
		return jihes_sys_notificationDao.selectCreateTime(map);
	}

	@Override
	public List<Ttz_unfreeze> selectFreezeInfoNoFreeze(Map<String, Object> map) {
		return ttz_unfreezeDao.selectFreezeInfoNoFreeze(map);
	}
}
