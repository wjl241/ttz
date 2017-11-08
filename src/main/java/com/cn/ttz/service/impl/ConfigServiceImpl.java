package com.cn.ttz.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Jihes_configDao;
import com.cn.ttz.service.ConfigService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{
	@Resource
	private Jihes_configDao jihes_configDao;
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public String selectConfig(String name,String title,String key) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("title", title);
		map.put("key", key);
		return jihes_configDao.selectConfig(map);
	}

}
