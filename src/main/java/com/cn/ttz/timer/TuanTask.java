package com.cn.ttz.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.service.Ttz_goodsService;

/**
 * 监控团团表状态并修改
 * @author Administrator
 *
 */
public class TuanTask {
	@Resource
	private Ttz_goodsService	ttz_goodsService;
	private static final Logger logger = LoggerFactory.getLogger(TuanTask.class);
	   public void doBiz() {
		   task();
	   }
	   
	   
	   
	   
	   public void task() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String start_date = sdf.format(now);
			int time1=0,time2 = 0;
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, -5);//每隔5分钟处理一次，5分钟以外的状态修改
			try {
				time2 =  (int)(sdf2.parse(start_date + " 00:00:00").getTime()/1000);
				time1 = (int) (calendar.getTime().getTime()/1000);
				
			} catch (ParseException e) {
				logger.error("处理日期错误",e);
			}
			
			Map<String,Object> map = new HashedMap<String,Object>();
			map.put("time1", time1);
			map.put("time2", time2);
			map.put("updateTime", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			int success = ttz_goodsService.updateExpireTuanStatus(map);
			
			System.err.println("执行"+success+"条记录成功");
			
			
			
			
			
			
			
			
			
			
			
			
	   }
}
