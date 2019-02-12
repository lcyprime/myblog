var lujin = "/lzxBlog/page/";

$(function () {
	
	$("#changeBackgroundImg").on("click",function(){
		showORcolesBackgroundTailor();
	});
//////////头像上传/////////
	//弹出图片裁剪框
	$("#replaceImg #changeLogo").on("click", function() {
		showORcolesLogoTailor();
	});
	
	//cropper图片裁剪
	$('#tailoringImg').cropper({
		aspectRatio: 1 / 1, //默认比例
		preview: '.previewImg', //预览视图
		guides: false, //裁剪框的虚线(九宫格)
		autoCropArea: 0.5, //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
		movable: false, //是否允许移动图片
		dragCrop: true, //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
		movable: true, //是否允许移动剪裁框
		resizable: true, //是否允许改变裁剪框的大小
		zoomable: false, //是否允许缩放图片大小
		mouseWheelZoom: false, //是否允许通过鼠标滚轮来缩放图片
		touchDragZoom: true, //是否允许通过触摸移动来缩放图片
		rotatable: true, //是否允许旋转图片
		crop: function(e) {
			// 输出结果数据裁剪图像。
		}
	});
	//旋转
	$(".cropper-rotate-btn").on("click", function() {
		$('#tailoringImg').cropper("rotate", 45);
	});
	//复位
	$(".cropper-reset-btn").on("click", function() {
		$('#tailoringImg').cropper("reset");
	});
	//裁剪后的处理
	$("#sureCut").on("click", function() {
		if($("#tailoringImg").attr("src") == null) {
			return false;
		} else {
			var cas = $('#tailoringImg').cropper('getCroppedCanvas'); //获取被裁剪后的canvas
		var base64url = cas.toDataURL('image/png'); //转换为base64地址形式
		$("#finalImg").prop("src", base64url); //显示为图片的形式
		
		//关闭裁剪框
		showORcolesLogoTailor();
			}
	});
	//换向
	var flagX = true;
	$(".cropper-scaleX-btn").on("click", function() {
	if(flagX) {
		$('#tailoringImg').cropper("scaleX", -1);
		flagX = false;
	} else {
		$('#tailoringImg').cropper("scaleX", 1);
		flagX = true;
	}
	flagX != flagX;
	});
	//////////////////////	
	//弹出框水平垂直居中
	(window.onresize = function() {
		var win_height = $(window).height();
		var win_width = $(window).width();
		if(win_width <= 768) {
			$(".tailoring-content").css({
				"top": (win_height - $(".tailoring-content").outerHeight()) / 2,
				"left": 0
			});
		} else {
			$(".tailoring-content").css({
				"top": (win_height - $(".tailoring-content").outerHeight()) / 2,
				"left": (win_width - $(".tailoring-content").outerWidth()) / 2
			});
		}
	})();

   
});
//地区加载
//读取文本文件
function readTextFile(file, callback) {
	var rawFile = new XMLHttpRequest();
	rawFile.overrideMimeType("application/json");
	rawFile.open("GET", file, true);
	rawFile.onreadystatechange = function() {
		if (rawFile.readyState === 4 && rawFile.status == "200") {
			callback(rawFile.responseText);
		}
	}
	rawFile.send(null);
}
//头像上传
//图像上传
function selectImg(file) {
	if(!file.files || !file.files[0]) {
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt) {
		var replaceSrc = evt.target.result;
		//更换cropper的图片
		$('#tailoringImg').cropper('replace', replaceSrc, false); //默认false，适应高度，不失真
	}
	reader.readAsDataURL(file.files[0]);
}
//关闭选择背景图片
function showORcolesLogoTailor() {
	$(".tailoring-container").toggle();
}
//弹出/关闭 选择背景图片
function showORcolesBackgroundTailor() {
	$("#replaceBackgroundImg").toggle();
};


