package com.cn.ttz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.IUserDao;
import com.cn.ttz.pojo.User;
import com.cn.ttz.service.IUserService;
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}


}
