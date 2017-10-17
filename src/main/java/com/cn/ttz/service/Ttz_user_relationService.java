package com.cn.ttz.service;

import com.cn.ttz.pojo.Ttz_user_relation;

public interface Ttz_user_relationService {
	/**
	 * 绑定上级用户
	 * @param ttz_user_relation
	 * @return
	 */
	public int insertSelective(Ttz_user_relation ttz_user_relation);
}
