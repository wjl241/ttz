package com.cn.ttz.dao;

import java.util.List;

import com.cn.ttz.pojo.Ttz_pid;

public interface Ttz_pidDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_pid record);

    int insertSelective(Ttz_pid record);

    Ttz_pid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_pid record);

    int updateByPrimaryKey(Ttz_pid record);
    
    int insertTtz_pids(List<Ttz_pid> pids);
    
    Ttz_pid selectValuePid();
    
    /**
     * 凌晨批量重置
     * @param id
     * @return
     */
    int  updatePids(Integer id);
}