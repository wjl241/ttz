package com.cn.ttz.controller;

import java.io.BufferedReader;
import java.io.File;
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
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.ttz.pojo.Jihes_config;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_unfreeze;
import com.cn.ttz.service.ConfigService;
import com.cn.ttz.service.Ttz_bill_ordersService;

import util.BASE64;
import util.ConfigJson;
import util.CookieUtil;
import util.ForFile;
import util.HttpClientUtils;

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
	
	
	
	
	/**
     * 判断多级路径是否存在，不存在就创建 
     *  
     * @param filePath 支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
     */
  
    public static void isExistDir(String filePath) {
        String paths[] = {""};
        //切割路径
        try {
            String tempPath = new File(filePath).getCanonicalPath();//File对象转换为标准路径并进行切割，有两种windows和linux
            paths = tempPath.split("\\\\");//windows            
            if(paths.length==1){paths = tempPath.split("/");}//linux
        } catch (IOException e) {
            System.out.println("切割路径错误");
            e.printStackTrace();
        }
        //判断是否有后缀
        boolean hasType = false;
        if(paths.length>0){
            String tempPath = paths[paths.length-1];
            if(tempPath.length()>0){
                if(tempPath.indexOf(".")>0){
                    hasType=true;
                }
            }
        }        
        //创建文件夹
        String dir = paths[0];
        for (int i = 0; i < paths.length - (hasType?2:1); i++) {// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
            try {
                dir = dir + "/" + paths[i + 1];//采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("成功创建目录：" + dirFile.getCanonicalFile());
                }
            } catch (Exception e) {
                System.err.println("文件夹创建发生异常");
                e.printStackTrace();
            }
        }
    }  
	
	@RequestMapping("/test")
	public  void test(HttpServletRequest request,HttpServletResponse response) {
		String path = System.getProperty("user.dir") + File.separator + "orders.xls";
		String path2 =new File("").getAbsolutePath() + File.separator + "orders.xls";
		String[] a = new String[2];
		a[0] = path;
		a[1] =path2;
		path = path.replace("//", "/");
		//boolean a1 =deleteFile("/test/ttz/aa.txt");
		String info = "";
		File file2 = new File("/usr/local/tomcat_test/webapps/ttz/WEB-INF/classes/com/cn/ttz/controller/b");
		info=info+"0";
		if(!file2.exists()) {
			info=info+"1";
			file2.setWritable(true,false);
			boolean aaa = file2.mkdir();
			if(aaa) {
				info = info + "T";
			}else {
				info = info + "F";
			}
		}
		
		String hehe  = getPath();
		info=info+"2";
		info = ForFile.createFile("/usr/local/tomcat_test/webapps/ttz/WEB-INF/classes/com/cn/ttz/controller/aa.txt", "abcdefgadfsasdf",info);
		info = info + "9";
		
//		File f = new File ("/test/ttz/aa.txt");
//		if(!f.exists()) {
//			try {
//				f.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String b = "没删除";
//		if(a1) {
//			b ="删除";
//		}
		responseWriteInfo(200, path+","+path2+","+"hehe"+",info:"+info+"hehe:"+hehe, null, response);
	}
	
	@RequestMapping("/test2")
	public  void test2(HttpServletRequest request,HttpServletResponse response) {
		boolean success = ConfigService.selectConfig2("ttz","hbdetail","hb_day");
		if(success) {
			responseWriteInfo(200, "缓存成功", null, response);
		}else {
			responseWriteInfo(200, "缓存未成功", null, response);
		}
		return;
	
		
	}
	  public static String test() throws IOException {  
	        String filePath = "."+File.separator +"farParentfile" + File.separator + "parentFile"+File.separator + "test.jsp";  
	          String info = "";
	        File file = new File(filePath);  
	          
	          
	        if (!file.getParentFile().exists()) {  
	            if (!file.getParentFile().mkdirs()) {  
	            }  
	        }  
	  
	          
	        if(!file.exists()) {  
	            try {  
	                file.createNewFile();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	          
	        System.out.println("exist: " +file.exists() );  
	        info = info + "exist: " +file.exists() ;
	          
	        System.out.println("path: " + file.getCanonicalPath());  
	        info = info + ",path: " + file.getCanonicalPath();
	        return info;
	    }  
	@RequestMapping("/infos")
	/**
	 * 获取红包信息
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
		String noUserRedPacket = "想要更多红包?赶紧分享或参加其他团团呗~";
		String useRedPacket = "今日事今日毕，你真棒！；每天醒来，发现你和红包都在；带我败家带我飞，抢到红包so happy；红包我出，你开心就好；终于找到失散多年的红包君了！；世间始终你好，红包比比谁高；大金链子小手表，抢了红包吃烧烤；别紧张，我领个红包就走；世界那么大，我想领完红包就去看看。";
		JSONObject ret = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject jb = new JSONObject(); 
		response.setContentType("application/json;charset=utf-8"); 
		String test_id = request.getParameter("test_id") == null ? "" : request.getParameter("test_id");
		int user_id;
		if(!test_id.equals("")) {
			user_id = Integer.valueOf(test_id);
		}else {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}
	
//		int user_id = 5659;
		
		String hb_day = ConfigService.selectConfig("ttz","hbdetail","hb_day");
		if(hb_day ==null || hb_day.equals("")) {
			 hb_day = "7";
		}
		
		//转换积分rate 比例 如果是1就说明还是按RMB计算，大于1则转换积分
		String rate = ConfigService.selectConfig("ttz","hbdetail","rate");
		if(rate ==null || rate.equals("")) {
			rate = "1";
		}
		BigDecimal rateB = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		//获取最大信息
		List<Ttz_bill_orders> maxOrders =  ttz_bill_ordersService.selectMaxAmounts();
		if(maxOrders ==null || maxOrders.size()<=0) {//为空，就放默认数据
			list = addList(rate);
		}else {
			int count = 0 ;
			for(Ttz_bill_orders orders : maxOrders) {
				jb = new JSONObject();
				jb.put("sumAmount", orders.getCommission().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rate)).toString());
				jb.put("nickName", orders.getGoodId());
				jb.put("userId", orders.getUserId());
				count = ttz_bill_ordersService.getValidOrderCount(orders.getUserId());
				jb.put("number", count);//购买数量
				list.add(jb);
			}
		}
		
		String os_type = request.getParameter("os_type") == null ? "" : request.getParameter("os_type");
//		if(os_type.equals("2")) {//安卓
//			tuan = ttz_bill_ordersService.selectIsTuan(user_id);//是否参团成功 0 失败 1 成功
//		}else {//IOS
//			 tuan = ttz_bill_ordersService.selectYLQRedPacket(user_id);//是否成功
//		}
		int tuan = ttz_bill_ordersService.selectYLQRedPacket(user_id);//有红包就传1 没有传0
	
		if(tuan > 0) {
			data.put("tuan", "1");
		}else {
			data.put("tuan", "0");
		}
		
		
	
		
		
		int status = 2;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("status1", 2);//已领取
		map.put("status2", 4);//失效
		
		double ylqAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//已领取金额
		map.put("status2", 2);//失效
		double djdAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//所有需要解冻金额
		String ylqAmount_str = String.valueOf(ylqAmount);//返回的已领取金额
		String djdAmount_str = String.valueOf(djdAmount);//返回的待解冻金额
		if(!rate.equals("1")) {
			//将获取的数据乘以倍率，转换成积分
			BigDecimal yl = new BigDecimal(ylqAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal dj = new BigDecimal(ylqAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			ylqAmount_str = yl.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			djdAmount_str = dj.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		
		
		System.err.println("领取总数："+ylqAmount);
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int dlqCount = ttz_bill_ordersService.selectBillOrderCount(map);//待领取红包总数
		System.err.println("待领取总数："+dlqCount);
		
		if(dlqCount == 0) {
			data.put("showMessage", noUserRedPacket);
		}else {
			data.put("showMessage", "");
		}
		
		if(ylqAmount ==0 ) {//没有领取红包，没有待领取金额，直接返回
			data.put("ylqAmount", String.valueOf(ylqAmount_str));
			data.put("dlqCount", String.valueOf(dlqCount));
			if(!rate.equals("1")) {
				data.put("jdAmount", String.valueOf(0));
			}else {
				data.put("jdAmount", String.valueOf(0.00));
			}
			data.put("freezeDay", String.valueOf(-1));
			data.put("freezeTime", String.valueOf(-1));
			data.put("list", list);
			data.put("allDay", hb_day);//每轮总天数
			//data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
			data.put("freezeMessage", "暂时还没有红包需要解冻哦~快去参团领红包吧!");
			responseWriteInfo(200, "", data, response);
			return;
		}
		
	
		double txAmount = ttz_bill_ordersService.selectAmountByUserId(user_id);//已提现金额
		BigDecimal a1 = new BigDecimal(djdAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal a2 = new BigDecimal(txAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		double jdAmount = a1.subtract(a2).doubleValue();//解冻金额
		if(jdAmount<=0) {//12.18临时补丁，后期修改整个逻辑
			jdAmount = 0.00;
			ret.put("code", 200);
			ret.put("message", "");
			data.put("ylqAmount", ylqAmount_str);
			data.put("dlqCount", String.valueOf(dlqCount));
			data.put("jdAmount", String.valueOf(jdAmount));//jdAmount   djdAmount_str
			data.put("freezeDay", String.valueOf(-1));
			data.put("freezeTime", String.valueOf(-1));
			data.put("freezeMessage", "暂时还没有红包需要解冻哦~快去参团领红包吧!");
			data.put("allDay", hb_day);//每轮总天数
			
			data.put("list", list);
			ret.put("data", data);
			responseWriteInfo(ret.toJSONString(), response);
			return;
		}
	
		
		
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hb_rate;
	

		
		Calendar calendar = Calendar.getInstance();
		//TODO 测试库使用
		String valueTime = ConfigService.selectConfig("ttz","hbdetail","valueTime");
		if(rate ==null || rate.equals("")) {
			rate = "60";
		}
		calendar.add(Calendar.DATE, -Integer.valueOf(valueTime));
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		try {
			map.put("receive_time", sdf2.parse(sdf.format(calendar2.getTime())+" 23:59:59").getTime()/1000);
		} catch (ParseException e1) {
			logger.error("解析日期出错", e1);
		}
		//map.put("receive_time", sdf.format(calendar2.getTime())); //之前的处理，想用string进行大小值 判断，结果MySQL发现不理想
		
		List<Ttz_unfreeze> notUnfreezes = ttz_bill_ordersService.getNotunFreezeInfo(map);
		int minDay =10;
		int freezeTime=0;//最近需要解冻红包的时间戳
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
			
			
			if(freezeInfos ==null || freezeInfos.size()<=0) {//说明从未有过解冻信息,但有已领取的红包，此时计算解冻日期
				
				map = new HashMap<String, Object>();
				map.put("user_id", user_id);
				map.put("expire_time", calendar.getTime().getTime()/1000);//创建日期90天内的第一个红包
				List<Ttz_bill_orders> firstOrders = ttz_bill_ordersService.selectFirstBill(map);
				if(firstOrders ==null || firstOrders.size()==0) {
					responseWriteInfo(600, "红包数据异常", null, response);
					return ;
				}
				String firstReceiveTime = firstOrders.get(0).getReceiveTime();
				long l1;
					try {
						l1 = sdf.parse(firstReceiveTime).getTime();
						Date now2 = new Date();
						long l2 = now2.getTime();
						int sub2= (int) ((l2-l1)/1000);
						DecimalFormat df2 = new DecimalFormat("0.00");
						double hb_day3 =Double.valueOf(hb_day);
						double floor2 = Math.floor(Double.valueOf(df2.format((float) sub2/(86400*hb_day3))));//相差几周，向下取整
						if(floor2>=1) {
							minDay = 0;
							data.put("freezeTime", "0");//时间戳，18.5.17日以后版本，用于计算后续时间
						}else {
							double a =Math.ceil((1-calculateProfit(Double.valueOf(df2.format((float) sub2/(86400*hb_day3)))))*hb_day3);
							minDay =(int) a;
							data.put("freezeTime", sub2+"");//时间戳，18.5.17日以后版本，用于计算后续时间
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
			
				data.put("freezeDay", minDay);
				data.put("list", list);
				ret.put("code",200 );
				ret.put("message", "");
				ret.put("data", data);
				data.put("allDay", hb_day);//每轮总天数
				
				
				data.put("ylqAmount", ylqAmount_str);
				data.put("dlqCount", String.valueOf(dlqCount));
				data.put("jdAmount", String.valueOf(jdAmount));
				
				if(minDay<=0) {
					data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
				}else {
					//data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩:"+minDay+"天");
					data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩 ");
				}
				
				responseWriteInfo(ret.toJSONString(), response);
				return;
			}
			
			
			List<Ttz_unfreeze> freezeInfos2 = ttz_bill_ordersService.selectFreezeInfoNoFreeze(map);//所有有已领取红包记录的解冻集合
			List<Ttz_unfreeze> freezeInfos3 = new ArrayList<Ttz_unfreeze>();
			boolean has = false;
			for(Ttz_unfreeze info2 : freezeInfos2) {
				has = false;
				for(Ttz_unfreeze info : freezeInfos) {
					if(info.getReceiveTime().equals(info2.getReceiveTime())) {//若解冻信息已经有了，则跳过
						has =true;
						break;
					}
				}
				if(!has) {//若info2是解冻信息都没有的，则加入到总信息中
					freezeInfos3.add(info2);
				}
				
			}
			freezeInfos3.addAll(freezeInfos);
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
			DecimalFormat df = new DecimalFormat("0.00000000");
			int sub;
		
			for(Ttz_unfreeze info : freezeInfos3) {
				try {
					int rateNum = 0;
					int day;
					l1 = sdf.parse(info.getReceiveTime()).getTime();
					sub= (int) ((l2-l1)/1000);
					floor = Math.floor(Double.valueOf(df.format((float) sub/(86400*hb_day2))));
					//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整\
					if(floor>7) {
						continue;
					}
					int min = Math.min((int)floor, rates.length);
					for(int i=0;i<min;i++) {
						rateNum = rateNum +Integer.valueOf(rates[i]);
					}
					if(info.getRate() == rateNum || rateNum< info.getRate()) {//相等，说明本周已领,小于一般只会出现在修改领取周期的情况下
						double a =Math.ceil((1-calculateProfit(Double.valueOf(df.format((float) sub/(86400*hb_day2)))))*hb_day2);
						day =(int) a;
					}else {//说明本周或前几周未领
						minDay = 0;
						break;
					}
					if(day<minDay) {
						minDay = day;
						freezeTime = sub;
					}
					
				} catch (ParseException e) {
					logger.error("selectFreezeInfo后续解析异常",e);
					responseWriteInfo(600, "selectFreezeInfo后续解析异常"+e.getMessage(), null, response);
					return;
				}
			}
		}
		
		
		if(minDay==10 && jdAmount>0) {
			minDay =0;
		}
	
		
		ret.put("code", 200);
		ret.put("message", "");
		data.put("ylqAmount", ylqAmount_str);
		data.put("dlqCount", String.valueOf(dlqCount));
		data.put("jdAmount", String.valueOf(jdAmount));//jdAmount   djdAmount_str
		data.put("freezeDay", String.valueOf(minDay));
		if(minDay<=0) {
			data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
			data.put("freezeTime", "0");
		}else {
			data.put("freezeTime", freezeTime+"");
			//data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩:"+minDay+"天");
			data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩 ");
		}
		data.put("allDay", hb_day);//每轮总天数
		
		data.put("list", list);
		ret.put("data", data);
		responseWriteInfo(ret.toJSONString(), response);
		
	}
	
	
	@RequestMapping("/infosTest")
	/**
	 * 获取红包信息测试接口
	 * @param request
	 * @param response
	 */
	public  void getBillOrderInfoTest(HttpServletRequest request,HttpServletResponse response){
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
		String noUserRedPacket = "想要更多红包?赶紧分享或参加其他团团呗~";
		String useRedPacket = "今日事今日毕，你真棒！；每天醒来，发现你和红包都在；带我败家带我飞，抢到红包so happy；红包我出，你开心就好；终于找到失散多年的红包君了！；世间始终你好，红包比比谁高；大金链子小手表，抢了红包吃烧烤；别紧张，我领个红包就走；世界那么大，我想领完红包就去看看。";
		JSONObject ret = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject jb = new JSONObject(); 
		response.setContentType("application/json;charset=utf-8"); 
		String test_id = request.getParameter("test_id") == null ? "" : request.getParameter("test_id");
		int user_id;
		if(!test_id.equals("")) {
			user_id = Integer.valueOf(test_id);
		}else {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}
	
//		int user_id = 5659;
		
		String hb_day = ConfigService.selectConfig("ttz","hbdetail","hb_day");
		if(hb_day ==null || hb_day.equals("")) {
			 hb_day = "7";
		}
		
		//转换积分rate 比例 如果是1就说明还是按RMB计算，大于1则转换积分
		String rate = ConfigService.selectConfig("ttz","hbdetail","rate");
		if(rate ==null || rate.equals("")) {
			rate = "1";
		}
		BigDecimal rateB = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		//获取最大信息
		List<Ttz_bill_orders> maxOrders =  ttz_bill_ordersService.selectMaxAmounts();
		if(maxOrders ==null || maxOrders.size()<=0) {//为空，就放默认数据
			list = addList(rate);
		}else {
			int count = 0 ;
			for(Ttz_bill_orders orders : maxOrders) {
				jb = new JSONObject();
				jb.put("sumAmount", orders.getCommission().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rate)).toString());
				jb.put("nickName", orders.getGoodId());
				jb.put("userId", orders.getUserId());
				count = ttz_bill_ordersService.getValidOrderCount(orders.getUserId());
				jb.put("number", count);//购买数量
				list.add(jb);
			}
		}
		
		String os_type = request.getParameter("os_type") == null ? "" : request.getParameter("os_type");
//		if(os_type.equals("2")) {//安卓
//			tuan = ttz_bill_ordersService.selectIsTuan(user_id);//是否参团成功 0 失败 1 成功
//		}else {//IOS
//			 tuan = ttz_bill_ordersService.selectYLQRedPacket(user_id);//是否成功
//		}
		int tuan = ttz_bill_ordersService.selectYLQRedPacket(user_id);//有红包就传1 没有传0
	
		if(tuan > 0) {
			data.put("tuan", "1");
		}else {
			data.put("tuan", "0");
		}
		
		
	
		
		
		int status = 2;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("status1", 2);//已领取
		map.put("status2", 4);//失效
		
		double ylqAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//已领取金额
		map.put("status2", 2);//失效
		double djdAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//所有需要解冻金额
		String ylqAmount_str = String.valueOf(ylqAmount);//返回的已领取金额
		String djdAmount_str = String.valueOf(djdAmount);//返回的待解冻金额
		if(!rate.equals("1")) {
			//将获取的数据乘以倍率，转换成积分
			BigDecimal yl = new BigDecimal(ylqAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal dj = new BigDecimal(ylqAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			ylqAmount_str = yl.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			djdAmount_str = dj.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		
		
		System.err.println("领取总数："+ylqAmount);
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int dlqCount = ttz_bill_ordersService.selectBillOrderCount(map);//待领取红包总数
		System.err.println("待领取总数："+dlqCount);
		
		if(dlqCount == 0) {
			data.put("showMessage", noUserRedPacket);
		}else {
			data.put("showMessage", "");
		}
		
		if(ylqAmount ==0 ) {//没有领取红包，没有待领取金额，直接返回
			data.put("ylqAmount", String.valueOf(ylqAmount_str));
			data.put("dlqCount", String.valueOf(dlqCount));
			if(!rate.equals("1")) {
				data.put("jdAmount", String.valueOf(0));
			}else {
				data.put("jdAmount", String.valueOf(0.00));
			}
			data.put("freezeDay", String.valueOf(-1));
			data.put("freezeTime", String.valueOf(-1));
			data.put("list", list);
			data.put("allDay", hb_day);//每轮总天数
			//data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
			data.put("freezeMessage", "暂时还没有红包需要解冻哦~快去参团领红包吧!");
			responseWriteInfo(200, "", data, response);
			return;
		}
		
	
		double txAmount = ttz_bill_ordersService.selectAmountByUserId(user_id);//已提现金额
		BigDecimal a1 = new BigDecimal(djdAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal a2 = new BigDecimal(txAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		double jdAmount = a1.subtract(a2).doubleValue();//解冻金额
		if(jdAmount<=0) {//12.18临时补丁，后期修改整个逻辑
			jdAmount = 0.00;
			ret.put("code", 200);
			ret.put("message", "");
			data.put("ylqAmount", ylqAmount_str);
			data.put("dlqCount", String.valueOf(dlqCount));
			data.put("jdAmount", String.valueOf(jdAmount));//jdAmount   djdAmount_str
			data.put("freezeDay", String.valueOf(-1));
			data.put("freezeTime", String.valueOf(-1));
			data.put("freezeMessage", "暂时还没有红包需要解冻哦~快去参团领红包吧!");
			data.put("allDay", hb_day);//每轮总天数
			
			data.put("list", list);
			ret.put("data", data);
			responseWriteInfo(ret.toJSONString(), response);
			return;
		}
	
		
		
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hb_rate;
	

		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -90);
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		try {
			map.put("receive_time", sdf2.parse(sdf.format(calendar2.getTime())+" 23:59:59").getTime()/1000);
		} catch (ParseException e1) {
			logger.error("解析日期出错", e1);
		}
		//map.put("receive_time", sdf.format(calendar2.getTime())); //之前的处理，想用string进行大小值 判断，结果MySQL发现不理想
		
		List<Ttz_unfreeze> notUnfreezes = ttz_bill_ordersService.getNotunFreezeInfo(map);
		int minDay =10;
		int freezeTime=0;//最近需要解冻红包的时间戳
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
			
			
			if(freezeInfos ==null || freezeInfos.size()<=0) {//说明从未有过解冻信息,但有已领取的红包，此时计算解冻日期
				
				map = new HashMap<String, Object>();
				map.put("user_id", user_id);
				map.put("expire_time", calendar.getTime().getTime()/1000);//创建日期90天内的第一个红包
				List<Ttz_bill_orders> firstOrders = ttz_bill_ordersService.selectFirstBill(map);
				
				String firstReceiveTime = firstOrders.get(0).getReceiveTime();
				long l1;
					try {
						l1 = sdf.parse(firstReceiveTime).getTime();
						Date now2 = new Date();
						long l2 = now2.getTime();
						int sub2= (int) ((l2-l1)/1000);
						DecimalFormat df2 = new DecimalFormat("0.00");
						double hb_day3 =Double.valueOf(hb_day);
						double floor2 = Math.floor(Double.valueOf(df2.format((float) sub2/(86400*hb_day3))));//相差几周，向下取整
						if(floor2>=1) {
							minDay = 0;
							data.put("freezeTime", "0");//时间戳，18.5.17日以后版本，用于计算后续时间
						}else {
							double a =Math.ceil((1-calculateProfit(Double.valueOf(df2.format((float) sub2/(86400*hb_day3)))))*hb_day3);
							minDay =(int) a;
							data.put("freezeTime", sub2+"");//时间戳，18.5.17日以后版本，用于计算后续时间
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
			
				data.put("freezeDay", minDay);
				data.put("list", list);
				ret.put("code",200 );
				ret.put("message", "");
				ret.put("data", data);
				data.put("allDay", hb_day);//每轮总天数
				
				
				data.put("ylqAmount", ylqAmount_str);
				data.put("dlqCount", String.valueOf(dlqCount));
				data.put("jdAmount", String.valueOf(jdAmount));
				
				if(minDay<=0) {
					data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
				}else {
					//data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩:"+minDay+"天");
					data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩 ");
				}
				
				responseWriteInfo(ret.toJSONString(), response);
				return;
			}
			
			
			List<Ttz_unfreeze> freezeInfos2 = ttz_bill_ordersService.selectFreezeInfoNoFreeze(map);//所有有已领取红包记录的解冻集合
			List<Ttz_unfreeze> freezeInfos3 = new ArrayList<Ttz_unfreeze>();
			boolean has = false;
			for(Ttz_unfreeze info2 : freezeInfos2) {
				has = false;
				for(Ttz_unfreeze info : freezeInfos) {
					if(info.getReceiveTime().equals(info2.getReceiveTime())) {//若解冻信息已经有了，则跳过
						has =true;
						break;
					}
				}
				if(!has) {//若info2是解冻信息都没有的，则加入到总信息中
					freezeInfos3.add(info2);
				}
				
			}
			freezeInfos3.addAll(freezeInfos);
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
			DecimalFormat df = new DecimalFormat("0.00000000");
			int sub;
		
			for(Ttz_unfreeze info : freezeInfos3) {
				try {
					int rateNum = 0;
					int day;
					l1 = sdf.parse(info.getReceiveTime()).getTime();
					sub= (int) ((l2-l1)/1000);
					floor = Math.floor(Double.valueOf(df.format((float) sub/(86400*hb_day2))));
					//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整\
					if(floor>7) {
						continue;
					}
					int min = Math.min((int)floor, rates.length);
					for(int i=0;i<min;i++) {
						rateNum = rateNum +Integer.valueOf(rates[i]);
					}
					if(info.getRate() == rateNum || rateNum< info.getRate()) {//相等，说明本周已领,小于一般只会出现在修改领取周期的情况下
						double a =Math.ceil((1-calculateProfit(Double.valueOf(df.format((float) sub/(86400*hb_day2)))))*hb_day2);
						day =(int) a;
					}else {//说明本周或前几周未领
						minDay = 0;
						break;
					}
					if(day<minDay) {
						minDay = day;
						freezeTime = sub;
					}
					
				} catch (ParseException e) {
					logger.error("selectFreezeInfo后续解析异常",e);
					responseWriteInfo(600, "selectFreezeInfo后续解析异常"+e.getMessage(), null, response);
					return;
				}
			}
		}
		
		
		if(minDay==10 && jdAmount>0) {
			minDay =0;
		}
	
		
		ret.put("code", 200);
		ret.put("message", "");
		data.put("ylqAmount", ylqAmount_str);
		data.put("dlqCount", String.valueOf(dlqCount));
		data.put("jdAmount", String.valueOf(jdAmount));//jdAmount   djdAmount_str
		data.put("freezeDay", String.valueOf(minDay));
		if(minDay<=0) {
			data.put("freezeMessage", "被解冻的红包金额，可立即提现!");
			data.put("freezeTime", "0");
		}else {
			data.put("freezeTime", freezeTime+"");
			//data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩:"+minDay+"天");
			data.put("freezeMessage", "购物可加速解冻!距离下次解冻还剩 ");
		}
		data.put("allDay", hb_day);//每轮总天数
		
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
    	
    	String useRedPacket = "今日事今日毕，你真棒！;每天醒来，发现你和红包都在;带我败家带我飞，抢到红包so happy;红包我出，你开心就好;终于找到失散多年的红包君了！;世间始终你好，红包比比谁高;大金链子小手表，抢了红包吃烧烤;别紧张，我领个红包就走;世界那么大，我想领完红包就去看看";
    	
    	String[] showMessages = useRedPacket.split(";");
    	
    	int status = 2;
    	Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("status1", 2);//已领取
		map.put("status2", 4);//失效
		
		double ylqAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//已领取金额
		map.put("status2", 2);//失效
		//double djdAmount = ttz_bill_ordersService.selectBillOrderAmout(map);//所有需要解冻金额
		System.err.println("领取总数："+ylqAmount);
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int dlqCount = ttz_bill_ordersService.selectBillOrderCount(map);//待领取红包总数
		if(mode.equals("1")) {
			dlqCount = 0;
		}else {
			dlqCount =dlqCount -1;
		}
		int number = new Random().nextInt(8);
		String showMessage = "";
		if(dlqCount  == 0) {
			showMessage = showMessages[number];
		}
		JSONObject data = new JSONObject();
		data.put("showMessage", showMessage);
		System.err.println("待领取总数："+dlqCount);
    	
    	
    	
		map = new HashMap<String, Object>();
    	map.put("user_id", user_id);
    	map.put("limit", limit);
    	map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
    	List<Ttz_bill_orders> ttz_bill_orders = ttz_bill_ordersService.getRedPacket(map);
    	
    	if(ttz_bill_orders ==null || ttz_bill_orders.size()<=0) {
    		responseWriteInfo(600, "红包数量为0，无法领取", null, response);
    		return;
    	}
    	
    	//转换积分rate 比例 如果是1就说明还是按RMB计算，大于1则转换积分
		String rate = ConfigService.selectConfig("ttz","hbdetail","rate");
		if(rate ==null || rate.equals("")) {
			rate = "1";
		}
		BigDecimal rateB = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    	
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
    	
    	
    	
    	
    
    	
    	String ylqAmount2 = new BigDecimal(ylqAmount).add(decimal).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    	
    	if(rate.equals("1")) {//若为1 返回金额RMB double型  否则乘倍率后返回积分，int型
    		data.put("lqAmout", String.valueOf(decimal));//领取红包金额
    		data.put("ylqAmount", ylqAmount2);//已领取红包金额
    	}else {
    		data.put("lqAmout", decimal.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
    		data.put("ylqAmount", new BigDecimal(ylqAmount2).multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
    	}
		data.put("lqAmout", String.valueOf(decimal));//领取红包金额
		data.put("count", count);//领取红包数量
		data.put("dlqCount", dlqCount);//待领取红包数量
		responseWriteInfo(200, "", data, response);
		return;
    	
    	
    }
    
    @RequestMapping("/unfreezeTest")
   	/**
   	 * 解冻金额
   	 * @param request
   	 * @param response
   	 */
   	public  void unfreezeTest(HttpServletRequest request,HttpServletResponse response){
    	//根绝cookie获取request方法
//		int user_id = CookieUtil.getUserId(request, response);
//		if(user_id == -1 || user_id<0) {
//			responseWriteInfo(401, "未登录", null, response);
//			return;
//		}
	//	int user_id =5659;
    	String  id = request.getParameter("user_id") == null ? "" :request.getParameter("user_id");
    	if(id.equals("")) {
    		return;
    	}
    	int user_id =Integer.valueOf(id);
    	List<Ttz_unfreeze> ttz_unfreezes = new ArrayList<Ttz_unfreeze>();
    	BigDecimal allAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount2 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount3 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount4 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal totalAmount = new BigDecimal("0.00");
    	BigDecimal totalAmount2 = new BigDecimal("0.00");
    	BigDecimal totalAmount3 = new BigDecimal("0.00");
    	BigDecimal totalAmount4 = new BigDecimal("0.00");
    	Ttz_unfreeze ttz_unfreeze = new Ttz_unfreeze();
    	Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		
		
		//转换积分rate 比例 如果是1就说明还是按RMB计算，大于1则转换积分
		String rate = ConfigService.selectConfig("ttz","hbdetail","rate");
		if(rate ==null || rate.equals("")) {
			rate = "1";
		}
		BigDecimal rateB = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -90);
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		Calendar calendar2 = Calendar.getInstance();
		//calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		//map.put("receive_time", sdf.format(calendar2.getTime()));
		
		calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		try {
			map.put("receive_time", sdf2.parse(sdf.format(calendar2.getTime())+" 23:59:59").getTime()/1000);
		} catch (ParseException e1) {
			logger.error("解析日期出错", e1);
		}
		
		
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
			int min = Math.min((int)floor, rates.length);
			//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
			for(int i=0;i< min;i++) {
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
			
			
			totalAmount = Rand(info.getAmount(), nowRate);
			totalAmount2 = Rand(info.getAmount(), nowRate);
			totalAmount3 = Rand(info.getAmount(), nowRate);
			totalAmount4 = Rand(info.getAmount(), nowRate);
			info.setAmount(totalAmount);
			allAmount = allAmount.add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount2 = allAmount2.add(totalAmount2).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount3 = allAmount3.add(totalAmount3).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount4 = allAmount4.add(totalAmount4).setScale(2, BigDecimal.ROUND_HALF_UP);
			ttz_unfreezes.add(info);
			
			} catch (ParseException e) {
				responseWriteInfo(600, "ParseException Exc:"+e.getMessage(), null, response);
				logger.error("selectFreezeInfo后续解析异常",e);
				return;
			
			}
		}
    	
    	
		map = new HashMap<String, Object>();
    	map.put("user_id", user_id);
		
		map.put("create_time", calendar.getTime().getTime()/1000);//90天
		List<Ttz_unfreeze> freezeInfos = ttz_bill_ordersService.selectFreezeInfo(map);
		
		
		if((freezeInfos ==null || freezeInfos.size()<=0) && ttz_unfreezes.size()<=0) {
			//没有解冻信息，没有未使用解冻信息，此时还需要解冻的话，查找所有的红包，按照红包的时间来进行解冻
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
				if(floor>=8) {//超过7周，则一次性领齐
					Map<String,Object> sendMap = new HashMap<String,Object>();
					sendMap.put("user_id", user_id);
					sendMap.put("receive_time", info.getReceiveTime());
					double ylqAmout = ttz_bill_ordersService.getYLQAmount(sendMap);
					BigDecimal YQLAmount = new BigDecimal(ylqAmout).setScale(BigDecimal.ROUND_HALF_UP, 2);
					if(YQLAmount.compareTo(info.getAmount())>=0) {//若金额大于需要解冻的总金额，则跳过
						continue;
					}else {//否则，全解冻
						BigDecimal addAmount = info.getAmount().subtract(YQLAmount);
						
						info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						info.setStatus((byte)0);
						info.setRate(0);
						info.setAmount(addAmount);
						
//						totalAmount = totalAmount.add(addAmount);
//						totalAmount2 = totalAmount2.add(addAmount);
//						totalAmount3 = totalAmount3.add(addAmount);
//						totalAmount4 = totalAmount4.add(addAmount);
						info.setAmount(addAmount);
						allAmount = allAmount.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount2 = allAmount2.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount3 = allAmount3.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount4 = allAmount4.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						ttz_unfreezes.add(info);
					}
				}else {
					//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
					for(int i=0;i<floor;i++) {
						rateNum = rateNum +Integer.valueOf(rates[i]);
					}
					nowRate = rateNum - info.getRate();
					if(nowRate<0) {
						responseWriteInfo(200, "计算解冻比例异常", null, response);
						return;
					}else if(info.getRate() == rateNum) {//相等，说明本周已领
						continue;
					}
					info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
					info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
					info.setStatus((byte)0);
					info.setRate(nowRate);
					info.setAmount(Rand(info.getAmount(), nowRate));
					
					totalAmount = Rand(info.getAmount(), nowRate);
					totalAmount2 = Rand(info.getAmount(), nowRate);
					totalAmount3 = Rand(info.getAmount(), nowRate);
					totalAmount4 = Rand(info.getAmount(), nowRate);
					info.setAmount(totalAmount);
					allAmount = allAmount.add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount2 = allAmount2.add(totalAmount2).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount3 = allAmount3.add(totalAmount3).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount4 = allAmount4.add(totalAmount4).setScale(2, BigDecimal.ROUND_HALF_UP);
					ttz_unfreezes.add(info);
				}
			
				
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
		
		
		
		
		
		//入余额
		HttpClientUtils httpClientUtil2 = new HttpClientUtils();
		Map<String,String> createMap = new HashMap<String, String>();
		char[] aa = BASE64.encode(intToBytes(user_id));
		//createMap.put("user_id", new String(aa,0,aa.length));
		createMap.put("user_id", String.valueOf(user_id));
		createMap.put("type", String.valueOf(12));
		createMap.put("balance", allAmount.toString());
		if(!rate.equals("1")) {
			createMap.put("balance", allAmount.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		}
		createMap.put("rate", rate);
		
		
		String os_type = request.getParameter("os_type") == null ? "" : request.getParameter("os_type");
		String os_version = request.getParameter("os_version") == null ? "" : request.getParameter("os_version");
		String app_version = request.getParameter("app_version") == null ? "" : request.getParameter("app_version");
		String api_version = request.getParameter("api_version") == null ? "" : request.getParameter("api_version");
		String device_name = request.getParameter("device_name") == null ? "" : request.getParameter("device_name");
		String client_id = request.getParameter("client_id") == null ? "" : request.getParameter("client_id");
		String sign = request.getParameter("sign") == null ? "" : request.getParameter("sign");
		String t = request.getParameter("t") == null ? "" : request.getParameter("t");
		
		
		boolean insert  = true;
		int code =200;
		String message = "";
//		String api_url = "http://api.jihes.com/ttz/balance?os_type="+os_type+"&os_version="+os_version+"&app_version="+app_version+"&api_version="+api_version+"&device_name="+device_name+"&client_id="+client_id+"&sign="+sign+"&t="+t;
//
//		api_url = api_url.replace(" ", "%20");
//		
//		String httpOrgCreateTestRtn2 = httpClientUtil2.doPost2(api_url,createMap,"utf-8"); 
//		int code =200;
//		String message = "";
//		boolean insert = true;
//		if(httpOrgCreateTestRtn2 == null || httpOrgCreateTestRtn2.equals("")) {
//			code = 600;
//			insert = false;
//			message = "入余额接口返回结果为空";
//		}else {
//			JSONObject jb = new JSONObject();
//			
//			jb = (JSONObject) JSONObject.parse(httpOrgCreateTestRtn2);
//			if(jb== null ) {//回滚数据
//				insert =false;
//				code = 600;
//				message = "空指针异常";
//			}else {
//				code = (Integer) jb.get("code");
//				message =  jb.get("message") == null ? "" : (String) jb.get("message");
//				if(code == 600) {//回滚数据
// 					insert =false;
//				}
//			}
//		}
		
		
	
		
//		boolean insert =true;
//		String httpOrgCreateTestRtn2 = "";
//		int code =200;
//		String message= "";
		
		
		int count = 0;
		if(insert) {
			count = ttz_bill_ordersService.insertUnfreezes(ttz_unfreezes);
		}
		
		
		
		
		
		
		JSONObject data = new JSONObject();
		String[] total = new String[4];
		total[0] = allAmount.toString();
		total[1] = allAmount2.toString();
		total[2] = allAmount3.toString();
		total[3] = allAmount4.toString();
		if(!rate.equals("1")) {//按比例放大取整
			total[0]  =  allAmount.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[1]  =  allAmount2.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[2]  =  allAmount3.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[3]  =  allAmount4.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		data.put("totalAmount", total);
		data.put("count", count);
	//	data.put("reuslt", httpOrgCreateTestRtn2+",api_url:"+api_url);
		
		
		if(code == 200) {
			responseWriteInfo(200, "", data, response);
		}else if(code == 600) {
			responseWriteInfo(600, message, null, response);
		}else {
			responseWriteInfo(code, message, null, response);
		}
		
		
		
		
		
    	
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
	//	int user_id =5659;
    	
    	List<Ttz_unfreeze> ttz_unfreezes = new ArrayList<Ttz_unfreeze>();
    	BigDecimal allAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount2 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount3 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal allAmount4 = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal totalAmount = new BigDecimal("0.00");
    	BigDecimal totalAmount2 = new BigDecimal("0.00");
    	BigDecimal totalAmount3 = new BigDecimal("0.00");
    	BigDecimal totalAmount4 = new BigDecimal("0.00");
    	Ttz_unfreeze ttz_unfreeze = new Ttz_unfreeze();
    	Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		
		
		//转换积分rate 比例 如果是1就说明还是按RMB计算，大于1则转换积分
		String rate = ConfigService.selectConfig("ttz","hbdetail","rate");
		if(rate ==null || rate.equals("")) {
			rate = "1";
		}
		BigDecimal rateB = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		
		Calendar calendar = Calendar.getInstance();
		//TODO 测试用数据
		String valueTime = ConfigService.selectConfig("ttz","hbdetail","valueTime");
		if(rate ==null || rate.equals("")) {
			rate = "60";
		}
		calendar.add(Calendar.DATE, -Integer.valueOf(valueTime));
		
		//获取从未解冻过的红包
		map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("create_time", calendar.getTime().getTime()/1000);
		Calendar calendar2 = Calendar.getInstance();
		//calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		//map.put("receive_time", sdf.format(calendar2.getTime()));
		
		calendar2.add(Calendar.DATE, -Integer.valueOf(hb_day));
		try {
			map.put("receive_time", sdf2.parse(sdf.format(calendar2.getTime())+" 23:59:59").getTime()/1000);
		} catch (ParseException e1) {
			logger.error("解析日期出错", e1);
		}
		
		
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
			int min = Math.min((int)floor, rates.length);
			//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
			for(int i=0;i< min;i++) {
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
			
			
			totalAmount = Rand(info.getAmount(), nowRate);
			totalAmount2 = Rand(info.getAmount(), nowRate);
			totalAmount3 = Rand(info.getAmount(), nowRate);
			totalAmount4 = Rand(info.getAmount(), nowRate);
			info.setAmount(totalAmount);
			allAmount = allAmount.add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount2 = allAmount2.add(totalAmount2).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount3 = allAmount3.add(totalAmount3).setScale(2, BigDecimal.ROUND_HALF_UP);
			allAmount4 = allAmount4.add(totalAmount4).setScale(2, BigDecimal.ROUND_HALF_UP);
			ttz_unfreezes.add(info);
			
			} catch (ParseException e) {
				responseWriteInfo(600, "ParseException Exc:"+e.getMessage(), null, response);
				logger.error("selectFreezeInfo后续解析异常",e);
				return;
			
			}
		}
    	
    	
		map = new HashMap<String, Object>();
    	map.put("user_id", user_id);
		
		map.put("create_time", calendar.getTime().getTime()/1000);//90天
		List<Ttz_unfreeze> freezeInfos = ttz_bill_ordersService.selectFreezeInfo(map);
		
		
		if((freezeInfos ==null || freezeInfos.size()<=0) && ttz_unfreezes.size()<=0) {
			//没有解冻信息，没有未使用解冻信息，此时还需要解冻的话，查找所有的红包，按照红包的时间来进行解冻
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
				if(floor>=8) {//超过7周，则一次性领齐
					Map<String,Object> sendMap = new HashMap<String,Object>();
					sendMap.put("user_id", user_id);
					sendMap.put("receive_time", info.getReceiveTime());
					double ylqAmout = ttz_bill_ordersService.getYLQAmount(sendMap);
					BigDecimal YQLAmount = new BigDecimal(ylqAmout).setScale(BigDecimal.ROUND_HALF_UP, 2);
					if(YQLAmount.compareTo(info.getAmount())>=0) {//若金额大于需要解冻的总金额，则跳过
						continue;
					}else {//否则，全解冻
						BigDecimal addAmount = info.getAmount().subtract(YQLAmount);
						
						info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						info.setStatus((byte)0);
						info.setRate(0);
						info.setAmount(addAmount);
						
//						totalAmount = totalAmount.add(addAmount);
//						totalAmount2 = totalAmount2.add(addAmount);
//						totalAmount3 = totalAmount3.add(addAmount);
//						totalAmount4 = totalAmount4.add(addAmount);
						info.setAmount(addAmount);
						allAmount = allAmount.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount2 = allAmount2.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount3 = allAmount3.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allAmount4 = allAmount4.add(addAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						ttz_unfreezes.add(info);
					}
				}else {
					//floor = Math.floor(Double.valueOf(xx/60/60/24)/hb_day2);//相差几个星期，向下取整
					for(int i=0;i<floor;i++) {
						rateNum = rateNum +Integer.valueOf(rates[i]);
					}
					nowRate = rateNum - info.getRate();
					if(nowRate<0) {
						responseWriteInfo(200, "计算解冻比例异常", null, response);
						return;
					}else if(info.getRate() == rateNum) {//相等，说明本周已领
						continue;
					}
					info.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
					info.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
					info.setStatus((byte)0);
					info.setRate(nowRate);
					info.setAmount(Rand(info.getAmount(), nowRate));
					
					totalAmount = Rand(info.getAmount(), nowRate);
					totalAmount2 = Rand(info.getAmount(), nowRate);
					totalAmount3 = Rand(info.getAmount(), nowRate);
					totalAmount4 = Rand(info.getAmount(), nowRate);
					info.setAmount(totalAmount);
					allAmount = allAmount.add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount2 = allAmount2.add(totalAmount2).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount3 = allAmount3.add(totalAmount3).setScale(2, BigDecimal.ROUND_HALF_UP);
					allAmount4 = allAmount4.add(totalAmount4).setScale(2, BigDecimal.ROUND_HALF_UP);
					ttz_unfreezes.add(info);
				}
			
				
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
		
		
		
		
		
		//入余额
		HttpClientUtils httpClientUtil2 = new HttpClientUtils();
		Map<String,String> createMap = new HashMap<String, String>();
		char[] aa = BASE64.encode(intToBytes(user_id));
		//createMap.put("user_id", new String(aa,0,aa.length));
		createMap.put("user_id", BASE64.encodeData(String.valueOf(user_id)));
		createMap.put("type", String.valueOf(12));
		createMap.put("balance", allAmount.toString());
		
		if(!rate.equals("1")) {
			createMap.put("balance", allAmount.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		}
		createMap.put("rate", rate);
		
		
		String os_type = request.getParameter("os_type") == null ? "" : request.getParameter("os_type");
		String os_version = request.getParameter("os_version") == null ? "" : request.getParameter("os_version");
		String app_version = request.getParameter("app_version") == null ? "" : request.getParameter("app_version");
		String api_version = request.getParameter("api_version") == null ? "" : request.getParameter("api_version");
		String device_name = request.getParameter("device_name") == null ? "" : request.getParameter("device_name");
		String client_id = request.getParameter("client_id") == null ? "" : request.getParameter("client_id");
		String sign = request.getParameter("sign") == null ? "" : request.getParameter("sign");
		String t = request.getParameter("t") == null ? "" : request.getParameter("t");
		
		//String api_url = "http://api.jihes.com/ttz/balance?os_type="+os_type+"&os_version="+os_version+"&app_version="+app_version+"&api_version="+api_version+"&device_name="+device_name+"&client_id="+client_id+"&sign="+sign+"&t="+t;
		String api_url = "http://testapi.jihes.com/ttz/balance?os_type="+os_type+"&os_version="+os_version+"&app_version="+app_version+"&api_version="+api_version+"&device_name="+device_name+"&client_id="+client_id+"&sign="+sign+"&t="+t;
		
		api_url = api_url.replace(" ", "%20");
		logger.error("createMap:"+createMap);
		logger.error("api_url:"+api_url);
		
		String httpOrgCreateTestRtn2 = httpClientUtil2.doPost2(api_url,createMap,"utf-8"); 
		int code =200;
		String message = "";
		boolean insert = true;
		if(httpOrgCreateTestRtn2 == null || httpOrgCreateTestRtn2.equals("")) {
			code = 600;
			insert = false;
			message = "入余额接口返回结果为空";
		}else {
			JSONObject jb = new JSONObject();
			
			jb = (JSONObject) JSONObject.parse(httpOrgCreateTestRtn2);
			if(jb== null ) {//回滚数据
				insert =false;
				code = 600;
				message = "空指针异常";
			}else {
				code = (Integer) jb.get("code");
				message =  jb.get("message") == null ? "" : (String) jb.get("message");
				if(code == 600) {//回滚数据
 					insert =false;
				}
			}
		}
		
		
	
		
//		boolean insert =true;
//		String httpOrgCreateTestRtn2 = "";
//		int code =200;
//		String message= "";
		
		
		int count = 0;
		if(insert) {
			count = ttz_bill_ordersService.insertUnfreezes(ttz_unfreezes);
		}
		
		
		
		
		
		
		JSONObject data = new JSONObject();
		String[] total = new String[4];
		total[0] = allAmount.toString();
		total[1] = allAmount2.toString();
		total[2] = allAmount3.toString();
		total[3] = allAmount4.toString();
		if(!rate.equals("1")) {//按比例放大取整
			total[0]  =  allAmount.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[1]  =  allAmount2.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[2]  =  allAmount3.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			total[3]  =  allAmount4.multiply(rateB).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		data.put("totalAmount", total);
		data.put("count", count);
	//	data.put("reuslt", httpOrgCreateTestRtn2+",api_url:"+api_url);
		
		
		if(code == 200) {
			responseWriteInfo(200, "", data, response);
		}else if(code == 600) {
			responseWriteInfo(600, message, null, response);
		}else {
			responseWriteInfo(code, message, null, response);
		}
		
		
		
		
		
    	
    }
    
    
    
    public static byte[] intToBytes( int value )   
    {   
        byte[] src = new byte[4];  
        src[3] =  (byte) ((value>>24) & 0xFF);  
        src[2] =  (byte) ((value>>16) & 0xFF);  
        src[1] =  (byte) ((value>>8) & 0xFF);    
        src[0] =  (byte) (value & 0xFF);                  
        return src;   
    }  
    

	/**
	 * 计算随机金额
	 * @param amount
	 * @param rate
	 * @return
	 */
	public static BigDecimal Rand(BigDecimal amount,int rate) {
		if(rate >= 100) {
			return amount;
		}
		amount = amount.multiply(new BigDecimal("100"));
		int min = 0;
		int max = amount.intValue();
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal retAmount = new BigDecimal((df.format((float) s*rate/10000)));
		if(retAmount.compareTo(new BigDecimal("0.01")) ==-1) {
			retAmount = new BigDecimal("0.01");
		}
		return retAmount;
	}
	
	
	
	
	
	
	
	/**
	 * 造假数据
	 * @return
	 */
	public static JSONArray addList(String rate){
		JSONArray list = new JSONArray();
		JSONObject jb = new JSONObject();
		if(rate.equals("1")) 
			jb.put("sumAmount", 143.7D);
		else 
			jb.put("sumAmount", 14370);
		jb.put("nickName", "150****4305");
		jb.put("userId", -1);
		jb.put("number", 17);
		list.add(jb);
		jb = new JSONObject();
		
		if(rate.equals("1")) 
			jb.put("sumAmount", 113.4D);
		else 
			jb.put("sumAmount", 11340);
		jb.put("nickName", "小小强把");
		jb.put("userId", -1);
		jb.put("number", 16);
		list.add(jb);
		jb = new JSONObject();
		if(rate.equals("1")) 
			jb.put("sumAmount", 104.6D);
		else 
			jb.put("sumAmount", 10460);
		jb.put("nickName", "186****3289");
		jb.put("userId", -1);
		jb.put("number", 16);
		list.add(jb);
		jb = new JSONObject();
		if(rate.equals("1")) 
			jb.put("sumAmount", 103.1D);
		else 
			jb.put("sumAmount", 10310);
		jb.put("nickName", "tb_7133200");
		jb.put("userId", -1);
		jb.put("number", 15);
		list.add(jb);
		jb = new JSONObject();
		if(rate.equals("1")) 
			jb.put("sumAmount", 98.3D);
		else 
			jb.put("sumAmount", 9830);
		jb.put("nickName", "赖头浩");
		jb.put("userId", -1);
		jb.put("number", 14);
		list.add(jb);
		return list;
	}
	
	
	
	 /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	

	
	public static void main(String[] args) {
		//System.err.println(calculateProfit(111.11));
		//Rand2();
		
//		int number = new Random().nextInt(5);
//		for(int i= 0;i<100;i++) {
//			System.err.println(number = new Random().nextInt(5));
//		}
		char[] aa = new char[2];
		aa[0] = 'a';
		aa[1] = 'b';
		String str = new String(aa,0,aa.length);
		System.err.println(str);
	}
	
	 public static String getPath(){
		// 方式一
		System.out.println(System.getProperty("user.dir"));
		String info = "1:" + System.getProperty("user.dir");
		// 方式二
		File directory = new File("");// 设定为当前文件夹
		try {
			System.out.println(directory.getCanonicalPath());// 获取标准的路径
			info = info+",2:" + directory.getCanonicalPath();
			System.out.println(directory.getAbsolutePath());// 获取绝对路径
			info = info+",3:" + directory.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 方式三
		System.out.println(TtzBillOderController.class.getResource("/"));
		System.out.println(TtzBillOderController.class.getResource(""));
		info = info+",4:" + TtzBillOderController.class.getResource("/");
		info = info+",5:" + TtzBillOderController.class.getResource("");
		// 方式4
		System.out.println(TtzBillOderController.class.getClassLoader().getResource(""));
		System.out.println(TtzBillOderController.class.getClassLoader().getResource("source.xml"));
		info = info+",6:" + TtzBillOderController.class.getClassLoader().getResource("");
		info = info+",7:" + TtzBillOderController.class.getClassLoader().getResource("");
		return info;
	}

	 /**
	  * 清除红包接口
	  * @param request
	  * @param response
	  */
	 @RequestMapping("/clearReadPacket")
	 public  void clearReadPacket(HttpServletRequest request,HttpServletResponse response) {
		JSONObject data = new JSONObject();
	    String test_id = request.getParameter("test_id") == null ? "" : request.getParameter("test_id");
		int user_id;
		if(!test_id.equals("")) {
			user_id = Integer.valueOf(test_id);
		}else {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}
		Map<String,Object> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("update_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int count = ttz_bill_ordersService.clearReadPacket(map);
		data.put("count", count);
		data.put("update","success" );
		responseWriteInfo(200, "", data, response);
		 
	 }
}
