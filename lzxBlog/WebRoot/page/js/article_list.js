//var html = $('#a_list1').html();
var mark = 0;	//当前标记  用于 加载更多
var how_many = 10; 		//一次读取多少条
var recordCount = 0;

$(document).ready(function() {
	
	format_content();	//格式化
	
	//页面加载时初始化
	mark = 0;
	how_many = 8;
	recordCount = 0;
	
   	var Request = getRequest();
	var page = Number(Request["page"]);	
	if(page == null || isNaN(page) || page<0){
		page = 0;
	}
	var user = Number(Request["u"]);
	if(user == null || user<=0 || isNaN(user) || typeof(user) == "undefined"){
		user=123456;
	}
	
	if($('#index_show_more').length == 1){
		load_more_list();
		$('#index_show_more').on('click', function(){
			load_more_list();
		});
	}
	var $uhp = $('#userArticle-ht-page');
	var $chp = $('#collection-ht-page');
	if($uhp.length == 1){
		loadUserArticles(user,page);	//读取该用户的文章
		loadPagination(recordCount,$uhp);	//初始化分页
	}else if($chp.length == 1){
		loadUserCollection(user,page);
		loadPagination(recordCount,$chp);
		
	}
	format_content();	//格式化
});

	function loadPagination(rc,$ht){
		var pageSize = how_many;
		var recordCount =rc;
	    Pagination.init($ht, pageChange);

	   	var Request = getRequest();
		var page = Number(Request["page"]);	//获取页数
		if(page == null || isNaN(page)|| page<0){
			page = 0;
		}
		Pagination.Page($ht, page, recordCount, pageSize);	
		
	    var srch = /(.*[\?]?.*\page\=).*(\&.*)/;
	    var srch2= /.*[\?]?.*[\&]?\page\=/;
	    
	    function pageChange(i) {  	
			var h = window.location.search;
			var search = "";
			if(h.match(srch)==null){
				if(h.match(srch2)==null){
					search = h + "&page=" + i;
				}else{
					search = h.match(srch2)[0] + i;
				}
			}else{
				search = h.match(srch)[1] + i + h.match(srch)[2];
			}
			window.location.search = (search);
	    }
	}
	
	//用户发表的文章分页加载
	function loadUserArticles(userID,page){
		$.ajax({
			type : 'post',
			url : 'loadUserArticle_list.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'page' : (page*how_many),
				'mark' : userID,
				'howMany' : how_many
			},
		success : function(r) {
				if (r.result == "ok") {
					recordCount = Number(r.maps.info);
					appendHtml(r);		//输出文章列表
					$("#write_article_name").text(r.maps.articleList[0][1].toString() + " 的文章");
					format_content();	//格式化
				}else if(r.result == "no"){
					var err = '<div style="text-align: center; width: 100%; height:400px;line-height: 400px; font-size:30px; color: #c5c5c5; ">'+
							'还没发表过文章喔'+
						'</div>';
					$('#article_list').append(err);
				}
			},
		error : function(err) {
				window.location.replace("/lzxBlog/index.html");
				alert("加载时出错");
			}
		}); 
	}
	
	//用户收藏的文章分页加载
	function loadUserCollection(userID,page){
		$.ajax({
			type : 'post',
			url : 'loadUserCollection_list.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'page' : (page*how_many),
				'mark' : userID,
				'howMany' : how_many
			},
		success : function(r) {
				if (r.result == "ok") {
					recordCount = Number(r.maps.info);
					appendHtml(r);		//输出文章列表
					$("#write_article_name").text(r.maps.userName + " 的收藏");
					format_content();	//格式化
				}else if(r.result == "no"){
					$("#write_article_name").text("还没有收藏的文章");
				}
			},
		error : function(err) {
				window.location.replace("/lzxBlog/index.html");
				alert("加载时出错");
			}
		}); 
	}
	
	//加载更多
	function load_more_list(){
		$.ajax({
			type : 'post',
			url : 'loadArticle_list.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			async: false,
			cache : false,
			data : {
				'mark' : mark,
				'howMany' : how_many
			},
		success : function(r) {
				if (r.result == "ok") {
					mark += how_many;
					appendHtml(r);	//输出文章列表
					format_content();	//格式化
					if(r.maps.articleList.length == how_many){
						$(".smp").css("display","none");
						$(".smb").css("display","block");
					}else {
						$(".smb").css("display","none");
						$(".smp").css("display","block");
					}
				}else if(r.result == "no"){
					$(".smb").css("display","none");
					$(".smp").css("display","block");
				}
			},
		error : function(err) {
				window.location.replace("/lzxBlog/index.html");
				alert("加载时出错");
			}
		}); 		
	}
	
	function format_content() {
		var $a = $("p", $(".textts"));
		//5 40  40*15=600 宽/字大小=字数  --- 20*5=100 高/行高=字数 
		// 高字数*宽字数*2=总字数
		var wordNumber = ($a.width()/parseInt($a.css("font-size")))*($a.height()/parseInt($a.css("line-height")))*2
		$a.each(function() {
			$(this).text(autoAddEllipsis($(this).text(),parseInt(wordNumber)));
		});
    }
	
	function appendHtml(r){
		for(var i=0 ; i< r.maps.articleList.length; i++){
			var html = 	'<li>' +
							'<a href="/lzxBlog/article/content.html?u='+r.maps.articleList[i][0].articleWriterId.toString()+'&art='+ r.maps.articleList[i][0].articleContentId.toString() +'" target="_self">' +
								'<div class="textts">' +
										'<h2>'+ r.maps.articleList[i][0].articleTitle.toString() +'</h2>' +
										'<p>'+ r.maps.articleList[i][0].articleContentText.toString() +'</p>'+
										'<div class="article_info">' +
											'<div>作者：'+ r.maps.articleList[i][1].toString() +'</div>' +
											'<div> | 发表时间：'+ r.maps.articleList[i][0].articleUpdateTime.toString().replace(/T/g, ' ') +'</div>' +
										'<div>' +
								'</div>' +
							'</a>' +
						'</li>';
			$('.article_list').append(html);
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

/****截取字符串 中文+英文****/
/* 
 * 处理过长的字符串，截取并添加省略号 
 * 注：半角长度为1，全角长度为2 
 *  
 * pStr:字符串 
 * pLen:截取长度 
 *  
 * return: 截取后的字符串 
 */
	function autoAddEllipsis(pStr, pLen) {

	    var _ret = cutString(pStr, pLen);
	    var _cutFlag = _ret.cutflag;
	    var _cutStringn = _ret.cutstring;

	    if ("1" == _cutFlag) {
	        return _cutStringn + "...";
	    } else {
	        return _cutStringn;
	    }
	}

/* 
 * 取得指定长度的字符串 
 * 注：半角长度为1，全角长度为2 
 *  
 * pStr:字符串 
 * pLen:截取长度 
 *  
 * return: 截取后的字符串 
 */
	function cutString(pStr, pLen) {
	    // 原字符串长度  
	    var _strLen = pStr.length;
	    var _tmpCode;
	    var _cutString;
	    // 默认情况下，返回的字符串是原字符串的一部分  
	    var _cutFlag = "1";
	    var _lenCount = 0;
	    var _ret = false;
	    if (_strLen <= pLen / 2) {
	        _cutString = pStr;
	        _ret = true;
	    }
	    if (!_ret) {
	        for (var i = 0; i < _strLen ; i++) {
	            if (isFull(pStr.charAt(i))) {
	                _lenCount += 2;
	            } else {
	                _lenCount += 1;
	            }

	            if (_lenCount > pLen) {
	                _cutString = pStr.substring(0, i);
	                _ret = true;
	                break;
	            } else if (_lenCount == pLen) {
	                _cutString = pStr.substring(0, i + 1);
	                _ret = true;
	                break;
	            }
	        }
	    }
	    if (!_ret) {
	        _cutString = pStr;
	        _ret = true;
	    }
	    if (_cutString.length == _strLen) {
	        _cutFlag = "0";
	    }
	    return { "cutstring": _cutString, "cutflag": _cutFlag };
	}

/* 
 * 判断是否为全角 
 *  
 * pChar:长度为1的字符串 
 * return: true:全角 
 *          false:半角 
 */
	function isFull(pChar) {
	    if ((pChar.charCodeAt(0) > 128)) {
	        return true;
	    } else {
	        return false;
	    }
	}
/****截取字符串 END ****/
