$(document).ready(function(){		
	var Request = getRequest();

	var user = Number(Request["u"]);
	var ts = "ok";
	if(user == null || user<=0 || isNaN(user) || typeof(user) == "undefined"){
		user=-1;
	}
	//
	if(ts=="ok") {
	
		$.ajax({
			type : 'post',	
			url : 'userInfoDoLoadOfUser.action', 
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'writeID' : user
			},
			success : function(r) {
					if (r.result == "ok") {
						if(r.maps.isMe == 0){
							
							$("#profile_table").load('/lzxBlog/page/mod/profile_table2.html', function(){
								loadInfo1(r);
								$('#userBirthday').val(r.maps.userInfo.birthday);
								$('#userSex').val(getSex(r.maps.userInfo.sex));
								$('#userFrom').val((r.maps.userInfo.addressNow).split('&*&')[0]);	
							}).html();
						}else if(r.maps.isMe == 1){
							$("#profile_table").load('/lzxBlog/page/mod/profile_table1.html', function(){
									serverInfo.rs = r.maps.userInfo;	//修改profile.html 定义的变量
									loadInfo1(r);
									$('#userSex .sex').val(r.maps.userInfo.sex);
									var birth= r.maps.userInfo.birthday;
									if(birth != null && birth != ""){
										var birthday = birth.split("-");
									for(var i=0;i<birthday.length;i++){
											$('#userBirthday select:eq('+i+')').attr("rel",Number(birthday[i]));
										}
									}
									loadMod();
									var from= r.maps.userInfo.addressNow;
									if(from != null && from !=""){
										from = from.split('&*&')[1];userFrom;
										var ufrom = from.split("-");
										for(var i=0;i<ufrom.length;i++){
											$('#userFrom input').eq(i).val(ufrom[i]);
										}
									}
									
							}); 

						}

					}else if(r.result == "no"){
						alert("加载个人信息时出错 - no");
					}
				},
			error : function(err) {			
				window.location.replace("/lzxBlog/index.html");
				alert("加载个人信息时出错 - erro");
			}
		});
	}
});
//资料1
function loadInfo1(r){
	$("#nickName").val(r.maps.userInfo.userName);	//昵称
	$('#finalImg').attr("src",r.maps.userInfo.userLogo);	//头像
	$('#userAddress').val(r.maps.userInfo.addressHome);	//地址
	$('#userEducational').val(r.maps.userInfo.educational);	//学历
	$('#userHobby').val(r.maps.userInfo.hobby);	//兴趣爱好
	$('#userJob').val(r.maps.userInfo.job);	//职业 
//	$('#userIntroduce').val(r.maps.userInfo.introduce);		//简介
	$('#userTelephone').val(r.maps.userInfo.telephone);	//电话
	$('#userEmailAddress').val(r.maps.userInfo.email);		//邮箱地址
}
function getSex(sex){
	var s = "";
	if(sex == "0" || sex == 0){
		s = "保密";
	}else if(sex == "1" || sex == 1){
		s = "男";
	}else if(sex == "2" || sex == 2){
		s = "女";
	}
	return s;
}
//加载插件
function loadMod(){
	//日期加载
	$.ms_DatePicker({
            YearSelector: ".sel_year",
            MonthSelector: ".sel_month",
            DaySelector: ".sel_day"
   });
	//读取地区
	//usage: 
	readTextFile("/lzxBlog/page/js/profile/profile.txt", function(text) {
		var date = text.split(",");	
		$('#userFrom').ganged({
			'data': date,
			'width': 100,
			'height': 30
		});
//		$('#test2').ganged({
//			'data': date
//		});
	});	
}

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

