package com.cn.ttz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_user_relationDao;
import com.cn.ttz.pojo.Ttz_user_relation;
import com.cn.ttz.service.Ttz_user_relationService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("Ttz_user_relationService")
public class Ttz_user_relationServiceImpl implements Ttz_user_relationService{
	@Resource
	private Ttz_user_relationDao ttz_user_relationDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertSelective(Ttz_user_relation ttz_user_relation) {
		return ttz_user_relationDao.insertSelective(ttz_user_relation);
	}

}
