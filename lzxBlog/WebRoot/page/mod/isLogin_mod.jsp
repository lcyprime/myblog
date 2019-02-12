<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>

<c:if test="${userInfo!=null}">
	<p id="isLogin_getUserID" style="display: none;">userInfo.userId<p>
		<script type="text/javascript">	
		//用户回复
		if(document.getElementById("content-message-html")){
			document.getElementById("content-message-html-is").style.display='block';
			
			document.getElementById("content-message-html-not").style.display='none';
		}			
	</script>
</c:if>
<c:if test="${userInfo==null}">
	<script type="text/javascript">	
		//用户写文章
		if(document.getElementById("the-myWrite-html")){	
			window.location.replace("/lzxBlog/auth/Login.html");
			alert("请您先登录");
		}
		//用户回复
		if(document.getElementById("content-message-html")){
			document.getElementById("content-message-html-is").style.display='none' ;
			document.getElementById("content-message-html-not").style.display='block' ;
		}
		//用户资料
		if(document.getElementById("profile_isLogon")){
			if(document.getElementById("profile_table")){
			//	$("#profile_table").load('/lzxBlog/page/mod/profile_table2.html').html();
			}
		}
	</script>
</c:if>
</div>