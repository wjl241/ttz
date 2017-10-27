package com.cn.ttz.service;

import java.util.List;


import com.cn.ttz.pojo.Ttz_pid;

public interface Ttz_pidService {
	public int insertTtz_pids(List<Ttz_pid> pids);
	
	/**
	 * 获取有效的pid，status = 0,isdelete = 0
	 * @return
	 */
	public Ttz_pid selectValuePid();
	
	/**
	 * 修改pid实体
	 * @param pid
	 * @return
	 */
	public int updateByPrimaryKeySelective(Ttz_pid pid);
	
	 
    /**
     * 凌晨批量重置
     * @param id
     * @return
     */
    int  updatePids(Integer id);
}
