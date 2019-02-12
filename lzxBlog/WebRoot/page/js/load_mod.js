//var w = 0;
document.addEventListener("DOMContentLoaded", function(){
	document.body.style.display = "block";
	$('body').show();
});

$(document).ready(function() {
	$('body').show();
	$('body').css("display","block");
	var mod = new Array(
			"nav",
		//	"fotter_bottom",
			"article_list",
			"info_right",
			"isLogin"
		);
	/*$(".center").css("position", "relative");*/

	
	for(var i=0; i< mod.length; i++) {	
		if(document.getElementById(""+mod[i]+"")) {
			$('#'+mod[i]+'').load('/lzxBlog/page/mod/'+mod[i]+'_mod.jsp').html();
		}
	}

/*	var center = $('.center');
	center.children().each(function(){
		w += parseInt($(this).width());//获取宽度。并累加
	});
	
	center.propup();//要居中的DOM元素
*/});


//居中
/*(function($) {
	var methods = {
		autosize: function(ele) {
				//ele.css("padding-left", ($(window).width() - ele.width()) / 2);
				var eleW = ($(window).width() - ele.width()) / 2; 
				if(eleW <0){
					ele.css("width",w);
				}else{
					ele.css("width",w + eleW);
				}
		}
	}
	$.fn.extend({
		propup: function(options) {
			$this = $(this);
			methods.autosize($this);
			$(window).resize(function() {
				methods.autosize($this);
			});
		}
	});
})(jQuery);*/