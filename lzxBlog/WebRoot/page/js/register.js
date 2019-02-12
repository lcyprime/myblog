window.onload = function() {
	var tReg = document.getElementById("tReg");
	tReg.style.opacity = 1;


}

var unMax = 16;	
var unMin = 3;
var pwMax = 16;
var pwnMin = 6;
var regString = /^[a-zA-Z0-9]+$/; //验证大小写26个字母任意字母最少出现1次。

function format(userN, passW, rpassW) {
	var uNum = userN.val();
	var pWord = passW.val();
	var rpWord = rpassW.val();
	
	if (uNum == null || uNum == "") {
		return "请填写用户名";
	}
	if ((uNum.length > unMax || uNum.length < unMin) || !(regString.test(uNum))) {
		userN.css({"border" : "1px solid red"});
		return "用户名格式:3-16由英文或数字组合";
	}
	userN.css({"border" : "none"});

	if (pWord == null || pWord == "") {
		return "请填写密码";
	}
	if ((pWord.length > pwMax || pWord.length < pwnMin) || !(regString.test(pWord))) {
		passW.css({"border" : "1px solid red"});
		return "密码格式:6-16由英文或数字组合";
	}
	passW.css({"border" : "none"});

	if (rpWord != pWord) {
		rpassW.css({"border" : "1px solid red"});
		return "密码不一致";
	}
	rpassW.css({"border" : "none"});

	return "ok";
}

function doRegister() {
	var userN = $("#userNum");
	var passW = $("#passWord");
	var rpassW = $("#rpassWord");
	var tips = $("#tips");
	var ts = format(userN, passW, rpassW);
	
	if (ts == "ok") {
		$.ajax({
			type : 'post',
			url : 'register.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			data : {
				'usernum' : userN.val(),
				'password' : passW.val(),
				'rpassword' : rpassW.val(),
			},
			success : function(r) {
				if (r.result == "ok") {
					alert("注册成功！");
					window.location.replace("Login.html");
				}
				tips.text(r.result);
			},
			error : function(err) {
				alert("注册时发生错误" + err);
				window.location.reload();
			}
		});
	} else {
		tips.text(ts);
	}


}


function removeTips() {
	var userN = $("#userNum");
	var passW = $("#passWord");
	var tips = $("#tips");

	userN.css({
		"border" : "none"
	});
	passW.css({
		"border" : "none"
	});
	tips.text("");
}

//function getRequest() { //用于获取表单提交的数据
//	var url = window.location.search; //获取url中"?"符后的字串     
//	var theRequest = new Object();
//	if(url.indexOf("?") != -1) {
//		var str = url.substr(1);
//		strs = str.split("&");
//		for(var i = 0; i < strs.length; i++) {
//			//就是这句的问题  
//			theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
//			//之前用了unescape()  
//			//才会出现乱码    
//		}
//	}
//	return theRequest;
//}