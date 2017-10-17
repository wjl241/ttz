package com.cn.ttz.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Jihes_goods;
import com.cn.ttz.service.ConfigService;
import com.cn.ttz.service.Ttz_goodsService;

@Controller
@RequestMapping("/goods")
public class Ttz_goodsController {
	Logger logger = LoggerFactory.getLogger(Ttz_pidController.class);  
	@Resource
	private Ttz_goodsService ttz_goodsService;
	
	@ResponseBody
	@RequestMapping("/selectAll")
	public void selectAll(HttpServletRequest request,HttpServletResponse response){
		//TODO 此处应有user_id校验
		response.setContentType("application/json;charset=utf-8"); 
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("page", Integer.valueOf(page));
		map.put("limit", Integer.valueOf(limit));
		List<Jihes_goods> goods = ttz_goodsService.selectPage(map);
		JSONObject jsob;
		JSONArray jar = new JSONArray();
		int redPacketNum = 0;
		int tuanNum = 0;
		Map<String,Object> tuanMap = new HashMap<String, Object>();
		try {
			for(Jihes_goods good : goods) {
				tuanMap =new HashMap<String, Object>();
				jsob = new JSONObject();
				jsob = (JSONObject) JSON.toJSON(good);
				tuanMap.put("end_time", good.getEndTime());
				tuanMap.put("start_time", good.getStartTime());
				tuanMap.put("good_id", good.getItemId());
				redPacketNum = ttz_goodsService.selectByGoodId(tuanMap);
				jsob.put("redPacketNum", redPacketNum);
				tuanNum = ttz_goodsService.selectTuanNum(tuanMap);
				jsob.put("tuanNum", tuanNum);
				//System.err.println(jsob.toJSONString());
				jar.add(jsob);
			}
			response.getWriter().write(jar.toJSONString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
