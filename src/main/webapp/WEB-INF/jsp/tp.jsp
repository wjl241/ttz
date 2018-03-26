<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	String path = request.getContextPath();
	//path = "/jsp/ttz/";
%>

<html>

	<head>
		<meta charset="utf-8">
		<title>投票</title>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<link rel="stylesheet " type="text/css " href="<%=path%>/css/globle.css " />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/tp.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css" />
		<script src='js/jquery.js'></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<style type="text/css">
		
	</style>

	<body>
		<header>
			<div>
				<span class="head-left"><a href="#"><img src="<%=path%>/img/logo.png"/></a></span>
			</div>
		</header>
		<div class="banner">
			<img src="<%=path%>/img/banner.png" />
		</div>

		<section class="vote">
			<div class="pro">

				<form class="pict">
					<h3>图片投票</h3>
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/1.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic01" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/2.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic02" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/3.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic03" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/4.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic04" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/5.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic05" />
								</div>
							</div>
						</li>
					</ul>

					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/6.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic06" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/7.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic07" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/8.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic08" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/9.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic09" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/10.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic10" />
								</div>
							</div>
						</li>
					</ul>
					
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/11.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic11" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/12.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic12" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/13.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic13" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/14.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic14" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/15.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic15" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/16.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic16" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/17.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic17" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/18.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic18" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/19.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic19" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/20.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic20" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/21.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic21" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/22.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic22" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/23.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic23" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/24.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic24" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/25.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic25" />
								</div>
							</div>
						</li>
					</ul>
					
						<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/26.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic26" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/27.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic27" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/28.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic28" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/29.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic29" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/30.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic30" />
								</div>
							</div>
						</li>
					</ul>
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/31.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic31" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/32.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic32" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/33.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic33" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/34.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic34" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/35.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic35" />
								</div>
							</div>
						</li>
					</ul>

					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/36.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic36" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/37.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic37" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/38.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic38" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/39.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic39" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/40.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic40" />
								</div>
							</div>
						</li>
					</ul>
					
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/41.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic41" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/42.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic42" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/43.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic43" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/44.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic44" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/45.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic45" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/46.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic46" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/47.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic47" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/48.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic48" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/pingzi/49.png"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic49" />
								</div>
							</div>
						</li>
					</ul>


					<label for="">姓名</label>
					<input type="text" class="form-control" style="width: 260px;display: inline-block;" />
					<div class="butt">提&nbsp;交</div>
				</form>
				
				<span class="hr1"></span>

			</div>
		</section>

		<!--姓名-->
		<section class="code">
			<div>
				<form>
					<label for="">姓名</label>
					<input type="text" class="form-control" style="width: 260px;display: inline-block;" />
				</form>
				<div class="butt">提&nbsp;交</div>
			</div>
		</section>
		<footer>
			<div>
				<span class="hr"></span>
				<span class="bot">至客科技有限公司版权所有</span>
			</div>
		</footer>

	</body>

</html>
