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

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;

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
	
	//存在领取红包后读从库数据未刷新的情况，所以查询改为主库
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public double selectBillOrderAmout(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectBillOrderAmout(map);
	}
	//存在领取红包后读从库数据未刷新的情况，所以查询改为主库
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int selectBillOrderCount(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectBillOrderCount(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public Double selectAmountByUserId(Integer userId) {
		return ttz_unfreezeDao.selectAmountByUserId(userId);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_bill_orders> selectMaxAmounts() {
		return ttz_bill_ordersDao.selectMaxAmounts();
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public List<Ttz_unfreeze> selectFreezeInfo(Map<String, Object> map) {
		return ttz_unfreezeDao.selectFreezeInfo(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_bill_orders> getRedPacket(Map<String, Object> map) {
		return ttz_bill_ordersDao.getRedPacket(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateRedPacket(Map<String, Object> map) {
		return ttz_bill_ordersDao.updateRedPacket(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public List<Ttz_unfreeze> getNotunFreezeInfo(Map<String, Object> map) {
		return ttz_unfreezeDao.getNotunFreezeInfo2(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertUnfreezes(List<Ttz_unfreeze> ttz_unfreezes) {
		return ttz_unfreezeDao.insertUnfreezes(ttz_unfreezes);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public List<Ttz_bill_orders> selectFirstBill(Map<String, Object> map) {
		return ttz_bill_ordersDao.selectFirstBill(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertRedPackages(List<Ttz_bill_orders> list) {
		return ttz_bill_ordersDao.insertRedPackages(list);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Integer selectIsTuan(Integer userId) {
		return ttz_tuantuanDao.selectIsTuan(userId);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int dealExpireRedPacket(Integer updateTime) {
		return ttz_bill_ordersDao.dealExpireRedPacket(updateTime);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Integer> selectWlqRedPacket(Integer expireTime) {
		return ttz_bill_ordersDao.selectWlqRedPacket(expireTime);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertNotifications(List<Jihes_sys_notification> records) {
		return jihes_sys_notificationDao.insertNotifications(records);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Integer> selectWlqRedPacket2(Integer expireTime) {
		return ttz_bill_ordersDao.selectWlqRedPacket2(expireTime);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_bill_orders> selectExpireRedPacket(Integer createTime) {
		return ttz_bill_ordersDao.selectExpireRedPacket(createTime);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Integer getValidOrderCount(Integer userId) {
		return ttz_ordersDao.getValidOrderCount(userId);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Integer selectYLQRedPacket(Integer userId) {
		return ttz_bill_ordersDao.selectYLQRedPacket(userId);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public int selectCreateTime(Map<String, Object> map) {
		return jihes_sys_notificationDao.selectCreateTime(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public List<Ttz_unfreeze> selectFreezeInfoNoFreeze(Map<String, Object> map) {
		return ttz_unfreezeDao.selectFreezeInfoNoFreeze(map);
	}
}
