package com.cn.ttz.service;

import java.util.Map;

import com.cn.ttz.pojo.Jihes_config;

public interface ConfigService {
	/**
	 * 获取配置参数， name, titile,key
	 * @param name
	 * @param title
	 * @param key
	 * @return
	 */
	String selectConfig(String name,String title,String key);
}