function saveMyInfo(){
	var arrayInfo = objArray(new getMyInfoFromVal());
//	alert(arrayInfo[0].Key + " - " + arrayInfo[0].Value);
	if(arrayInfo.length>0) {
		var arr = JSON.stringify(arrayInfo);
		$.ajax({
			type : 'post',
			url : 'userInfoDoSaveOfUser.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			data : {
				'result' : arr
			},
			success : function(r) {
				if (r.result == "ok") {
					window.location.reload('/lzxBlog/profile.html');
					alert("保存成功");
				}
			},
			error : function(err) {
				alert("登录时发生错误" + err);
			//	window.location.reload();
			}
		});
	}else{
		tips.text(ts);
	}
	
}
//serverInfo为profile.html创建的变量  在 profile.js里修改
function objArray(info){
	var srcOrurl = new Array();
	if(!compare(serverInfo.rs.userName,info.nickName)){
		var s=new objData("userName",info.nickName); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.userLogo,info.logoimg)){
		var s=new objData("userLogo",info.logoimg); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.addressHome,info.address)){
		var s=new objData("addressHome",info.address); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.educational,info.educational)){
		var s=new objData("educational",info.educational); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.hobby,info.hobby)){
		var s=new objData("hobby",info.hobby); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.job,info.job)){
		var s=new objData("job",info.job); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
//	if(!compare(serverInfo.rs.introduce,info.introduce)){
//		var s=new objData("introduce",info.introduce); //创建键值对象
//		srcOrurl.push(s);//把对象放入对象数组中
//	}
	if(!compare(serverInfo.rs.telephone,info.telephone)){
		var s=new objData("telephone",info.telephone); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.email,info.emailAddress)){
		var s=new objData("email",info.emailAddress); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.sex,info.sex)){
		var s=new objData("sex",info.sex); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.birthday,info.birthday)){
		var s=new objData("birthday",info.birthday); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	if(!compare(serverInfo.rs.addressNow,info.from)){
		var s=new objData("addressNow",info.from); //创建键值对象
		srcOrurl.push(s);//把对象放入对象数组中
	}
	return srcOrurl;
}
function objData(key,value){
	this.Key=key;
	this.Value=value;
}
function compare(one,two){
	if(one == two){
		return true;
	}else{
		return false;
	}
}
//获取文本信息
function getMyInfoFromVal() {
	var cfromNum = $('div[name="province"] input[name="province"]').val() + "-" + $('div[name="city"] input[name="city"]').val() + "-" + $('div[name="area"] input[name="area"]').val();
	$('.sel_year').val() + $('.sel_month').val() + $('.sel_day').val()
	var cfrom = "";
	if($('div[name="province"] input[name="province"]').val() != -1){
		cfrom = cfrom + $('div[name="province"] .selected').eq(0).text();
	};
	if($('div[name="city"] input[name="city"]').val() != -1){
		cfrom = cfrom + "-" + $('div[name="city"] .selected').eq(0).text();
	};
	if($('div[name="area"] input[name="area"]').val() != -1){
		cfrom = cfrom + "-" +  $('div[name="area"] .selected').eq(0).text();
	};
	//地址
	this.from = cfrom + "&*&" + cfromNum;
	
	//日期
	this.birthday = "";
	if($('.sel_year').val() != 0){
		this.birthday = this.birthday + $('.sel_year').val();
	};
	if($('.sel_month').val() != 0){
		this.birthday = this.birthday +"-"+ $('.sel_month').val();
	};
	if($('.sel_day').val() != 0){
		this.birthday = this.birthday +"-"+ $('.sel_day').val();
	};
	//性别
	this.sex = $("#userSex .sex").val();
	this.nickName = $("#nickName").val();	//昵称
	this.logoimg = $('#finalImg').attr("src");	//头像
	this.address =$('#userAddress').val();	//详细地址
	this.educational = $('#userEducational').val();	//学历
	this.hobby = $('#userHobby').val();	//兴趣爱好
	this.job = $('#userJob').val();	//职业 
//	this.introduce = $('#userIntroduce').val();		//简介
	this.telephone = $('#userTelephone').val();	//电话
	this.emailAddress = $('#userEmailAddress').val();		//邮箱地址
}