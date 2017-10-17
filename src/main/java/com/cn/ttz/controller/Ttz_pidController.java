package com.cn.ttz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Ttz_pid;
import com.cn.ttz.service.Ttz_pidService;

import util.MD5Util;

@Controller
@RequestMapping("/pid")
public class Ttz_pidController {
	Logger logger = LoggerFactory.getLogger(Ttz_pidController.class);  
	private byte pid_type = 5; //'PID类型 1:集合特卖APP 2:群 3:网页 4:软件,5:团团赚'
	private byte status = 0; //'状态 0:下线 1;激活'
	private byte isdelete = 0; //软删除 
	private String appkey = "24637543"; //圆圆小能豆. 牛熊证
	@Resource
	private Ttz_pidService ttz_pidService;
	@ResponseBody
	@RequestMapping("/insertpids")
	/**
	 * 批量生成当前联盟账户的pid
	 * @param request
	 * @param response
	 */
	public void insertpids(HttpServletRequest request,HttpServletResponse response){
			
		response.setContentType("application/json;charset=utf-8"); 
		String _pids = request.getParameter("pids");
		String[] pids = _pids.split(",");
		List<Ttz_pid> ttz_pids = new ArrayList<Ttz_pid>();
		Ttz_pid ttz_pid;
		try {
			if(pids ==null || pids.length<=0) {
				response.getWriter().write("解析pids失败:pids为空或长度小于0");
			}
			for(int i=0;i<pids.length;i++) {
				ttz_pid = new Ttz_pid();
				ttz_pid.setPidType(pid_type);
				ttz_pid.setStatus(status);
				ttz_pid.setIsdelete(isdelete);
				ttz_pid.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				ttz_pid.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				ttz_pid.setPidName("");
				ttz_pid.setPid(pids[i]);
				ttz_pid.setPidHash(MD5Util.toMD5(pids[i]));
				ttz_pid.setAppkey(appkey);
				ttz_pids.add(ttz_pid);
			}
			int success = ttz_pidService.insertTtz_pids(ttz_pids);
			JSONObject aa = new JSONObject();
			if(success>=0) {
				aa.put("success", true);
				aa.put("count", success);
			}else {
				aa.put("success", false);
			}
			response.getWriter().write(aa.toJSONString());
		} catch (IOException e) {
			logger.error("response.getWriter().write错误：", e);
		}
		return;
	}
	
	  
	  

}
