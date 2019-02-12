$(document).ready(function(){
	var Request = getRequest();
	var artID = Request["art"];	//获取用户名
//获取回复列表	
	if(artID !=null && artID !="" && typeof(artID) != "undefined"){
		$.ajax({
			type : 'post',
			url : 'loadArticle.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'address' : artID,
			},
		success : function(r) {
				if (r.result == "ok") {			
					//alert(r.maps.listMessage[0][0].msgContent.toString());		$('#article_reply ul').append(replyHtml);
					for(var i=0 ; i< r.maps.listMessage.length; i++){
						var replyHtml = '<li>'+
											'<img class="r_logo" src="'+r.maps.listMessage[i][1]+'" />'+
											'<div class="r_name">'+ r.maps.listMessage[i][0].messageWriterName.toString() +'</div>'+
											'<div class="r_content">'+ r.maps.listMessage[i][0].msgContent.toString() +'</div>'+
											'<div class="r_time">回复时间：'+ r.maps.listMessage[i][0].msgUpdateTime.toString().replace(/T/g, ' ') +'</div>'+
										'</li>'
							$('#article_reply ul').append(replyHtml);
					}
					
					var words  = CryptoJS.enc.Base64.parse(r.maps.articleContent.toString());
					$("#atext").html(words.toString(CryptoJS.enc.Utf8));
					$("#atitle").text(r.maps.artTitle.toString());
					changeCollection(r.maps.state);
					$('#upTime').text("发表时间：" + r.maps.updateTime);		//发表时间：2016-12-03 04:46:00
					$('#CollNum').text("收藏数：" + r.maps.collectionNum.toString());		//收藏数：30000000
				}else if(r.result == "no"){
					alert("读取时发生错误");
				}
			},
		error : function(err) {
			//	window.location.replace("/lzxBlog/index.html");
				alert("加载文章时出错");
			}
		});
	} 

});
//发表回复
function postContent(){
	var Request = getRequest();
	var artID = Request["art"];	//获取用户名
	//http://127.0.0.1:8020/lzxBlog/article/content.html?r=5004234
	//http://localhost:8132/lzxBlog/article/content.html?art=8SUK21260820181214eiGr
	var content = $("#reply").val();
	if(artID !=null && artID !="" && typeof(artID) != "undefined" && content!=null && content!=""){
		$.ajax({
			type : 'post',
			url : 'postReply.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'content' : content,
				'address' : artID,
			},
		success : function(r) {
				if (r.result == "ok") {
					alert("发表成功！");
					window.location.replace("/lzxBlog/article/content.html?art="+r.address);
				}else if(r.result == "no"){
					alert("发表时发生错误");
				}
			},
		error : function(err) {
				window.location.replace("/lzxBlog/index.html");
				alert("加载时出错");
			}
		}); 
	}
}
//改变订阅状态
function doCollection(state){
	var Request = getRequest();
	var artID = Request["art"];
	if(artID !=null && artID !="" && typeof(artID) != "undefined"){
		$.ajax({
			type : 'post',
			url : 'postCollection.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'address' : artID,
				'state' : state
			},
		success : function(r) {
				if (r.result == "ok") {
					changeCollection(state);
				}
			},
		error : function(err) {
				window.location.reload();
				alert("发生错误");
			}
		});
	}
}
//改变订阅按钮样式
function changeCollection(state){
	if(state==0){	//显示未订阅状态的按钮
		$("#collection_0").css("display","block");
		$("#collection_1").css("display","none");
	}else if(state==1){	//显示已订阅状态的按钮
		$("#collection_0").css("display","none");
		$("#collection_1").css("display","block");
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
