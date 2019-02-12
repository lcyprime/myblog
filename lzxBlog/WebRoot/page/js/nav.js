
var w = parseInt($('.show_more_function').width());
var center = $('.center');

$.ajax({
	type : 'post',
	url : 'relation_LoadFollow.action', //此处的Action要与struts.xml中的action的name对应
	dataType : 'json',
	async: false,
	cache : false,	
	success : function(r) {
		if (r.result == "ok") {
			appendRelation(r);
		}else if(r.result == "no"){
			
		}
	},
	error : function(err) {
	/*		window.location.replace("/lzxBlog/index.html");
			alert("加载时出错");*/
		}
});
function doCancellation(){
		$.ajax({
			type : 'post',
			url : 'cancellation.action', //此处的Action要与struts.xml中的action的name对应
			dataType : 'json',
			success : function(r) {
				if (r.result == "ok") {
					window.location.replace("/lzxBlog/index.html");
					alert("已退出登录");
				}
			},
		});
}
var a = 1;
function show_function(){
	var nw = 200;
	var $show = $('.show_more_function');
	if(a==1){
		$show.css("left","0px");
		if(parseInt($show.css("left")) == 0){
			$show.css("display"," block");
		}
		if(parseInt(center.css("margin-left")) < nw){
			center.css("padding-left",nw+ "px");
		}else{
			center.css("padding-left",0+ "px");
		}
		a=0;
	}else{
		$show.css("left",(0-nw)+"px");
		if(parseInt($show.css("left")) == (0-nw)){
			$show.css("display"," none");
		}
		center.css("padding-left",0+ "px");
		a=1;
	}
}

$(document).ready(function() 
{
    $(function()
    {
      	$(window).scroll(function()
      	{
	        $(window).scrollTop() > 300 ? $("#button-top").fadeIn('slow') : $("#button-top").fadeOut('slow');
	    });

	    $("#button-top").click(function()
	    {
	        $('body,html').animate({scrollTop: 0},500);
	        return false;
      	});
    });
});

function appendRelation(r){
	for(var i=0 ; i< r.maps.followList.length; i++){
		var html = 	'<li><a href="/lzxBlog/userArticles.html?u='+r.maps.followList[i].userId+'">'+
						'<img src="'+r.maps.followList[i].userLogo+'" />'+
						r.maps.followList[i].userName + '</a>'+
					'</li>';
		$('.user_follow').append(html);
	}
}