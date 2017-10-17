package com.cn.ttz.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.User;
import com.cn.ttz.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	@ResponseBody
	@RequestMapping("/showUser2")
	public void toIndex2(HttpServletRequest request,HttpServletResponse response){
			
		response.setContentType("application/json;charset=utf-8"); 
		try {
			JSONObject aa = new JSONObject();
			aa.put("1", "123");
			aa.put("2", "345");
			response.getWriter().write(aa.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	@ResponseBody
	@RequestMapping("/showUser3")
	public void toIndex3(HttpServletRequest request,HttpServletResponse response){
			
		response.setContentType("application/json;charset=utf-8"); 
		String _pids = request.getParameter("pids");
		String[] pids = _pids.split(",");
		try {
			if(pids ==null || pids.length<=0) {
				response.getWriter().write("解析pids失败:pids为空或长度小于0");
			}
			for(int i=0;i<pids.length;i++) {
				
			}
			
			JSONObject aa = new JSONObject();
			aa.put("1", "123");
			aa.put("2", "345");
			response.getWriter().write(aa.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
