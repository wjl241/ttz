package com.cn.ttz.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Ttz_pid;
import com.cn.ttz.pojo.Ttz_user_pid;
import com.cn.ttz.service.Ttz_pidService;
import com.cn.ttz.service.Ttz_user_pidService;

import util.CookieUtil;

@Controller
@RequestMapping("/goodsdetail")
public class Ttz_user_pidController {
	Logger logger = LoggerFactory.getLogger(Ttz_user_pidController.class);  
	@Resource
	private Ttz_user_pidService ttz_user_pidService;
	@Resource
	private Ttz_pidService ttz_pidService;
	@RequestMapping("/join")
	/**
	 * 参团方法 1.赋予用户pid 2.参团状态表修改 3.返回appkey,三段数pid
	 * @param request
	 * @param response
	 */
	public synchronized void joinTuan(HttpServletRequest request,HttpServletResponse response){
		
		//Object a = request.getSession().getAttribute("user");
		//System.err.println("heh:"+a);
		//TODO SESSION解析
		
		
		//根绝cookie获取request方法
		int userId = CookieUtil.getUserId(request, response);
		if(userId == -1 || userId<0) {
			return;
		}
		
		int count = ttz_user_pidService.selectByUserid(userId);
		if(count<=0) { //若没有关联userid
			byte origin = 1;
			byte status =1;
			Ttz_pid ttz_pid = new Ttz_pid();
			ttz_pid = ttz_pidService.selectValuePid();
			if(ttz_pid ==null) {
				logger.error("查不到有效的pid，请联系相关运营人员");
			}
			
			//给userid赋予pid
			Ttz_user_pid ttz_user_pid = new Ttz_user_pid();
			ttz_user_pid.setPid(ttz_pid.getPid());
			ttz_user_pid.setUserId(userId);
			ttz_user_pid.setOrderSn("");
			ttz_user_pid.setStartTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_user_pid.setEndTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_user_pid.setOrigin(origin);
			ttz_user_pid.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_user_pid.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			//保存ttz_user_pid
			int success = ttz_user_pidService.insertSelective(ttz_user_pid);
			JSONObject ret = new JSONObject();
			JSONObject data =new JSONObject();
			if(success<0) {
				ret.put("code", 600);
				ret.put("message", "ttz_user_pid保存失败:"+userId);
				ret.put("data", "");
				try {
					response.getWriter().write(ret.toJSONString());
					return;
				} catch (IOException e) {
					ret.put("code", 600);
					ret.put("message", "ttz_user_pid保存失败:"+userId+e.getMessage());
					ret.put("data", "");
					logger.error("response.getWriter().write错误：", e);
				}
			}
			//更新ttz_pid状态
			ttz_pid.setStatus(status);
			ttz_pid.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			success = ttz_pidService.updateByPrimaryKeySelective(ttz_pid);
			if(success<0) {
				ret.put("code", 600);
				ret.put("message", "ttz_user_pid保存失败:"+userId);
				ret.put("data", "");
				responseWriteInfo(ret.toJSONString(), response);
				return;
			}
		}
		
	
		
		System.err.println("success");
		
		
		
		
		 
		
		
		return ;
	}
	public HttpServletResponse responseWriteInfo(String info,HttpServletResponse response) {
		try {
			response.getWriter().write(info);
			return response;
		} catch (IOException e) {
			logger.error("response.getWriter().write错误：", e);
		}
		return response;
	}
	
	
	
	
}
