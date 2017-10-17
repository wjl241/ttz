package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Ttz_user_pid;

public interface Ttz_user_pidDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ttz_user_pid record);

    int insertSelective(Ttz_user_pid record);

    Ttz_user_pid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ttz_user_pid record);

    int updateByPrimaryKey(Ttz_user_pid record);
    
    int selectByUserid(Integer user_id);
    
    List<Ttz_user_pid> selectUseridByPid(Map<String,Object> map);
}