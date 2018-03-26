package com.cn.ttz.timer;

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

import com.cn.ttz.pojo.Jihes_user;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.service.ConfigService;
import com.cn.ttz.service.Ttz_OrderService;

import util.MD5Util;

/**
 * NPC陪你玩定时器，每隔固定时间（如5分钟），查询一次订单，若有人参与活动，则开启订单扫码功能
 * @author Administrator
 *
 */
public class NPCTask {
	   private static final Logger logger = LoggerFactory.getLogger(NPCTask.class);
	   private static int count = 0;//计数器
	   @Resource
	   private Ttz_OrderService ttz_OrderService;
	   @Resource
	   private ConfigService ConfigService;
	   public void doBiz() {
		   task();
		   synchronized (this) {
			   count++;
		  }
	   }
	   
	   
	   
	   
	   public void task() {
		   
		    Map<String,Object> map = new HashMap<String,Object>();
		    
			String retry = ConfigService.selectConfig("ttz","npcTask","retry");//retry  NPC重复次数
			if(retry ==null || retry.equals("")) {
				retry = "5";
			}
			int retrys = Integer.valueOf(retry);
			if(retrys<=0) {//若重复次数小于等于0，则不需要NPC
				return;
			}
		    
		    
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date now = new Date();
			String start = sdf.format(now) + " 00:00:00";
			String end = sdf.format(now) + " 23:59:59";
			try {
				Date start_time = sdf2.parse(start);
				Date end_time = sdf2.parse(end);
				map.put("start_time", start_time.getTime()/1000);
				map.put("end_time", end_time.getTime()/1000+1);
			} catch (ParseException e) {
				logger.error("NPC定时器解析日期异常",e);
			}
			//查询今日活动商品的有效订单，若有，则开启NPC陪玩模式
		    List<Ttz_orders> orders = ttz_OrderService.getValuesOrders(map);
		    
		    if(orders== null || orders.size()<=0) {
		    	return;
		    }else {//陪玩模式开启
		    	
		    	List<Ttz_orders> NPCList = ttz_OrderService.getNPCOrderNumber(map);
		    	Map<Integer,Integer> NPCMap = new HashMap<Integer,Integer>();//ttz_goods_id , 数量 的集合
		    	Map<Integer,Integer> valueMap = new HashMap<Integer,Integer>();//ttz_goods_id , 数量 的集合
		    	if(NPCList == null || NPCList.size()<=0) {
		    		NPCMap = null;
		    	}else {
		    		for(Ttz_orders npc : NPCList) {
		    			NPCMap.put(npc.getTtzGoodsId(), npc.getAmount());
		    		}
		    	}
		    		
		    	
		    	
		    	List<Ttz_orders> NPCOrders = new ArrayList<Ttz_orders>();//NPC自动生成订单
		    	List<Ttz_orders> validOrders = new ArrayList<Ttz_orders>();//有效订单数，去重复
		    	List<Integer> ttz_goods_ids = new ArrayList<Integer>();
		    	for(Ttz_orders order :orders) {
		    		if(!ttz_goods_ids.contains(order.getTtzGoodsId())) {//若没有，则加入有效订单
		    			valueMap.put(order.getTtzGoodsId(), 1);//说明第一次
	    				ttz_goods_ids.add(order.getTtzGoodsId());
		    			validOrders.add(order);
		    			
		    		}else {//说明第N次
		    			valueMap.put(order.getTtzGoodsId(), valueMap.get(order.getTtzGoodsId())+1);//有效订单数量+1
		    		}
		    	}
		    	
		    	
		    
		    	int valueCount = 0;
		    	int NPCCount = 0;
		    	List<Ttz_orders> validOrders2 = new ArrayList<Ttz_orders>();//需要继续执行NPC陪你玩计划的订单集合
		    	if(NPCMap != null  ) {
		    		for(Ttz_orders order :validOrders) {
			    		valueCount = valueMap.get(order.getTtzGoodsId());
			    		//可能会存在新增的订单，所有npcmap对应值未null
			    		NPCCount = NPCMap.get(order.getTtzGoodsId()) == null? 0: NPCMap.get(order.getTtzGoodsId());
			    		if(valueCount*retrys >=NPCCount) {//若每个有效订单数还没执行到5次，就继续
			    			validOrders2.add(order);
			    		}
			    	}
		    	}else {
		    		validOrders2 = validOrders;
		    	}
		    	
		    	
		    	Jihes_user npc = ttz_OrderService.selectNPCInfo(count);
		    	if(npc ==null) {
		    		logger.error("未找到相关NPC信息，请联系管理员,count:"+count);
		    		count = 0;
		    		return;
		    	}
		    	Ttz_orders copyOrder = new Ttz_orders();
		    	for(Ttz_orders order :validOrders2) {
		    		copyOrder = new Ttz_orders();
		    		copyOrder.setUserId(npc.getId());
		    		copyOrder.setPidId(0);//不分配pid
		    		copyOrder.setGoodsId(order.getGoodsId());
		    		copyOrder.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		copyOrder.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		copyOrder.setAmount(1);
		    		copyOrder.setPrice(order.getPrice());
		    		copyOrder.setCommissionAmount(order.getCommissionAmount());
		    		copyOrder.setOrderStatus((byte)24);//301 NPC陪你玩订单
		    		copyOrder.setOrderSn(order.getOrderSn()+count+String.valueOf(System.currentTimeMillis()/1000));
		    		copyOrder.setOrderHash(MD5Util.toMD5(copyOrder.getOrderSn()));
		    		copyOrder.setAddTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		copyOrder.setOrigin(order.getOrigin());
		    		copyOrder.setIncomeRatio(order.getIncomeRatio());
		    		copyOrder.setDivideRatio(order.getDivideRatio());
		    		copyOrder.setPayAmount(order.getPayAmount());
		    		copyOrder.setEffectsPrediction(order.getEffectsPrediction());
		    		copyOrder.setCheckoutAmount(order.getCheckoutAmount());
		    		copyOrder.setIncomePrediction(order.getIncomePrediction());
		    		copyOrder.setCheckoutTime(order.getCheckoutTime());
		    		copyOrder.setCommissionRatio(order.getCommissionRatio());
		    		copyOrder.setCommissionAmount(order.getCommissionAmount());
		    		copyOrder.setCategory(order.getCategory());
		    		copyOrder.setSiteId(order.getSiteId());
		    		copyOrder.setAdzoneId(order.getAdzoneId());
		    		copyOrder.setGoodsName(order.getGoodsName());
		    		copyOrder.setShopmanWangwang(order.getShopmanWangwang());
		    		copyOrder.setStoreName(order.getStoreName());
		    		copyOrder.setSubsidyRatio(order.getSubsidyRatio());
		    		copyOrder.setSubsidyAmount(order.getSubsidyAmount());
		    		copyOrder.setPid("-1");
		    		copyOrder.setTtzGoodsId(order.getTtzGoodsId());
		    		copyOrder.setCommissionPercent(order.getCommissionPercent());
		    		NPCOrders.add(copyOrder);
		    	}
		    	if(NPCOrders ==null || NPCOrders.size()<=0) {
		    		
		    	}else {
		    		Map<String,Object> sendMap =new HashMap<String, Object>();
					sendMap.put("list", NPCOrders);
					//int success =  ttz_OrderService.updateOrders(sendMap);//批量更新ttz_orders表
					int success = ttz_OrderService.insertNPC(NPCOrders);
					System.err.println("更新次数orders"+success);
		    	}
		    	
		    }
		   
		   
	   }
}
