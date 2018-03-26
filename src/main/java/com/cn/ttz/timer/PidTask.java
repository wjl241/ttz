package com.cn.ttz.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.service.Ttz_OrderService;
import com.cn.ttz.service.Ttz_bill_ordersService;
import com.cn.ttz.service.Ttz_pidService;

/**
 * 夜晚12点需要做的线程
 * @author Administrator
 *
 */
public class PidTask {
	  private static final Logger logger = LoggerFactory.getLogger(PidTask.class);
	 
	  @Resource
	  private Ttz_pidService ttz_pidService;
	  @Resource
	  private Ttz_bill_ordersService ttz_bill_ordersService;
	  public void doBiz() {
		   task();
	   }
	  public void task() {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //批量重置pid状态
		 int count =  ttz_pidService.updatePids(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		 logger.info("批量回复pid成功,修改"+count+"条，时间："+sdf.format(new Date()));
		 System.err.println("成功");
		  
		 
		 //红包过期处理
		
		 int count2 = ttz_bill_ordersService.dealExpireRedPacket(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		 logger.info("成功处理"+count2+"条过期红包，过期时间："+sdf.format(new Date()));
	  }
	  
	  
	  


}
