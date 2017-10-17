package com.cn.ttz.service;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_user_pid;

public interface Ttz_user_pidService {
	public int insertSelective(Ttz_user_pid ttz_user_pid);
	
	public int selectByUserid(Integer user_id);
	
	 List<Ttz_user_pid> selectUseridByPid(Map<String,Object> map);
}
