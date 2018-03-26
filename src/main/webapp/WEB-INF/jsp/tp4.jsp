<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	String path = request.getContextPath();
	path = "/jsp/ttz/";
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
		<script src='<%=path%>/js/jquery.js'></script>
		<script src="<%=path%>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script>
			function aa(){
			    var obj = {};
			    //获得所属分类的pkId 
			    var params = getUrlVars(); 
			    //获得分类的名称 
			    var productName = decodeURI(params[params[0]]); 
			    $('input:checked').each(function(i,input){
			        var name = input.getAttribute('name');
			        if(obj[name]){
			            obj[name] += ',' + this.id;
			        }else{
			            obj[name] = this.id;
			        }
			    })
			    $('input[type=text]').each(function(i,input){
			        var name = input.getAttribute('name');
			        obj[name] = this.value;
			    })
			    obj["sex"] = "1";
				console.log(obj);
				//var urlprefix = 'http://localhost:8080/ttz/tp/';
				var urlprefix = 'http://testapi.jihes.com/jsp/ttz/tp/';
				 $.ajax({
			        url:urlprefix+'dealTp2?name='+productName,
			        data:obj,
			        type:'post',
			        success:function(result){
			            alert("提交成功")
			        }
			        
			    })
			    
			}
			
			/** 初始化加载结束 */ 
			function getUrlVars(){ 
				var vars = [], hash; 
				var hashes = window.location.href.slice(window.location.href.indexOf('?')+1).split('&'); 
				for(var i = 0; i < hashes.length; i++) { 
				hash = hashes[i].split('='); 
				vars.push(hash[0]); 
				vars[hash[0]] = hash[1]; 
				} 
				return vars; 
			} 
		</script>
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
			<span>主办单位：中共晋城市委宣传部&nbsp;&nbsp;晋城市文明办&nbsp;&nbsp;晋城市人社局&nbsp;&nbsp;晋城市总工会&nbsp;&nbsp;共青团晋城市委&nbsp;&nbsp;晋城市妇联</span>
		</div>
		

		<section class="vote">
			<div class="pro">

				<form class="pict">
					<h3>图片投票</h3>
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/1.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic01" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/2.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic02" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/3.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic03" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/4.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic04" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/5.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic05" />
								</div>
							</div>
						</li>
					</ul>

					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/6.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic06" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/7.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic07" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/8.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic08" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/9.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic09" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/10.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic10" />
								</div>
							</div>
						</li>
					</ul>
					
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/11.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic11" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/12.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic12" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/13.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic13" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/14.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic14" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/15.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic15" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/16.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic16" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/17.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic17" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/18.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic18" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/19.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic19" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/20.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic20" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/21.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic21" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/22.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic22" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/23.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic23" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/24.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic24" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/25.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic25" />
								</div>
							</div>
						</li>
					</ul>
					
						<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/26.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic26" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/27.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic27" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/28.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic28" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/29.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic29" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/30.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic30" />
								</div>
							</div>
						</li>
					</ul>
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/31.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic31" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/32.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic32" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/33.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic33" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/34.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic34" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/35.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic35" />
								</div>
							</div>
						</li>
					</ul>

					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/36.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic36" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/37.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic37" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/38.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic38" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/39.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic39" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/40.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic40" />
								</div>
							</div>
						</li>
					</ul>
					
					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/41.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic41" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/42.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic42" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/43.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic43" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/44.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic44" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/45.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic45" />
								</div>
							</div>
						</li>
					</ul>

					<ul>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/46.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type=checkbox name="aaa" id="pic46" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/47.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic47" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/48.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic48" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/49.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic49" />
								</div>
							</div>
						</li>
						<li>
							<span class="pict-1"><img src="<%=path%>/img/female/50.jpg"/></span>
							<div class="poll">
								<div class="poll-left">
									<input type="checkbox" name="aaa" id="pic50" />
								</div>
							</div>
						</li>
					</ul>
					

				</form>
				<span class="hr1"></span>

			</div>
		</section>

		<!--姓名-->
		<section class="code">
			<div>
				<div class="butt" onclick="aa()">提&nbsp;交</div>
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
