package com.cn.ttz.service;

import com.cn.ttz.pojo.Ttz_xgcs;

public interface Ttz_xgcsService {
	public Ttz_xgcs selectByName(String name);
	
	public Ttz_xgcs selectByName2(String name);
	
	public Integer updateTtz(Ttz_xgcs xgcs);
	
	public Integer InsertTtz(Ttz_xgcs xgcs);
}
