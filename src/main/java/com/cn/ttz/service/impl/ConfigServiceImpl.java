package com.cn.ttz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Jihes_configDao;
import com.cn.ttz.pojo.Jihes_config;
import com.cn.ttz.redis.IJedisClient;
import com.cn.ttz.service.ConfigService;

import util.JsonUtils;
import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{
	
	
	@Autowired
	@Qualifier("jedisClientPool")
	private IJedisClient jedisClient;
	
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
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public boolean selectConfig2(String name,String title,String key) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("title", title);
		map.put("key", key);
		
		// TODO Auto-generated method stub
		try {
			String json = jedisClient.hget("jihes_config", "list");
			if (StringUtils.isNotBlank(json)) {
				List<Jihes_config> config = JsonUtils.jsonToList(json, Jihes_config.class);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Jihes_config> list = new ArrayList<Jihes_config>();
		Jihes_config config  = jihes_configDao.selectByPrimaryKey(1);
		list.add(config);
		try {
			jedisClient.hset("jihes_config", "list", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	

	


}
