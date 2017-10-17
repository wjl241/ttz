package com.cn.ttz.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Ttz_user_relation;
import com.cn.ttz.service.Ttz_user_relationService;

import util.CookieUtil;

@Controller
@RequestMapping("/userrelation")
public class Ttz_user_relationController {
	Logger logger = LoggerFactory.getLogger(Ttz_user_relationController.class);  
	@Resource
	private Ttz_user_relationService ttz_user_relationService;
	@RequestMapping("/insertrelation")
	/**
	 * 注册后 绑定上级团团关系
	 * @param request
	 * @param response
	 */
	public  void insertUserRelation(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int userId = CookieUtil.getUserId(request, response);
		if(userId == -1) {
			return;
		}
		String parent_id = request.getParameter("parent_id");
		if(parent_id ==null || "".equals(parent_id)) {
			CookieUtil.responseWriteInfo("parent_id is null", response);
			return;
		}
		Ttz_user_relation ttz_user_relation = new Ttz_user_relation();
		ttz_user_relation.setId(userId);
		ttz_user_relation.setParentId(Integer.valueOf(parent_id));
		ttz_user_relation.setType((byte)0);
		ttz_user_relation.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		ttz_user_relation.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int success = ttz_user_relationService.insertSelective(ttz_user_relation);
		JSONObject ret = new JSONObject();
		if(success>=0) {
			ret.put("success", true);
			ret.put("count", success);
		}else {
			ret.put("success", false);
			ret.put("resaon", "ttz_user_relation保存失败:"+ttz_user_relation.getUserId());
			CookieUtil.responseWriteInfo(ret.toJSONString(), response);
			return;
		}
		CookieUtil.responseWriteInfo(ret.toJSONString(), response);
		return;
		
		
		
		
	}
	
}
