package com.cn.ttz.timer;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.ttz.pojo.Ttz_bill_orders;
import com.cn.ttz.pojo.Ttz_goods;
import com.cn.ttz.pojo.Ttz_orders;
import com.cn.ttz.pojo.Ttz_team;
import com.cn.ttz.pojo.Ttz_user_relation;
import com.cn.ttz.service.ConfigService;
import com.cn.ttz.service.Ttz_OrderService;
import com.cn.ttz.service.Ttz_bill_ordersService;

import util.RedEnvelopesDemo;

/**
 * 红包定时器
 * @author Administrator
 *
 */
public class billTask {
	 private static final Logger logger = LoggerFactory.getLogger(billTask.class);
	 
	 @Resource
	 private Ttz_OrderService ttz_OrderService;
	 @Resource
	 private Ttz_bill_ordersService ttz_bill_ordersService;
	 @Resource
	 private ConfigService ConfigService;
	 private static int randRate = 80;//随机比率
	 private static RedEnvelopesDemo dd = new RedEnvelopesDemo();  
	 public void doBiz() {
		 task();
	 }
	 
	 public void task() {
		 //Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now =new Date();
		String ymd =  sdf.format(now);
		Date start = new Date();
		Date end = new Date();
		try {
			start = sdf2.parse(ymd+" 00:00:00");
			end =sdf2.parse(ymd+" 23:59:59");
		} catch (ParseException e) {
			logger.error("日期解析异常",e);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start_time", start.getTime()/1000);
		map.put("end_time", end.getTime()/1000);
		//获取当日参团的活动与人数
		List<Ttz_orders> orders = ttz_OrderService.selectGoodsNum(map);
		if(orders == null || orders.size()<=0) {//若当日没有成交
			System.err.println("当日没有成交");
			return;
		}
		int num,ttzGoodsId;
		List<Integer> goodsIds = new ArrayList<Integer>();
		Map<Integer,Integer> nums = new HashMap<Integer, Integer>();//活动Id与人数的集合
		for(Ttz_orders order : orders) {
			num = order.getAmount();//所有参团人数
			ttzGoodsId = order.getTtzGoodsId();//活动ID
			goodsIds.add(ttzGoodsId);
			nums.put(ttzGoodsId, num);
		}
		
		if(goodsIds == null || goodsIds.size()<=0) {//没有ttz_goodsID
			logger.info("goodsIds为空");
			return;
		}
		map = new HashMap<String, Object>();
		map.put("ids", goodsIds);
		List<Ttz_goods> goods = ttz_OrderService.selectGoodPerson(map);//获取活动信息
		if(goodsIds == null || goodsIds.size()<=0) {//没有获取到对应活动详情
			logger.info("没有获取到对应活动详情");
			return;
		}
		int number;
		List<Integer> updateIds = new ArrayList<Integer>();
		
		
		
		String hb_create = ConfigService.selectConfig("ttz","hbdetail","hb_create");//4,4,2  新用户，邀请，老用户
		if(hb_create ==null || hb_create.equals("")) {
			hb_create = "0.4,0.4,0.2";
		}
		String[] hb_creates = hb_create.split(",");
		
//		String commsion_rate = ConfigService.selectConfig("ttz","hbdetail","commsion_rate");//发放佣金比例
//		if(commsion_rate ==null || commsion_rate.equals("")) {
//			commsion_rate = "0.85";
//		}
		//BigDecimal commsionRate = new BigDecimal(commsion_rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal commsionRate = new BigDecimal(0.85).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		String expire_day = ConfigService.selectConfig("ttz","hbdetail","expire_day");//失效天数
		if(expire_day ==null || expire_day.equals("")) {
			expire_day = "7";
		}
		Calendar calendar = Calendar.getInstance(); 
		String nyr = sdf.format(new Date());
		nyr = nyr + " 23:59:59";
		try {
			calendar.setTime(sdf2.parse(nyr));
			calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(expire_day));//7日后失效
		} catch (ParseException e) {
			logger.error("计算日期失败"+e.getMessage(),e);
		}
		
		int expire = (int) (calendar.getTime().getTime()/1000);//红包失效时间
		
		//健全模式
		double newRate = Double.valueOf(hb_creates[0]);//新人比例
		double inviteRate = Double.valueOf(hb_creates[1]);//邀请比例
		double oldRate = Double.valueOf(hb_creates[2]);//老人比例
		
		
		
		//没有邀请红包模式
		double extraRate = div(inviteRate, 2, 2);//额外的比例
		double newRate2 = new BigDecimal(newRate).add(new BigDecimal(extraRate)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//新人比例
		double oldRate2 = new BigDecimal(oldRate).add(new BigDecimal(extraRate)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//老人比例
				
		
		List<Ttz_orders> allOrders = new ArrayList<Ttz_orders>();//红包总人数（包括失效）
		List<Ttz_orders> validOrders = new ArrayList<Ttz_orders>();//有效领红包人数
		List<Ttz_bill_orders> redPackages = new ArrayList<Ttz_bill_orders>();//生成红包列
		Ttz_bill_orders redPackage = new Ttz_bill_orders();//红包列
		
		
		//为二人团新增逻辑  2017.11.9
		List<Ttz_goods> lunHuiGoods = new ArrayList<Ttz_goods>();//轮回商品
		List<Ttz_goods> twoTuanGoods = new ArrayList<Ttz_goods>();//二轮团商品
		//goods 分类 type 0,1 走正常红包轮回逻辑   type 2 走二人团红包逻辑
		for(Ttz_goods good : goods) {//goods分类
			if(good.getType() == 0 || good.getType() == 1) {
				lunHuiGoods.add(good);
			}else if(good.getType() == 2) {
				twoTuanGoods.add(good);
			}else {
				logger.info("存在good.type未知的情况:"+good.getId()+","+good.getType());
			}
		}
		
		//轮回红包分配
		for(Ttz_goods good : lunHuiGoods) {
			if(good.getCommissionRate().compareTo(new BigDecimal(0.00))==0) {
				good.setCommissionRate(commsionRate);
			}
			commsionRate = good.getCommissionRate().setScale(2, BigDecimal.ROUND_HALF_UP);//现在改为每个商品单独配佣金比例
			number = nums.get(good.getId()) == null?-1:nums.get(good.getId());//最新活动人数
			if(number < 0) {
				continue;
			}
			if(good.getNumber() == number) {//若原活动人数与最新活动人数相等,则不更新
				continue;
			}
			updateIds.add(good.getId());//需要修改人数的活动集合
			int newPerson = (number - good.getNumber() + (good.getNumber()%good.getMember()));//新增人数
			int oldPerson = good.getNumber() - (good.getNumber()%good.getMember());//已领取过红包人数
			int lunhui = newPerson/good.getMember();//几轮红包
			int member =good.getMember();//每轮人数
			BigDecimal commsioons = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//总佣金数
			if(newPerson/member>=1) {//若新增人数达到开团条件，则触发开团事件
				allOrders = new ArrayList<Ttz_orders>();
				validOrders = new ArrayList<Ttz_orders>();
				int allPerson;//本轮总人数
				//获取所有的团团成员，然后生成红包
				map = new HashMap<String, Object>();
				map.put("ttz_goods_id", good.getId());
				allOrders = ttz_OrderService.getOrdersByGoodsId(good.getId());
				if(allOrders == null || allOrders.size()<=0) {
					continue;
				}
				
//				for(Ttz_orders o : allOrders) {
//					if(o.getOrderStatus() == 13) {//若失效订单，返回
//						continue;
//					}
//					commsioons = commsioons.add(o.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//累加佣金数
//					validOrders.add(o);
//				}
//				commsioons = commsioons.multiply(commsionRate).setScale(2, BigDecimal.ROUND_HALF_UP);//扣除比例后是发放的金额
				List<Ttz_orders> mlvalidOldOrders = new ArrayList<Ttz_orders>();//每轮有效老用户
				List<Ttz_orders> mlvalidNewOrders = new ArrayList<Ttz_orders>();//每轮有效新用户
				List<Ttz_orders> mlvalidIvtOrders = new ArrayList<Ttz_orders>();//每轮有邀请用户
				List<Integer> user_ids = new ArrayList<Integer>();//所有有效用户id集合
				Map<Integer,List<Integer>> inviteMap = new HashMap<Integer, List<Integer>>();//邀请集合，userid,父类集合
				int allInviteRedPacketNum = 0;//所有邀请红包总数//所有邀请红包每轮重置（因为所有有新老用户都会判断一遍）
				int yffInviteRedPacketNum = 0;//已发放邀请红包数//发放红包一直不重置，这样刚好和上面匹配，瞎猫碰上死耗子
				for(int i =0;i<lunhui;i++) {//轮回红包开始
					user_ids = new ArrayList<>();//初始化用户//只有新人加入进去
					commsioons = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
					allInviteRedPacketNum =0;
					yffInviteRedPacketNum =0;
					inviteMap = new HashMap<Integer,List<Integer>>();
					if(oldPerson == 0 && i==0) {//若原人数为0 ，则自己先发一波
						//TODO
					}
				
					mlvalidOldOrders = new ArrayList<Ttz_orders>();
					mlvalidNewOrders = new ArrayList<Ttz_orders>();
					mlvalidIvtOrders = new ArrayList<Ttz_orders>();
					allOrders.get(oldPerson + i*member);
					Ttz_orders o2 =new Ttz_orders();
					for(int j =0;j<oldPerson + i*member;j++) {
						o2 =allOrders.get(j);
						if(o2.getOrderStatus() == 13) {//失效订单不记录
							continue;
						}
						//user_ids.add(o2.getUserId());
						mlvalidOldOrders.add(o2);
						
					}
					for(int j =oldPerson + i*member;j<oldPerson + (i+1)*member;j++) {
						o2 =allOrders.get(j);
						if(o2.getOrderStatus() == 13) {//失效订单不记录
							continue;
						}
						user_ids.add(o2.getUserId());
						mlvalidNewOrders.add(o2);
						commsioons = commsioons.add(o2.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//每轮累加佣金数
					}
					commsioons = commsioons.multiply(commsionRate).setScale(2, BigDecimal.ROUND_HALF_UP);//扣除比例后是发放的金额
					if(commsioons.compareTo(new BigDecimal(0.00))<=0) {
						continue;
					}
					map = new HashMap<String, Object>();
					map.put("start_time", start.getTime()/1000);
					map.put("end_time", end.getTime()/1000);
					boolean invite =false;//本轮是否存在邀请红包
					for(int uid : user_ids) {//查询关系
						int count = 0;//本uid有多少邀请红包给父类
						map.put("user_id", uid);
						List<Ttz_user_relation> relations = ttz_OrderService.selectRelations(map);
						if(relations ==null || relations.size()<=0) {
							continue;
						}
						List<Integer> parentIds = new ArrayList<Integer>();//每个子类对应的父类，理论上只有一个
						for(Ttz_user_relation relation : relations) {
							if(user_ids.contains(relation.getUserId())) {//有这轮红包的子类，邀请红包数+1
								count = count+1;
								invite = true;
								allInviteRedPacketNum = allInviteRedPacketNum +1;
								parentIds.add(relation.getParentId());//存入到父类集合中，一般只有一个
							}
						}
						if(count > 0 ) {
							inviteMap.put(uid, parentIds);//存入到邀请红包Map中
						}
						
					}
					
					//开始分发红包
					BigDecimal oldCommsioon = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal newCommsioon = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal inviteCommsioon = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
					if(!invite) {//若没有邀请红包
						oldCommsioon = commsioons.multiply(new BigDecimal(oldRate2)).setScale(2, BigDecimal.ROUND_HALF_UP);
						newCommsioon = commsioons.multiply(new BigDecimal(newRate2)).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else {//有邀请红包的情况下
						oldCommsioon = commsioons.multiply(new BigDecimal(oldRate)).setScale(2, BigDecimal.ROUND_HALF_UP);
						newCommsioon = commsioons.multiply(new BigDecimal(newRate)).setScale(2, BigDecimal.ROUND_HALF_UP);
						inviteCommsioon = commsioons.multiply(new BigDecimal(inviteRate)).setScale(2, BigDecimal.ROUND_HALF_UP);
					}
					if(mlvalidOldOrders.size()==0) {//若为第一波红包，则老人新人的佣金都是新人的,拉新红包比例不变
						newCommsioon = newCommsioon.add(oldCommsioon).setScale(2, BigDecimal.ROUND_HALF_UP);
					}
					//邀请红包的大小
					List<BigDecimal> inviteRands =  new ArrayList<BigDecimal>();
					if(invite) {
						//邀请红包的大小
						inviteRands = Rand2(inviteCommsioon, allInviteRedPacketNum);
					}
					
					int x=0;
					int count =0;
					List<Integer> parentIds = new ArrayList<Integer>();//每个子类对应的父类，理论上只有一个
					List<BigDecimal> oldRands = new ArrayList<BigDecimal>();
					if(mlvalidOldOrders.size()>0) {
						oldRands = Rand2(oldCommsioon, mlvalidOldOrders.size());
					}
					
					for(Ttz_orders o : mlvalidOldOrders) {//发放老红包
						//x=x+1;
						redPackage = new Ttz_bill_orders();
						redPackage.setUserId(o.getUserId());
						redPackage.setBillSn("0");
						redPackage.setOrderId(o.getId());
						redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setExpireTime(expire);
						redPackage.setStatus((byte)1);
						redPackage.setGoodId(o.getGoodsId());
						redPackage.setType((byte)2);//1首次，2追加红包，3邀请
						redPackage.setTtzGoodsId(o.getTtzGoodsId());
						redPackage.setReceive((byte)0);//未领取
						redPackage.setReceiveTime(ymd);//2017-10-20
//						if(mlvalidOldOrders.size() == x) {
//							if(oldCommsioon.compareTo(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP))<=0) {//0 或-的情况，强行塞0.01
//								oldCommsioon = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
//							}
//							redPackage.setCommission(oldCommsioon);
//						}else {
//							//计算佣金 
//							redPackage.setCommission(Rand(oldCommsioon, randRate));
//					        //单位是分  
//					        System.out.println(dd.splitRedPackets(10000, 50));  
//							oldCommsioon = oldCommsioon.subtract(redPackage.getCommission()).setScale(2, BigDecimal.ROUND_HALF_UP);
//						}
						redPackage.setCommission(oldRands.get(x));
						redPackages.add(redPackage);
						x=x+1;
						//parentIds = inviteMap.get(o.getUserId());
						parentIds = new ArrayList<Integer>();//老员工不发邀请红包给自己的父类
						if(parentIds !=null && parentIds.size()>0) {//若有邀请红包
							for(int k=0;k<parentIds.size();k++) {
								redPackage = new Ttz_bill_orders();
								redPackage.setUserId(parentIds.get(k));//邀请红包是发给父类的
								redPackage.setBillSn("0");
								redPackage.setOrderId(o.getId());
								redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
								redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
								redPackage.setExpireTime(expire);
								redPackage.setStatus((byte)1);
								redPackage.setGoodId(o.getGoodsId());
								redPackage.setType((byte)3);//1首次，2追加红包，3邀请
								redPackage.setTtzGoodsId(o.getTtzGoodsId());
								redPackage.setReceive((byte)0);//未领取
								redPackage.setReceiveTime(ymd);//2017-10-20
//								if(yffInviteRedPacketNum == allInviteRedPacketNum) {//发完了
//									if(inviteCommsioon.compareTo(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP))<=0) {//0 或-的情况，强行塞0.01
//										inviteCommsioon = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
//									}
//									redPackage.setCommission(inviteCommsioon);
//								}else {
//									redPackage.setCommission(Rand(inviteCommsioon, randRate));
//									inviteCommsioon = inviteCommsioon.subtract(redPackage.getCommission()).setScale(2, BigDecimal.ROUND_HALF_UP);
//								}
								redPackage.setCommission(inviteRands.get(yffInviteRedPacketNum));
								redPackages.add(redPackage);
								yffInviteRedPacketNum = yffInviteRedPacketNum +1;
								
							}
						}
					}
					x =0;
					List<BigDecimal> newRands = new ArrayList<BigDecimal>();
					newRands = Rand2(newCommsioon, mlvalidNewOrders.size());
					for(Ttz_orders o : mlvalidNewOrders) {//发放新红包
						//x=x+1;
						redPackage = new Ttz_bill_orders();
						redPackage.setUserId(o.getUserId());
						redPackage.setBillSn("0");
						redPackage.setOrderId(o.getId());
						redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setExpireTime(expire);
						redPackage.setStatus((byte)1);
						redPackage.setGoodId(o.getGoodsId());
						redPackage.setType((byte)1);//1首次，2追加红包，3邀请
						redPackage.setTtzGoodsId(o.getTtzGoodsId());
						redPackage.setReceive((byte)0);//未领取
						redPackage.setReceiveTime(ymd);//2017-10-20
//						if(mlvalidNewOrders.size() == x) {
//							if(newCommsioon.compareTo(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP))<=0) {//0 或-的情况，强行塞0.01
//								newCommsioon = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
//							}
//							redPackage.setCommission(newCommsioon);
//						}else {
//							//计算佣金 
//							redPackage.setCommission(Rand(newCommsioon, randRate));
//							newCommsioon = newCommsioon.subtract(redPackage.getCommission()).setScale(2, BigDecimal.ROUND_HALF_UP);
//						}
						
						redPackage.setCommission(newRands.get(x));
						redPackages.add(redPackage);
						x=x+1;
						
						
						parentIds = inviteMap.get(o.getUserId());//新人才发邀请红包给父类
						if(parentIds !=null && parentIds.size()>0) {//若有邀请红包
							for(int k=0;k<parentIds.size();k++) {
//								yffInviteRedPacketNum = yffInviteRedPacketNum +1;
								redPackage = new Ttz_bill_orders();
								redPackage.setUserId(parentIds.get(k));
								redPackage.setBillSn("0");
								redPackage.setOrderId(o.getId());
								redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
								redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
								redPackage.setExpireTime(expire);
								redPackage.setStatus((byte)1);
								redPackage.setGoodId(o.getGoodsId());
								redPackage.setType((byte)3);//1首次，2追加红包，3邀请
								redPackage.setTtzGoodsId(o.getTtzGoodsId());
								redPackage.setReceive((byte)0);//未领取
								redPackage.setReceiveTime(ymd);//2017-10-20
//								if(yffInviteRedPacketNum == allInviteRedPacketNum) {//发完了
//									if(inviteCommsioon.compareTo(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP))<=0) {//0 或-的情况，强行塞0.01
//										inviteCommsioon = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
//									}
//									redPackage.setCommission(inviteCommsioon);
//									
//								}else {
//									redPackage.setCommission(Rand(inviteCommsioon, randRate));
//									inviteCommsioon = inviteCommsioon.subtract(redPackage.getCommission()).setScale(2, BigDecimal.ROUND_HALF_UP);
//								}
								
								redPackage.setCommission(inviteRands.get(yffInviteRedPacketNum));
								redPackages.add(redPackage);
								
								yffInviteRedPacketNum = yffInviteRedPacketNum +1;
							}
						}
					}
					
					//本轮红包都已生成，进入下轮红包环节
				}
				//本活动所有轮回红包已结束，进入下一活动
			}
			//更新活动人数
			good.setNumber(number);
			good.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_OrderService.updateByPrimaryKeySelective(good);
		}
		
		List<Ttz_orders> payOrders = new ArrayList<Ttz_orders>();//成功付款的订单
		List<Ttz_orders> badOrders = new ArrayList<Ttz_orders>();//成功付款的订单
		for(Ttz_orders o : orders) {
			if(o.getOrderStatus() == 3 || o.getOrderStatus() == 12) {
				payOrders.add(o);
			}
			if(o.getOrderStatus() == 13) {
				badOrders.add(o);
			}
		}
		//二人团红包分配
		for(Ttz_goods good : twoTuanGoods) {
			if(good.getCommissionRate().compareTo(new BigDecimal(0.00))==0) {
				good.setCommissionRate(commsionRate);
			}
			commsionRate = good.getCommissionRate().setScale(2, BigDecimal.ROUND_HALF_UP);//现在改为每个商品单独配佣金比例
			number = nums.get(good.getId()) == null?-1:nums.get(good.getId());//最新活动人数
			if(number < 0) {
				continue;
			}
			if(good.getNumber() == number) {//若原活动人数与最新活动人数相等,则不更新
				continue;
			}
			Map<String,Object> teamMap = new HashMap<String,Object>();
			teamMap.put("ttz_goods_id", good.getId());
			teamMap.put("start_time", start.getTime()/1000);
			teamMap.put("end_time", end.getTime()/1000);
			teamMap.put("status", 1);
			//获取组队成功的team (两人都已下单且付款)
			List<Ttz_team> teams = ttz_bill_ordersService.getSuccessTeam(teamMap);
			if(teams ==null || teams.size()<=0) {
				continue;
			}
			BigDecimal commsioons = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//总佣金数
			for(Ttz_team team : teams) {
				List<Ttz_orders> mlvalidOrders = new ArrayList<Ttz_orders>();//每轮有用户
				commsioons = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
				for(Ttz_orders o : payOrders) {//循环当日所有成功付款的订单
					if(team.getCaptain() == o.getId()) {//队长订单
						commsioons = commsioons.add(o.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//每轮累加佣金数
						mlvalidOrders.add(o);
						continue;
					}
					if(team.getMemberOrderId().contains(",")) {//多个团下成员
						String[] memberOrderIds = team.getMemberOrderId().split(",");
						for(String mId : memberOrderIds) {
							if(mId.equals(o.getId().toString())) {
								commsioons = commsioons.add(o.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//每轮累加佣金数
								mlvalidOrders.add(o);
								break;
							}
						}
					}else {//二人团
						if(team.getMemberOrderId().equals(o.getId().toString())) {
							commsioons = commsioons.add(o.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//每轮累加佣金数
							mlvalidOrders.add(o);
						}
					}
				}
				commsioons = commsioons.multiply(commsionRate).setScale(2, BigDecimal.ROUND_HALF_UP);//扣除比例后是发放的总金额
				if(commsioons.compareTo(new BigDecimal(0.00))<=0) {//发放总佣金数目不对，则返回
					continue;
				}
				if(mlvalidOrders == null || mlvalidOrders.size() <=0){//有效订单不对，则返回
					continue;
				}
				//开始发放红包
				int x =0;
				List<BigDecimal> newRands = new ArrayList<BigDecimal>();
				newRands = Rand2(commsioons, mlvalidOrders.size());
				for(Ttz_orders o2 : mlvalidOrders) {
						//x=x+1;
						redPackage = new Ttz_bill_orders();
						redPackage.setUserId(o2.getUserId());
						redPackage.setBillSn("0");
						redPackage.setOrderId(o2.getId());
						redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setExpireTime(expire);
						redPackage.setStatus((byte)4);
						redPackage.setGoodId(o2.getGoodsId());
						redPackage.setType((byte)1);//1首次，2追加红包，3邀请
						redPackage.setTtzGoodsId(o2.getTtzGoodsId());
						redPackage.setReceive((byte)0);//未领取
						redPackage.setReceiveTime(ymd);//2017-10-20
						redPackage.setCommission(newRands.get(x));
						redPackages.add(redPackage);
						x=x+1;
				}
				
			}
			
			//更新活动人数
			good.setNumber(number);
			good.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			ttz_OrderService.updateByPrimaryKeySelective(good);
			
		}
		
		//后续考虑增加 二人团， 若有人退团的情况下，是否需要重置红包   失效原发红包，新增正常红包  已处理
		for(Ttz_goods good : twoTuanGoods) {
			if(good.getCommissionRate().compareTo(new BigDecimal(0.00))==0) {
				good.setCommissionRate(commsionRate);
			}
			commsionRate = good.getCommissionRate().setScale(2, BigDecimal.ROUND_HALF_UP);//现在改为每个商品单独配佣金比例
			number = nums.get(good.getId()) == null?-1:nums.get(good.getId());//最新活动人数
			Map<String,Object> teamMap = new HashMap<String,Object>();
			teamMap.put("ttz_goods_id", good.getId());
			teamMap.put("start_time", start.getTime()/1000);
			teamMap.put("end_time", end.getTime()/1000);
			teamMap.put("status", 3);
			//获取组队失败的team (两人都已下单，获取红包成功，后有人退款)
			List<Ttz_team> teams = ttz_bill_ordersService.getSuccessTeam(teamMap);
			if(teams ==null || teams.size()<=0) {
				continue;
			}
			BigDecimal commsioons = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//总佣金数
			for(Ttz_team team : teams) {
				List<Ttz_orders> mlbadOrders = new ArrayList<Ttz_orders>();//每轮有用户
				List<Ttz_orders> mlvalidOrders = new ArrayList<Ttz_orders>();//每轮有用户
				List<Integer> mlvalidId= new ArrayList<Integer>();//每轮所有用户
				List<Integer> mlvalidId2= new ArrayList<Integer>();//每轮实际有效用户Id
				commsioons = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				mlvalidId.add(team.getCaptain());
				if(team.getMemberOrderId().contains(",")) {//多个团下成员
					String[] memberOrderIds = team.getMemberOrderId().split(",");
					for(String mId : memberOrderIds) {
						mlvalidId.add(Integer.valueOf(mId));
					}
				}else {
					mlvalidId.add(Integer.valueOf(team.getMemberOrderId()));
				}
				
				for(Integer id : mlvalidId) {
					boolean add =true;
					for(Ttz_orders o : badOrders) {//循环当日所有失效的订单
						if(id == o.getId()) {
							mlbadOrders.add(o);
							add = false;
						}
					}
					if(add) {//若没有失效订单，则记录最终有效终
						mlvalidId2.add(id);
					}
				}
				
				if(badOrders ==null || mlvalidOrders.size() <=0) {//若不存在当前组队失效的订单，则返回
					return;
				}
				
				//失效原组队红包
				map = new HashMap<String,Object>();
				map.put("update_time",Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				map.put("order_ids", mlvalidId);
				int success = ttz_bill_ordersService.dealExpireRedPackets(map);
				System.err.println("批量失效 红包数量2:"+success);
				
				
				for(Ttz_orders o : orders) {//今日所有有效订单
					for(Integer id : mlvalidId2) {
						if(o.getId() == id) {
							mlvalidOrders.add(o);
							commsioons = commsioons.add(o.getEffectsPrediction()).setScale(2, BigDecimal.ROUND_HALF_UP);//每轮累加佣金数
							continue;
						}
					}
				}
				commsioons = commsioons.multiply(commsionRate).setScale(2, BigDecimal.ROUND_HALF_UP);//扣除比例后是发放的总金额
				if(commsioons.compareTo(new BigDecimal(0.00))<=0) {//发放总佣金数目不对，则返回
					continue;
				}
				if(mlvalidOrders == null || mlvalidOrders.size() <=0){//有效订单不对，则返回
					continue;
				}
				//开始发放红包
				int x =0;
				List<BigDecimal> newRands = new ArrayList<BigDecimal>();
				newRands = Rand2(commsioons, mlvalidOrders.size());
				for(Ttz_orders o2 : mlvalidOrders) {//重新生成新增红包
						//x=x+1;
						redPackage = new Ttz_bill_orders();
						redPackage.setUserId(o2.getUserId());
						redPackage.setBillSn("0");
						redPackage.setOrderId(o2.getId());
						redPackage.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
						redPackage.setExpireTime(expire);
						redPackage.setStatus((byte)4);
						redPackage.setGoodId(o2.getGoodsId());
						redPackage.setType((byte)1);//1首次，2追加红包，3邀请
						redPackage.setTtzGoodsId(o2.getTtzGoodsId());
						redPackage.setReceive((byte)0);//未领取
						redPackage.setReceiveTime(ymd);//2017-10-20
						redPackage.setCommission(newRands.get(x));
						redPackages.add(redPackage);
						x=x+1;
				}
				team.setStatus((byte)4);
				team.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				//修改team为红包重置状态
				int count = ttz_bill_ordersService.updateTeamByPrimaryKey(team);
				System.err.println("成功修改组队未红包重置状态:"+count);
			}
		}
		
		
		
		if(redPackages.size()>0) {
			//所有活动已计算完毕，生成红包
			int count = ttz_bill_ordersService.insertRedPackages(redPackages);
			System.err.println("生成红包条数"+count);
		}
		
		
		
		
		
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public static void main(String[] args) {
		int a =9;
		int b = a%3;
		System.err.println(1/2);
		System.err.println(9/2);
	}
	 
	 
	 /**  
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	 * 定精度，以后的数字四舍五入。  
	 * @param v1 被除数  
	 * @param v2 除数  
	 * @param scale 表示表示需要精确到小数点以后几位。  
	 * @return 两个参数的商  
	 */  
	 public static double div(double v1,double v2,int scale){   
	 if(scale<0){   
	 throw new IllegalArgumentException(   
	 "The scale must be a positive integer or zero");   
	 }   
	 BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	 BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	 return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	 }   
	 /**  
	 * 提供精确的小数位四舍五入处理。  
	 * @param v 需要四舍五入的数字  
	 * @param scale 小数点后保留几位  
	 * @return 四舍五入后的结果  
	 */  
	 public static double round(double v,int scale){   
	 if(scale<0){   
	 throw new IllegalArgumentException("The scale must be a positive integer or zero");   
	 }   
	 BigDecimal b = new BigDecimal(Double.toString(v));   
	 BigDecimal one = new BigDecimal("1");   
	 return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
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
			if(max<=0) {//若已经领完，则补贴1分
				max = 1;
			}
			Random random = new Random();
			int s = 0;
			try {
				s = random.nextInt(max)%(max-min+1) + min;
			} catch (Exception e) {
				System.err.println("max:"+max);
			}
			System.err.println(s);
			DecimalFormat df = new DecimalFormat("0.00");
			BigDecimal retAmount = new BigDecimal((df.format((float) s*rate/10000)));
			if(retAmount.compareTo(new BigDecimal("0.01")) ==-1) {
				retAmount = new BigDecimal("0.01");
			}
			return retAmount;
		}
		
		
		/**
		 * 计算随机金额,模仿微信版逻辑
		 * @param amount
		 * @param person  人数
		 * @param count  第几次
		 * @return
		 */
		public static List<BigDecimal> Rand2(BigDecimal amount,int person) {
			
			List<Integer> list = dd.splitRedPackets(amount.multiply(new BigDecimal(100)).intValue(), person);
			List<BigDecimal> list2 = new ArrayList<BigDecimal>();
			DecimalFormat df = new DecimalFormat("0.00");
			if(list == null) {
				list2 = new ArrayList<BigDecimal>();
				for(int i=0;i<person;i++) {
					list2.add(new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}else {
				for(Integer s : list) {
					BigDecimal retAmount = new BigDecimal((df.format((float) s*100/10000)));
					if(retAmount.compareTo(new BigDecimal("0.01")) ==-1) {
						retAmount = new BigDecimal("0.01");
					}
					list2.add(retAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				}
			}
			
			return list2;
		}
	 
}
