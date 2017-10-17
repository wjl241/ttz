package com.cn.ttz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_pidDao;
import com.cn.ttz.pojo.Ttz_pid;
import com.cn.ttz.service.Ttz_pidService;
@Service("Ttz_pidService")
public class Ttz_pidServiceImpl implements Ttz_pidService{
	@Resource
	private Ttz_pidDao ttz_pidDao;
	public int insertTtz_pids(List<Ttz_pid> pids) {
		
		int count = ttz_pidDao.insertTtz_pids(pids);
		return count;
		
	}
	public Ttz_pid selectValuePid() {
		Ttz_pid ttz_pid = ttz_pidDao.selectValuePid();
		return ttz_pid;
	}
	
	public int updateByPrimaryKeySelective(Ttz_pid pid) {
		return ttz_pidDao.updateByPrimaryKeySelective(pid);
	}

}
