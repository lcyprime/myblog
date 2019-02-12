var maxFilesSize = 1*1024*1024; //单个文件大小
var lujin = "/lzxBlog/page/img/ui/";

$(document).ready(function() {
	var input = document.getElementById("upgteimg");
	var showui = document.getElementById("showui");
	var upimg = document.getElementById("upimg");
	var result;
	var dataArr = new Array();
	var fd;
	var dateli, dateinput;
	if(typeof FileReader === 'undefined') {
		alert("抱歉，你的浏览器不支持 FileReader");
		input.setAttribute('disabled', 'disabled');
	} else {
		input.addEventListener('change', readFile, false);
	};
	
	function readFile() {
		if(dataArr.length<5){
			fd = new FormData();
			var iLen = this.files.length;

			var index = 0;
			var currentReViewImgIndex = 0;
			for(var i = 0; i < iLen; i++) {
				if(!input['value'].match(/.jpg|.gif|.png|.jpeg|.bmp/i)) {
					alert("上传的图片格式不正确，请重新选择");
					return;
				};

				var reader = new FileReader();
				reader.index = i;
				fd.append(i, this.files[i]);
				reader.readAsDataURL(this.files[i]);
				reader.fileName = this.files[i].name;
				var fileAllSize = this.files[i].size;
				reader.files = this.files[i];
				reader.onload = function(e) {
					var imgMsg = {
						name: this.fileName,
						base64: this.result,
						files: this.files
				};
				if(fileAllSize > maxFilesSize){
					alert("上传的图片大于" + maxFilesSize/1024/1024 + "m");
					return;
				}
					dataArr.push(imgMsg);
					if(dataArr.length>=5){
						upimg.style.display="none";
					}
					for(var j = 0; j < dataArr.length; j++) {
						currentReViewImgIndex = j;
					};
					result = '<div class="showdiv"><img class="imgTurnLeft" src="'+ lujin +'profile_Arrow_left.png" /><img class="imgTurnCenter" src="'+ lujin +'profile_delete.png" /><img class="imgTurnRight" src="'+ lujin +'profile_Arrow_right.png" /></div><img id="srcimgid' + currentReViewImgIndex + '" class="showimg" src="' + this.result + '" />';
					var li = document.createElement('li');
					li.innerHTML = result;
					showui.appendChild(li);
					index++;
				};
			};
		}else{
			upimg.style.display="none";
		}
	};
	
	$("div").delegate(".showdiv>.imgTurnLeft", "click", function(e) {
		var num = $(this).parent().index("#showui .showdiv");
		if(num == 0) {} else {
			var up = num - 1;
			dataArr.splice(up, 0, dataArr[num]);
			dataArr.splice(num + 1, 1);
			var lists = $("ul#showui li").length;
			for(var j = 0; j < lists; j++) {
				var usid = $("ul#showui li")[j].getElementsByTagName('img')[3];
				$("#" + usid.id + "").attr("src", dataArr[j].base64);
			};
		};
			e.stopPropagation(); //停止冒泡事件
	});

	$("div").delegate(".showdiv>.imgTurnCenter", "click", function(e) {
		e.stopPropagation(); //停止冒泡事件
		var num = $(this).parent().index("#showui .showdiv");
		if(dataArr.length == 1) {
			dataArr = new Array();
			$("ul#showui").html("");
		} else {
			$("ul#showui li:eq(" + num + ")").remove();
			dataArr.splice(num, 1);
		};
		upimg.style.display="block";
	});

	$("div").delegate(".showdiv>.imgTurnRight", "click", function(e) {
		var num = $(this).parent().index("#showui .showdiv");
		dataArr.splice(dataArr.length, 0, dataArr[num]);
		dataArr.splice(num, 1);
		var lists = $("ul#showui li").length;
		for(var j = 0; j < lists; j++) {
			var usid = $("ul#showui li")[j].getElementsByTagName('img')[3];
			$("#" + usid.id + "").attr("src", dataArr[j].base64);
		};
		e.stopPropagation(); //停止冒泡事件
	});
});