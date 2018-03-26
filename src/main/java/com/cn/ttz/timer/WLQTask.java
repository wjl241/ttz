package com.cn.ttz.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.pojo.Jihes_sys_notification;
import com.cn.ttz.service.Ttz_bill_ordersService;

/**
 * 当天9点有未领取红包
 * @author Administrator
 *
 */
public class WLQTask {
	private static final Logger logger = LoggerFactory.getLogger(PidTask.class);
	@Resource
	private Ttz_bill_ordersService ttz_bill_ordersService;
	public void doBiz() {
		task();
	}
	public void task() {
		//获取所有未领取的
	    List<Integer> userIds =  ttz_bill_ordersService.selectWlqRedPacket(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
	    if(userIds ==null || userIds.size()<=0) {//若没有，则不执行
	    	
	    }else {//有，批量插库
	    	List<Jihes_sys_notification> notifications = new ArrayList<Jihes_sys_notification>();
	    	Jihes_sys_notification notification = new Jihes_sys_notification();
	    	for(int user_id : userIds) {
	    		notification = new Jihes_sys_notification();
	    		notification.setUserId(user_id);
	    		notification.setTitle("还有红包未领取~");
	    		notification.setContent("");
	    		notification.setType((byte)1);
	    		notification.setIcon("");
	    		notification.setStatus((byte)0);
	    		notification.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
	    		notification.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
	    		notifications.add(notification);
	    	}
	    	
	    	int count = ttz_bill_ordersService.insertNotifications(notifications);
	    	logger.info("批量插入通知消息："+count+"条");
	    }
	    
	    
	    
	    //获取即将失效的
	    userIds = new ArrayList<Integer>();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(Calendar.DAY_OF_YEAR, 1);
	    int expire_time = (int) (cal.getTime().getTime()/1000);//明天这个时候，若已经失效了，则提醒
	    userIds =  ttz_bill_ordersService.selectWlqRedPacket2(expire_time);
	    if(userIds ==null || userIds.size()<=0) {//若没有，则不执行
	    	
	    }else {//有，批量插库
	    	 List<Jihes_sys_notification> notifications = new ArrayList<Jihes_sys_notification>();
	    	 Jihes_sys_notification notification = new Jihes_sys_notification();
	    	 for(int user_id : userIds) {
	    		 notification = new Jihes_sys_notification();
		    		notification.setUserId(user_id);
		    		notification.setTitle("报告小主，我是小集合。您的红包已等待6天了，若再不领取，红包就要飞走啦~");
		    		notification.setContent("");
		    		notification.setType((byte)1);
		    		notification.setIcon("");
		    		notification.setStatus((byte)0);
		    		notification.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		notification.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		    		notifications.add(notification);
	 	    }
	    	int count = ttz_bill_ordersService.insertNotifications(notifications);
		    logger.info("批量插入通知消息："+count+"条");
		    //TODO 短信处理
		    
//		    TaobaoClient client ;
//		    AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//		    req.setExtend("123456");
//		    req.setSmsType("normal");
//		    req.setSmsFreeSignName("阿里大于");
//		    req.setSmsParamString("{\"code\":\"1234\",\"product\":\"alidayu\"}");
//		    req.setRecNum("13000000000");
//		    req.setSmsTemplateCode("SMS_585014");
//		    AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		  //  System.out.println(rsp.getBody());
	    }
	   
	    //解冻即将失效
	    
	    
	}
}
