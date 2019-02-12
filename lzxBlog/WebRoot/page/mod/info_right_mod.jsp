<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div>

	<img id="user_img" src="" />
	<div class="user_info_name" id="user_info_name"></div>
	<div id="user_info_interaction"></div>
	<div id="user_info_time">
		<ul></ul>
	</div>
	<div id="more_user_info"><a href="">查看更多信息</a></div>
</div>
<script type="text/javascript" >

$(document).ready(function(){

	var Request = getRequest();
	var user = Number(Request["u"]);
	var ts = "ok";
	if(user == null || user<=0 || isNaN(user) || typeof(user) == "undefined"){
		user=1;
		ts = "no";
	}
	var artID = Request["art"];
	if(artID !=null && artID !="" && typeof(artID) != "undefined"){
		ts = "ok";
	}
	//
	if(ts=="ok") {
		$.ajax({
			type : 'post',	//要求为String类型的参数，请求方式（post或get）默认为get。注意其他http请求方法，例如put和delete也可以使用，但仅部分浏览器支持。
			url : 'userInfoDoLoadOfWrite.action', //此处的Action要与struts.xml中的action的name对应	要求为String类型的参数，（默认为当前页地址）发送请求的地址。
			dataType : 'json',
			/**
				要求为String类型的参数，预期服务器返回的数据类型。如果不指定，JQuery将自动根据http包mime信息返回responseXML或responseText，并作为回调函数参数传递。可用的类型如下：
				xml：返回XML文档，可用JQuery处理。
				html：返回纯文本HTML信息；包含的script标签会在插入DOM时执行。
				script：返回纯文本JavaScript代码。不会自动缓存结果。除非设置了cache参数。注意在远程请求时（不在同一个域下），所有post请求都将转为get请求。
				json：返回JSON数据。
				jsonp：JSONP格式。使用SONP形式调用函数时，例如myurl?callback=?，JQuery将自动替换后一个“?”为正确的函数名，以执行回调函数。
				text：返回纯文本字符串。
			**/
			async: false,		//要求为Boolean类型的参数，默认设置为true，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为false。注意，同步请求将锁住浏览器，用户其他操作必须等待请求完成才可以执行。
			cache : false,		//要求为Boolean类型的参数，默认为true（当dataType为script时，默认为false），设置为false将不会从浏览器缓存中加载请求信息。
			data : {
				'writeID' : user,
				'address' : artID
			},
			success : function(r) {
					if (r.result == "ok") {
						$('#user_info_name').append("<span>"+ r.maps.userInfo.userName +"</span>");
						$('#user_img').attr("src",r.maps.userInfo.userLogo.toString());	
						
						if(r.maps.isMe == 0){
						var interaction= '<div id="follow" onclick="doFollow(1);" >关注Ta</div>'+
										'<div id="unfollow" onclick="doFollow(0);" style="display: none;" >取消关注</div>'+
										'<div>私信Ta</div>'+
										'<a href="/lzxBlog/userArticles.html?u='+r.maps.userInfo.userId.toString()+'"><div>Ta的文章</div></a>';
								$('#user_info_interaction').append(interaction);
								$("#more_user_info").css("margin-top","120px");
								changeFollow(r.maps.state);
						};
						if(r.maps.userInfo.loginLastTime != null){
							var time1 = '<li>最后登录：'+r.maps.userInfo.loginLastTime.toString().replace(/T/g, ' ') +'</li>';
							$('#user_info_time ul').append(time1);
						}
						if(r.maps.userInfo.updateLastTime != null){
							var time2 = '<li>最后发表：'+r.maps.userInfo.updateLastTime.toString().replace(/T/g, ' ') +'</li>';
							$('#user_info_time ul').append(time2);
						}
						if(r.maps.userInfo.registerTime != null){
							var time3 = '<li>注册时间：'+r.maps.userInfo.registerTime.toString().replace(/T/g, ' ') +'</li>';
							$('#user_info_time ul').append(time3);
						}
						$('#more_user_info a').attr("href",'/lzxBlog/profile.html?u=' + r.maps.userInfo.userId.toString() + '');
					}
				},
			error : function(err) {			
				window.location.replace("/lzxBlog/index.html");
				alert("加载文章时出错");
			}
		});
	}
});

//改变关注状态
function doFollow(state){
	var Request = getRequest();
		var ts = "ok";
	var user = Number(Request["u"]);	
	if(user == null || user<=0 || isNaN(user) || typeof(user) == "undefined"){
		user=1;
		ts = "no";
	}
	if(ts=="ok"){
		$.ajax({
			type : 'post',
			url : 'relation_ChangeFollow.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'writeID' : user,
				'state' : state
			},
		success : function(r) {
				if (r.result == "ok") {
					changeFollow(state);
				}else if(r.result == "not"){
					alert("请先登录");
				}
			},
		error : function(err) {
				window.location.reload();
				alert("发生错误");
			}
		});
	}
}
//改关注按钮样式
function changeFollow(state){
	if(state==0){	//显示未关注状态的按钮
		$("#follow").css("display","block");
		$("#unfollow").css("display","none");
	}else if(state==1){	//显示已关注状态的按钮
		$("#follow").css("display","none");
		$("#unfollow").css("display","block");
	}
}


/*****************
*
*一些工具的方法
*
******************/	

function getRequest() { //用于获取表单提交的数据
	var url = window.location.search; //获取url中"?"符后的字串     
	var theRequest = new Object();
	if(url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i++) {
			//就是这句的问题  
			theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
			//之前用了unescape()  
			//才会出现乱码    
		}
	}
	return theRequest;
}

</script>


<style type="text/css">
	
#info_right {
	float: right; 
	border: 2px solid #959595;
	background: white;
	width: 240px;
	margin: 10px 0px 0px 0px;
}
#info_right:hover {
	border: 2px solid #FE9A2E;
}
#user_img {
	width: 80%;
	border: 1px solid #BDBDBD;
	 /*border-radius: 50%; 圆角*/
	margin-top: 30px;
}

.user_info_name{
	margin-top: 15px;
	width: 100%;
}
#user_info_interaction div{
	width: 38%;
	height: 30px;
	line-height: 30px;
	border: 1px solid #000000;
	background-color: white;
	margin-top: 20px;
	text-align: center;
	margin-left: 8%;
	float: left;
}
#user_info_interaction div:hover{
	background: #FE9A2E;
	color: #FFFFFF;
}
#user_info_time{
	font-size: 14px;
	width: 100%;
	float: left;
	margin: 30px 0px;
	
}
#more_user_info{
	width: 100%;
	margin: 30px 0px 20px 0px;
}
#more_user_info:hover a{
	color: #FE9A2E;;
}
</style> 

