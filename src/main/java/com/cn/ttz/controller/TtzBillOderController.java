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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
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
import util.CookieUtil;

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
//		HttpSession session=request.getSession();//获取session
//		String user = (String) request.getSession().getAttribute("user");
//		JSONObject jsob = new JSONObject();
//		jsob.put("session", session);
//		jsob.put("test", "111");
//		String[]   names   =   session.getValueNames(); 
//		session.getAttributeNames();
//		jsob.put("namelength:", names.length);
//		if(session.getAttribute("user") == null) {
//			jsob.put("user2", "null");
//		}else if(session.getAttribute("user").equals("")) {
//			jsob.put("user2", "为空");
//		}else {
//			jsob.put("user2", "不为空");
//		}
//		jsob.put("user", session.getAttribute("user"));
//		String ab = "";
//		for   (int   i   =   0;   i   <   names.length;   i++)   { 
//			ab = ab+names[i] + ","   +   session.getValue(names[i]);
//		    System.out.println(names[i] + ","   +   session.getValue(names[i]));
//		   
//		}
//		jsob.put("ab", ab);
//		 responseWriteInfo(200,jsob.toJSONString() , null, response);
//		 return;
		
		
		JSONObject ret = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject jb = new JSONObject(); 
		response.setContentType("application/json;charset=utf-8"); 
		

		//根绝cookie获取request方法
		int user_id = CookieUtil.getUserId(request, response);
		if(user_id == -1 || user_id<0) {
			responseWriteInfo(401, "未登录", null, response);
			return;
		}
		//获取最大信息
		List<Ttz_bill_orders> maxOrders =  ttz_bill_ordersService.selectMaxAmounts();
		if(maxOrders ==null || maxOrders.size()<=0) {
			responseWriteInfo(600, "查询红包表异常", null, response);
			return;
		}
		for(Ttz_bill_orders orders : maxOrders) {
			jb = new JSONObject();
			jb.put("sumAmount", orders.getCommission());
			jb.put("nickName", orders.getGoodId());
			jb.put("userId", orders.getUserId());
			list.add(jb);
		}
		
		
		int status = 2;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("status1", 2);//已领取
		map.put("status2", 4);//失效
		
		double ylqAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//已领取金额
		map.put("status2", 2);//失效
		double djdAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//所有需要解冻金额
		System.err.println("领取总数："+ylqAmount);
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int dlqCount = ttz_bill_ordersService.selectBillOrderCount(map);//待领取红包总数
		System.err.println("待领取总数："+dlqCount);
		
		
		if(ylqAmount ==0 ) {//没有领取红包，没有待领取金额，直接返回
			data.put("ylqAmount", String.valueOf(ylqAmount));
			data.put("dlqCount", String.valueOf(dlqCount));
			data.put("jdAmount", String.valueOf(0.00));
			data.put("freezeDay", String.valueOf(-1));
			data.put("list", list);
			responseWriteInfo(200, "", data, response);
			return;
		}
		
	
		double txAmount = ttz_bill_ordersService.selectAmountByUserId(user_id);//已提现金额
		BigDecimal a1 = new BigDecimal(djdAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal a2 = new BigDecimal(txAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		double jdAmount = a1.subtract(a2).doubleValue();//解冻金额
		
	
		
		
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hb_rate;
		String hb_day = ConfigService.selectConfig("ttz","hbdetail","hb_day");
		if(hb_day ==null || hb_day.equals("")) {
			 hb_day = "7";
		}

		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -90);
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -Integer.valueOf(hb_day));
		map.put("receive_time", sdf.format(calendar.getTime()));
		
		List<Ttz_unfreeze> notUnfreezes = ttz_bill_ordersService.getNotunFreezeInfo(map);
		int minDay =10;
		if(notUnfreezes ==null || notUnfreezes.size()<=0) {//没有解冻信息，说明肯定有已领取的红包未解冻
//			map = new HashMap<String, Object>();
//			map.put("user_id", user_id);
//			map.put("expire_time", calendar.getTime().getTime()/1000);
//			List<Ttz_bill_orders> firstOrders = ttz_bill_ordersService.selectFirstBill(map);
//			
//			String firstReceiveTime = firstOrders.get(0).getReceiveTime();
//			long l1;
//			try {
//				l1 = sdf.parse(firstReceiveTime).getTime();
//				Date now2 = new Date();
//				long l2 = now2.getTime();
//				int sub2= (int) ((l2-l1)/1000);
//				DecimalFormat df2 = new DecimalFormat("0.00");
//				double hb_day3 =Double.valueOf(hb_day);
//				double floor2 = Math.floor(Double.valueOf(df2.format((float) sub2/(86400*hb_day3))));//相差几周，向下取整
//				if(floor2>=1) {
//					minDay = 0;
//				}else {
//					double a =Math.ceil((1-calculateProfit(Double.valueOf(df2.format((float) sub2/(86400*hb_day3)))))*hb_day3);
//					minDay =(int) a;
//				}
//				
//				data.put("ylqAmount", String.valueOf(ylqAmount));
//				data.put("dlqCount", String.valueOf(dlqCount));
//				data.put("jdAmount", String.valueOf(jdAmount));
//				data.put("freezeDay", String.valueOf(minDay));
//				data.put("list", list);
//				responseWriteInfo(200, "", data, response);
//				return;
//			} catch (ParseException e) {
//				logger.error("selectFreezeInfo后续解析异常",e);
//			}
			
			
		}
		
		if(notUnfreezes !=null && notUnfreezes.size()>0) {//存在需要解冻单未解冻的红包，直接返回0天 
			minDay = 0;
		}else {//否则，循环判断
			map = new HashMap<String, Object>();
			map.put("user_id", user_id);
			
			map.put("create_time", calendar.getTime().getTime()/1000);
			List<Ttz_unfreeze> freezeInfos = ttz_bill_ordersService.selectFreezeInfo(map);
			
			
			if(freezeInfos ==null || freezeInfos.size()<=0) {//说明从未有过解冻信息
				data.put("freezeDay", "-1");
				data.put("list", list);
				ret.put("code",600 );
				ret.put("message", "查询解冻信息表数据异常");
				ret.put("data", data);
				responseWriteInfo(ret.toJSONString(), response);
				return;
			}
			
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
		}
		
		
		
	
		
		ret.put("code", 200);
		ret.put("message", "");
		data.put("ylqAmount", String.valueOf(ylqAmount));
		data.put("dlqCount", String.valueOf(dlqCount));
		data.put("jdAmount", String.valueOf(jdAmount));
		data.put("freezeDay", String.valueOf(minDay));
		
		data.put("list", list);
		ret.put("data", data);
		responseWriteInfo(ret.toJSONString(), response);
		
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
	
	
	
	public HttpServletResponse responseWriteInfo(int code,String message,JSONObject data,HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8"); 
		try {
			JSONObject ret = new JSONObject();
    		ret.put("code", code);
    		ret.put("message", message);
    		if(data ==null) {
    			ret.put("data", "");
    		}else {
    			ret.put("data", data);
    		}
			response.getWriter().write(ret.toJSONString());
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

	
	
    
    @RequestMapping("/getBillOrder")
	/**
	 * 领红包
	 * @param request
	 * @param response
	 */
	public  void getBillOrder(HttpServletRequest request,HttpServletResponse response){
    	//根绝cookie获取request方法
		int user_id = CookieUtil.getUserId(request, response);
		if(user_id == -1 || user_id<0) {
			responseWriteInfo(401, "未登录", null, response);
			return;
		}
    	//int user_id = 15;
    	String mode = request.getParameter("mode") ==null ? "" : request.getParameter("mode");//mode 0 单抽  1批量抽
    	if("".equals(mode)) {
    		responseWriteInfo(600, "mode格式异常", null, response);
    		return;
    	}
    	int limit;
    	if(mode.equals("1")) {
    		limit = 10000;
    	}else if(mode.equals("0")){
    		limit = 1;
    	}else {
    		responseWriteInfo(600, "mode格式异常", null, response);
    		return;
    	}
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("user_id", user_id);
    	map.put("limit", limit);
    	map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
    	List<Ttz_bill_orders> ttz_bill_orders = ttz_bill_ordersService.getRedPacket(map);
    	
    	if(ttz_bill_orders ==null || ttz_bill_orders.size()<=0) {
    		responseWriteInfo(600, "红包数量为0", null, response);
    		return;
    	}
    	List<Integer> orderIds = new ArrayList<Integer>();
    	BigDecimal decimal = new BigDecimal("0");
    	for(Ttz_bill_orders order : ttz_bill_orders ) {
    		decimal = decimal.add(order.getCommission());
    		decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    		orderIds.add(order.getId());
    	}
    	map = new HashMap<String, Object>();
    	map.put("status", "2");
    	map.put("update_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
    	map.put("ids", orderIds);
    	map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
    	int count = ttz_bill_ordersService.updateRedPacket(map);
    	if(count<=0) {
    		responseWriteInfo(600, "领取红包失败", null, response);
    		return;
    	}
    	
    	
    	JSONObject data = new JSONObject();
		data.put("lqAmout", String.valueOf(decimal));//领取红包金额
		data.put("count", count);//领取红包数量
		responseWriteInfo(200, "", data, response);
		return;
    	
    	
    }
    
    @RequestMapping("/unfreeze")
   	/**
   	 * 解冻金额
   	 * @param request
   	 * @param response
   	 */
   	public  void unFreeze(HttpServletRequest request,HttpServletResponse response){
    	//根绝cookie获取request方法
		int user_id = CookieUtil.getUserId(request, response);
		if(user_id == -1 || user_id<0) {
			responseWriteInfo(401, "未登录", null, response);
			return;
		}
    	//int user_id =15;
    	
    	List<Ttz_unfreeze> ttz_unfreezes = new ArrayList<Ttz_unfreeze>();
    	BigDecimal totalAmount = new BigDecimal("0.00");
    	Ttz_unfreeze ttz_unfreeze = new Ttz_unfreeze();
    	Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hb_rate;
		String hb_day = ConfigService.selectConfig("ttz","hbdetail","hb_day");
		if(hb_day ==null || hb_day.equals("")) {
			 hb_day = "7";
		}

		double hb_day2 =Double.valueOf(hb_day);
		hb_rate = ConfigService.selectConfig("ttz","hbdetail","hb_rate");
		if(hb_rate ==null) {
			hb_day = "5,10,10,15,20,25,15";
		}
		String[] rates = hb_rate.split(",");
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -90);
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		map.put("receive_time", sdf.format(calendar2.getTime()));
		List<Ttz_unfreeze> notUnfreezes = ttz_bill_ordersService.getNotunFreezeInfo(map);
		int minDay =10;
		//计算最少解冻日期
		long l1;
		Date now = new Date();
		long l2 = now.getTime();
		double floor;
		DecimalFormat df = new DecimalFormat("0.00");
		int sub;
		int nowRate = 0;
		for(Ttz_unfreeze info : notUnfreezes) {
			try {
			int rateNum = 0;
			l1 = sdf.parse(info.getReceiveTime()).getTime();
			sub= (int) ((l2-l1)/1000);
			floor = Math.floor(Double.valueOf(df.format((float) sub/(86400*hb_day2))));
			//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
			for(int i=0;i<floor;i++) {
				rateNum = rateNum +Integer.valueOf(rates[i]);
			}
			nowRate = rateNum;
			if(nowRate<0) {
				responseWriteInfo(600, "计算解冻比例异常", null, response);
				return;
			}
			info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			info.setStatus((byte)0);
			info.setRate(nowRate);
			info.setAmount(Rand(info.getAmount(), nowRate));
			ttz_unfreezes.add(info);
			totalAmount = totalAmount.add(info.getAmount());
			
			} catch (ParseException e) {
				responseWriteInfo(600, "ParseException Exc:"+e.getMessage(), null, response);
				logger.error("selectFreezeInfo后续解析异常",e);
				return;
			
			}
		}
    	
    	
		map = new HashMap<String, Object>();
    	map.put("user_id", user_id);
		
		map.put("create_time", calendar.getTime().getTime()/1000);
		List<Ttz_unfreeze> freezeInfos = ttz_bill_ordersService.selectFreezeInfo(map);
		
		
		if((freezeInfos ==null || freezeInfos.size()<=0) && ttz_unfreezes.size()<=0) {
			responseWriteInfo(600, "未找到需要解冻的金额", null, response);
			return;
		}
		
		//计算最少解冻日期
		now = new Date();
		l2 = now.getTime();
		nowRate = 0;
		for(Ttz_unfreeze info : freezeInfos) {
			try {
				int rateNum = 0;
				l1 = sdf.parse(info.getReceiveTime()).getTime();
				sub= (int) ((l2-l1)/1000);
				floor = Math.floor(Double.valueOf(df.format((float) sub/(86400*hb_day2))));
				//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
				for(int i=0;i<floor;i++) {
					rateNum = rateNum +Integer.valueOf(rates[i]);
				}
				nowRate = rateNum - info.getRate();
				if(nowRate<0) {
					responseWriteInfo(600, "计算解冻比例异常", null, response);
					return;
				}else if(info.getRate() == rateNum) {//相等，说明本周已领
					continue;
				}
				info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				info.setStatus((byte)0);
				info.setRate(nowRate);
				info.setAmount(Rand(info.getAmount(), nowRate));
				ttz_unfreezes.add(info);
				totalAmount = totalAmount.add(info.getAmount());
				
				//xx
//				if(info.getRate() == rateNum) {//相等，说明本周已领
//					double a =Math.ceil((1-calculateProfit(Double.valueOf(df.format((float) sub/(86400*hb_day2)))))*hb_day2);
//					day =(int) a;
//				}else {//说明本周或前几周未领
//					minDay = 0;
//					rateNum - info.getRate()
//					break;
//				}
//				if(day<minDay) {
//					minDay = day;
//				}
				
			} catch (ParseException e) {
				responseWriteInfo(600, "ParseException Exc:"+e.getMessage(), null, response);
				logger.error("selectFreezeInfo后续解析异常",e);
				return;
			}
		}
		
		
		if(ttz_unfreezes == null || ttz_unfreezes.size()<=0) {
			responseWriteInfo(600, "解冻金额数量为0,请检查", null, response);
			return;
		}
		int count = ttz_bill_ordersService.insertUnfreezes(ttz_unfreezes);
		if(count <=0) {
			responseWriteInfo(600, "解冻金额数量为0,请检查", null, response);
			return;
		}
		
		
		JSONObject data = new JSONObject();
		
		data.put("totalAmount", totalAmount.toString());
		data.put("count", count);
		responseWriteInfo(200, "", data, response);
    	
    }
	public static void main(String[] args) {
		//System.err.println(calculateProfit(111.11));
		//Rand2();
	}
	
	/**
	 * 计算随机金额
	 * @param amount
	 * @param rate
	 * @return
	 */
	public static BigDecimal Rand(BigDecimal amount,int rate) {
		amount = amount.multiply(new BigDecimal("100"));
		int min = 0;
		int max = amount.intValue();
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		System.err.println(s);
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal retAmount = new BigDecimal((df.format((float) s*rate/10000)));
		if(retAmount.compareTo(new BigDecimal("0.01")) ==-1) {
			retAmount = new BigDecimal("0.01");
		}
		return retAmount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
