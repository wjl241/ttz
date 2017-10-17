package com.cn.ttz.timer;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.pojo.Ttz_goods;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_user_pid;
import com.cn.ttz.service.Ttz_OrderService;
import com.cn.ttz.service.Ttz_goodsService;
import com.cn.ttz.service.Ttz_user_pidService;
import com.sun.swing.internal.plaf.synth.resources.synth_es;

import util.CookieUtil;
import util.HttpClientUtils;
import util.MD5Util;
import util.XlsUtil;

public class OrderTask {
	@Resource
	private Ttz_user_pidService ttz_user_pidService;
	@Resource
	private Ttz_OrderService ttz_OrderService;
	@Resource
	private Ttz_goodsService ttz_goodsService;
	private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);
	   public void doBiz() {
		   System.err.println("11");
		   //task();
	   }
	   
	   public void task() {

			//"http://pub.alimama.com/report/getTbkPaymentDetails.json?
			//queryType={$ query_type}&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime={$start_date}&endTime={$end_date}",$cookie2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			Date tomorrow = new Date();
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			tomorrow = calendar.getTime();
			String start_date = sdf.format(now);
			String end_date = sdf.format(tomorrow);
			//TODO 存到配置表中，获取
			String cookie = "MDAwMDAwMDAwMH-Lts2OsrOcsn2vpJDMfmh_24Sxs3qean6gqZ2LnYDLgK7Iz4KMu5y8kLZ1";
			
			cookie = CookieUtil.think_decrypt(cookie);
			cookie = "cookie2="+cookie;
			String str = HttpClientUtils.httpGet2("http://pub.alimama.com/report/getTbkPaymentDetails.json?queryType=1&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime="+start_date+"&endTime="+end_date,
					cookie,"C:" + File.separator +"ttz"+File.separator+ "orders.xls");//"C:\\ttz\\orders.xls"
			List<String> ret = XlsUtil.readExcel("C:" + File.separator +"ttz"+File.separator+ "orders.xls");
			String[] values;
			String columnValue;
			List<Ttz_orders> orders = new ArrayList<Ttz_orders>();
			Ttz_orders order = new Ttz_orders();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long addtime;
			long checkout_time;
			String pid;
			List<String> pids = new ArrayList<String>();
			List<String> goods = new ArrayList<String>();
			
			for(String value : ret) {
				values = value.split(",");
				order = new Ttz_orders();
				for(int i=0;i<values.length;i++) {
					columnValue = values[i] ==null || values[i].equals("NULL")? "" :values[i];
					columnValue = columnValue.replaceAll("'", "");
					switch (i) {
					case 0://订单创建时间 A
						try {
							addtime = (sdf2.parse(columnValue).getTime())/1000;
							order.setAddTime((int) addtime);
						} catch (ParseException e) {
							logger.error("解析订单创建时间失败:"+columnValue,e);
						}
						break;
					case 2://商品信息 C
						order.setGoodsName(columnValue);
						break;
					case 3://商品ID D goods_id
						order.setGoodsId(columnValue);
						break;
					case 4://掌柜旺旺 E sopman_wangwang
						order.setShopmanWangwang(columnValue);
						break;
					case 5:// 'F' => '所属店铺', //store_name
						order.setStoreName(columnValue);
						break;
					case 6://  'G' => '商品数', //amount
						order.setAmount(Integer.valueOf(columnValue));
						break;
					case 7://'H' => '商品单价', //price
						order.setPrice(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 8:// 'I' => '订单状态', //order_status
						if("订单失效".equals(columnValue)) {
							order.setOrderStatus(new Byte("13"));
						}else if("订单付款".equals(columnValue)) {
							order.setOrderStatus(new Byte("12"));
						}else if("订单结算".equals(columnValue)) {
							order.setOrderStatus(new Byte("3"));
						}else if("分销结算".equals(columnValue)) {
							order.setOrderStatus(new Byte("200"));
						}
						break;
					case 9://'J' => '订单类型', //origin  转换为相应数字
						if("天猫".equals(columnValue)) {
							order.setOrigin(new Byte("2"));
						}else if("淘宝".equals(columnValue)) {
							order.setOrigin(new Byte("1"));
						}else if("聚划算".equals(columnValue)) {
							order.setOrigin(new Byte("3"));
						}
						break;
					case 10://  'K' => '收入比率', //income_ratio
					
						order.setIncomeRatio(Float.valueOf(columnValue.replaceAll("%", "").trim()));
						break;
					case 11://'L' => '分成比率', //divide_ratio
						order.setDivideRatio(Float.valueOf(columnValue.replaceAll("%", "").trim()));
						break;
					case 12://  'M' => '付款金额', //pay_amount
						order.setPayAmount(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 13://'N' => '效果预估', //effects_prediction
						order.setEffectsPrediction(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 14://  'O' => '结算金额', //checkout_amount 是否与付款金额绝对相同？待确认
						order.setCheckoutAmount(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 15://  'P' => '预估收入', //income_prediction
						order.setIncomePrediction(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 16:// 'Q' => '结算时间', //checkout_time
						try {
							if(columnValue ==null || columnValue.equals("") || columnValue.equals("NULL")) {
								checkout_time = 0;
							}else {
								checkout_time = (sdf2.parse(columnValue).getTime())/1000;
							}
							order.setCheckoutTime((int) checkout_time);
						} catch (ParseException e) {
							logger.error("解析结算时间失败:"+columnValue,e);
						}
						break;
					case 17://    'R' => '佣金比率', //commission_ratio
						order.setCommissionRatio(Float.valueOf(Float.valueOf(columnValue.replaceAll("%", "").trim())));
						break;
					case 18://   'S' => '佣金金额', //commission_amount
						order.setCommissionAmount(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 19://   'T' => '补贴比率',//subsidy_ratio
						order.setSubsidyRatio(Float.valueOf(Float.valueOf(columnValue.replaceAll("%", "").trim())));
						break;
					case 20://  'U' => '补贴金额',//subsidy_amount
						order.setSubsidyAmount(new BigDecimal(columnValue).setScale(2,BigDecimal.ROUND_HALF_UP));
						break;
					case 24:	//    'Y' => '订单编号', //order_sn,order_sn_hash
						order.setOrderSn(columnValue);
						order.setOrderHash(MD5Util.toMD5(columnValue));
						break;	
					case 25:	//     'Z' => '类目名称', //category
						order.setCategory(columnValue);
						break;	
					case 26:	//      'AA' => '来源媒体ID', //site_id
						order.setSiteId(Long.valueOf(columnValue));
						break;	
					case 28:	//      'AC' => '广告位ID', //adzone_id
						order.setAdzoneId(Long.valueOf(columnValue));
						break;	
					default:
	                     break;
					}
				}
				pid = "mm_"+"120117831_"+order.getSiteId()+"_"+order.getAdzoneId()+"";
				System.err.println(pid);
				System.err.println(order.getGoodsName());
				order.setPidId(0);
				order.setPid(pid);
				order.setCommissionPercent(Float.valueOf(0));
				orders.add(order);
				pids.add(pid);
				goods.add(order.getGoodsId());
				//order.setUserId(userId);
				
			}
			Map<String,Object> map  = new HashMap<String, Object>();
			map.put("pids", pids);
			map.put("endTime", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			List<Ttz_user_pid> user_pids = ttz_user_pidService.selectUseridByPid(map);
			List<Ttz_orders> orders2 = new ArrayList<Ttz_orders>();
			if(user_pids == null || user_pids.size()<=0) {
				return;
			}
			for(Ttz_orders ttz_order:orders) {
				for(Ttz_user_pid user_pid : user_pids ) {
					if(ttz_order.getPid().equals(user_pid.getPid())) {//插入订单表的pid对应的user_id
						ttz_order.setUserId(user_pid.getUserId());
						ttz_order.setPidId(user_pid.getId());
						ttz_order.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						ttz_order.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						ttz_order.setTtzGoodsId(0);
						orders2.add(ttz_order);
						break;
					}
				}
			}
			List<Ttz_orders> orders3 = new ArrayList<Ttz_orders>();
			map = new HashMap<String, Object>();
			map.put("good_ids", goods);
			List<Ttz_goods> ttz_goods =  ttz_goodsService.selectTtzGoodsId(map);
			for(Ttz_orders ttz_order:orders2) {
				if(ttz_order.getGoodsId().equals("38234529893")) {
					System.err.println("xx");
				}
				for(Ttz_goods good:ttz_goods) {
					if(ttz_order.getGoodsId().equals(good.getGoodId()) 
						&& ttz_order.getAddTime()>= good.getStartTime() 
						&& ttz_order.getAddTime()<= good.getEndTime()) {
						ttz_order.setTtzGoodsId(good.getId());
						orders3.add(ttz_order);
					}
				}
			}
			
			
			
			Map<String,Object> sendMap =new HashMap<String, Object>();
			sendMap.put("list", orders3);
			int success = ttz_OrderService.updateOrders(sendMap);
			System.err.println("更新次数"+success);
			
			
			
		
	   }
}
