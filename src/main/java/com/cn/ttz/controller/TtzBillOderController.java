package com.cn.ttz.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_unfreeze;
import com.cn.ttz.service.ConfigService;
import com.cn.ttz.service.Ttz_bill_ordersService;

import util.ConfigJson;

@Controller
@RequestMapping("/hbdetail")
/**
 * 红包处理
 * @author Administrator
 *
 */
public class TtzBillOderController {
	Logger logger = LoggerFactory.getLogger(TtzBillOderController.class);  
	@Resource
	private Ttz_bill_ordersService ttz_bill_ordersService;
	@Resource
	private ConfigService ConfigService;
	@RequestMapping("/infos")
	/**
	 * 注册后 绑定上级团团关系
	 * @param request
	 * @param response
	 */
	public  void getBillOrderInfo(HttpServletRequest request,HttpServletResponse response){
		//TODO 此处应有user_id校验
		int user_id = 15;
		int status = 2;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("status", status);
		double ylqAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//已领取金额
		System.err.println("领取总数："+ylqAmount);
		int dlqCount = ttz_bill_ordersService.selectBillOrderCount(map);//待领取红包总数
		System.err.println("待领取总数："+dlqCount);
	
		double txAmount = ttz_bill_ordersService.selectAmountByUserId(user_id);//已提现金额
		BigDecimal a1 = new BigDecimal(ylqAmount);
		BigDecimal a2 = new BigDecimal(txAmount);
		double jdAmount = a1.subtract(a2).doubleValue();//解冻金额
		
		
		JSONObject ret = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject jb = new JSONObject(); 
		
		
		List<Ttz_bill_orders> maxOrders =  ttz_bill_ordersService.selectMaxAmounts();
		if(maxOrders ==null || maxOrders.size()<=0) {
			data.put("list", list);
			ret.put("code",600 );
			ret.put("message", "查询红包表异常");
			ret.put("data", data);
			responseWriteInfo(ret.toJSONString(), response);
			return;
		}
		for(Ttz_bill_orders orders : maxOrders) {
			jb = new JSONObject();
			jb.put("sumAmount", orders.getCommission());
			jb.put("nickName", orders.getGoodId());
			jb.put("userId", orders.getUserId());
			list.add(jb);
		}
		
		
		
		
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -90);
		map.put("create_time", calendar.getTime().getTime()/1000);
		List<Ttz_unfreeze> freezeInfos = ttz_bill_ordersService.selectFreezeInfo(map);
		
		if(maxOrders ==null || maxOrders.size()<=0) {
			data.put("freezeDay", "-1");
			data.put("list", list);
			ret.put("code",600 );
			ret.put("message", "查询解冻信息表数据异常");
			ret.put("data", data);
			responseWriteInfo(ret.toJSONString(), response);
			return;
		}
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		String hb_rate;
		String hb_day = ConfigService.selectConfig("ttz","hbdetail","hb_day");
		if(hb_day ==null || hb_day.equals("")) {
			 hb_day = "7";
		}
		System.err.println("aa");
		double hb_day2 =Double.valueOf(hb_day);
		hb_rate = ConfigService.selectConfig("ttz","hbdetail","hb_rate");
		if(hb_rate ==null) {
			hb_day = "5,10,10,15,20,25,15";
		}
		String[] rates = hb_rate.split(",");
		//计算最少解冻日期
		long l1;
		Date now = new Date();
		long l2 = now.getTime();
		double floor;
		int minDay =10;
		DecimalFormat df = new DecimalFormat("0.00");
		int sub;
		for(Ttz_unfreeze info : freezeInfos) {
			try {
				int rateNum = 0;
				int day;
				l1 = sdf.parse(info.getReceiveTime()).getTime();
				sub= (int) ((l2-l1)/1000);
				floor = Math.floor(Double.valueOf(df.format((float) sub/(86400*hb_day2))));
				//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
				for(int i=0;i<floor;i++) {
					rateNum = rateNum +Integer.valueOf(rates[i]);
				}
				if(info.getRate() == rateNum) {//相等，说明本周已领
					double a =Math.ceil((1-calculateProfit(Double.valueOf(df.format((float) sub/(86400*hb_day2)))))*hb_day2);
					day =(int) a;
				}else {//说明本周或前几周未领
					minDay = 0;
					break;
				}
				if(day<minDay) {
					minDay = day;
				}
				
			} catch (ParseException e) {
				logger.error("selectFreezeInfo后续解析异常",e);
				
			}
		}
		
		ret.put("code", 200);
		ret.put("message", "");
		data.put("ylqAmount", ylqAmount);
		data.put("dlqCount", dlqCount);
		data.put("jdAmount", jdAmount);
		data.put("freezeDay", minDay);
		data.put("list", list);
		ret.put("data", data);
		responseWriteInfo(ret.toJSONString(), response);
		
	}
	public static void main(String[] args) {
		System.err.println(calculateProfit(111.11));
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
	/**
	 * 读取jar包文件
	 * @return
	 */
	public   String getConfigKeyJar() {
	    String keyVal = ""; 
        try {
	           InputStream is=ConfigJson.class.getClass().getResourceAsStream("/com/cn/ttz/controller/config.json");  
	           if(is == null) {
	        	   System.err.println("找不到配置文件/util/config.json");
	        	   return "";
	           }
	           BufferedReader br=new BufferedReader(new InputStreamReader(is));  
		       String str=null;
		       StringBuffer buf = new StringBuffer();
		       while((str=br.readLine())!=null){
		           buf.append(str);
		           buf.append("\r\n");
		       }
		       System.out.println(buf.toString());
		       keyVal = buf.toString();
		       br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return keyVal;  
    
	}
	 /**
     * 保留double类型小数后两位，不四舍五入，直接取小数后两位 比如：10.1269 返回：10.12
     * 
     * @param doubleValue
     * @return
     */
    public static double calculateProfit(double doubleValue) {
    	String a = String.valueOf(doubleValue);
    	String[] bb = a.split("\\.");
    	return Double.valueOf("0."+bb[1]);
    	
    }

    /**
     * 查找字符串pattern在str中第一次出现的位置
     * 
     * @param str
     * @param pattern
     * @return
     */
    public static int firstIndexOf(String str, String pattern) {
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j))
                    break;
                j++;
            }
            if (j == pattern.length())
                return i;
        }
        return -1;
    }

	
	
	
}
