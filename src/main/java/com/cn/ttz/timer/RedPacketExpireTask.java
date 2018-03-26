package com.cn.ttz.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.pojo.Jihes_sys_notification;
import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.service.Ttz_bill_ordersService;

/**
 * 红包失效定时器
 * @author Administrator
 *
 */
public class RedPacketExpireTask {
	private static final Logger logger = LoggerFactory.getLogger(PidTask.class);
	@Resource
	private Ttz_bill_ordersService ttz_bill_ordersService;
	public void doBiz() {
		task();
	}
	
	public void task() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, -3);//获取三天内的订单数据
		int createTime = (int) (calendar.getTime().getTime()/1000);
		List<Ttz_bill_orders> orders = ttz_bill_ordersService.selectExpireRedPacket(createTime);
		
		if(orders ==null || orders.size()<=0) {//若没有，则不执行
		    	
		}else {//有，批量插库
			List<Integer> orderIds = new ArrayList<Integer>();
			//List<Integer> userIds = new ArrayList<Integer>();
			Set<Integer> userIds = new HashSet<Integer>();
			for(Ttz_bill_orders order : orders) {
				orderIds.add(order.getId());
				userIds.add(order.getUserId());
			}
			
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("status", (byte)4);//红包失效
	    	map.put("update_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
	    	map.put("ids", orderIds);
	    	map.put("expire_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
	    	int count = ttz_bill_ordersService.updateRedPacket(map);
	    	logger.info("失效红包数量："+count);
	    	
	    	//失效提醒
	    	
	    	//发送站内通知
	    	 List<Jihes_sys_notification> notifications = new ArrayList<Jihes_sys_notification>();
	    	 Jihes_sys_notification notification = new Jihes_sys_notification();
	    	 Map<String,Object> sendsMap = new HashMap<String,Object>();
	    	 for(int user_id : userIds) {
			    	notification = new Jihes_sys_notification();
		    		notification.setUserId(user_id);
		    		notification.setTitle("报告小主，您参与的团团赚，因为某个单子退款，相关红包就失效了哦。去查看详情");
		    		notification.setContent("");
		    		notification.setType((byte)1);
		    		notification.setIcon("");
		    		notification.setStatus((byte)0);
		    		notification.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		notification.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		notifications.add(notification);
	    		   
	 	     }
	    	 if(notifications == null || notifications.size()==0) {
	    		 
	    	 }else {
	    		 count = ttz_bill_ordersService.insertNotifications(notifications);
	    		 logger.info("批量插入通知消息："+count+"条");
	    	 }
		}
	}
}
