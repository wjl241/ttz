package com.cn.ttz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.IUserDao;
import com.cn.ttz.pojo.User;
import com.cn.ttz.service.IUserService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}


}
