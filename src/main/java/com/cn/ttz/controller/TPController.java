package com.cn.ttz.controller;

import java.io.IOException;
import java.util.jar.Attributes.Name;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ttz.pojo.Ttz_xgcs;
import com.cn.ttz.pojo.User;
import com.cn.ttz.service.IUserService;
import com.cn.ttz.service.Ttz_xgcsService;

@Controller
@RequestMapping("/tp")
public class TPController {
	@Resource
	private IUserService userService;
	@Resource
	private Ttz_xgcsService ttz_xgcsService;
	@RequestMapping("/showTp")
	public String showTp(HttpServletRequest request,Model model){
		int userId = 1;
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "tp2";
	}
	
	@RequestMapping("/showTp2")
	public String showTp2(HttpServletRequest request,Model model){
		int userId = 1;
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "tp3";
	}
	
	@RequestMapping("/showTp3")
	public String showTp3(HttpServletRequest request,Model model){
		int userId = 1;
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "tp4";
	}
	
	@RequestMapping("/dealTp")
	public ModelAndView toIndex(HttpServletRequest request,Model model,HttpServletResponse response){
		String name  = request.getParameter("name");
		String  pics = request.getParameter("aaa");
		String sex = request.getParameter("sex");
		int sex2 = 0;
		if(pics == null || pics.length()<=0) {
			return null;
		}
		
		if(name == null || pics.length()<=0) {
			return null;
		}
		
		if(sex == null || sex.length()<=0) {
			return null;
		}
		
		if("male".equals(sex)) {
			sex2 = 0;
		}else {
			sex2 = 1;
		}
		pics = pics.replaceAll("pic", "");
		Ttz_xgcs ttz_xgcs = new Ttz_xgcs();
		ttz_xgcs = ttz_xgcsService.selectByName(name);
		int count = 0;
		if(ttz_xgcs != null) {
			ttz_xgcs.setPics(pics);
			ttz_xgcs.setSex((byte)sex2);
			ttz_xgcs.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = ttz_xgcsService.updateTtz(ttz_xgcs);
			System.err.println("修改行数"+count);
		}else {
			ttz_xgcs = new Ttz_xgcs();
			ttz_xgcs.setPics(pics);
			ttz_xgcs.setUserName(name);
			ttz_xgcs.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setType((byte)0);
			ttz_xgcs.setSex((byte)sex2);
			count = ttz_xgcsService.InsertTtz(ttz_xgcs);
			System.err.println("插入行数"+count);
		}
		
		
		return new ModelAndView("tp3");
	}
	
	
	@RequestMapping("/dealTp2")
	public ModelAndView dealTp2(HttpServletRequest request,Model model,HttpServletResponse response){
		String name  = request.getParameter("name");
		String  pics = request.getParameter("aaa");
		String sex =request.getParameter("sex");
		if(pics == null || pics.length()<=0) {
			return null;
		}
		
		if(name == null || pics.length()<=0) {
			return null;
		}
		if(sex == null || sex.length()<=0) {
			return null;
		}
		
		pics = pics.replaceAll("pic", "");
		Ttz_xgcs ttz_xgcs = new Ttz_xgcs();
		ttz_xgcs = ttz_xgcsService.selectByName2(name);
		int count = 0;
		if(ttz_xgcs != null) {
			ttz_xgcs.setPics(pics);
			ttz_xgcs.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setSex(Byte.valueOf(sex));
			count = ttz_xgcsService.updateTtz(ttz_xgcs);
			System.err.println("修改行数"+count);
		}else {
			ttz_xgcs = new Ttz_xgcs();
			ttz_xgcs.setPics(pics);
			ttz_xgcs.setUserName(name);
			ttz_xgcs.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_xgcs.setType((byte)1);
			ttz_xgcs.setSex(Byte.valueOf(sex));
			count = ttz_xgcsService.InsertTtz(ttz_xgcs);
			System.err.println("插入行数"+count);
		}
		
		//new ModelAndView("/WEB-INF/jsp/tp3.jsp")
		return new ModelAndView("tp3");
	}
}
