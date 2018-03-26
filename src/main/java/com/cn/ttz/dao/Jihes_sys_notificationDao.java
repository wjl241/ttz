package com.cn.ttz.dao;

import java.util.List;
import java.util.Map;

import com.cn.ttz.pojo.Jihes_sys_notification;

public interface Jihes_sys_notificationDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_sys_notification record);

    int insertSelective(Jihes_sys_notification record);

    Jihes_sys_notification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_sys_notification record);

    int updateByPrimaryKeyWithBLOBs(Jihes_sys_notification record);

    int updateByPrimaryKey(Jihes_sys_notification record);
    
    /**
     * 批量插入红包提示信息
     * @param records
     * @return
     */
    int insertNotifications(List<Jihes_sys_notification> list);
    
    /**
     * 查询
     * @param map
     * @return
     */
    int  selectCreateTime(Map<String,Object> map);
}