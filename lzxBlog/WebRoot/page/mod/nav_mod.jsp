<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>

<div class="headNav">
	<c:if test="${userInfo!=null}">	<!-- 不为空时  -->
	<div class="show_more_function_img" onclick="show_function();"></div>
	<div class="show_more_function">
		<div style="height: 80px; "></div>
		<div class="myfollow">
			<h2 style="margin: 25px 0px 10px 0px; text-align: center; color: #FE9A2E">我的关注</h2>
			<ul class="user_follow"></ul>
		</div>
	</div>
		<div class="navigation">
			<ul class="nav_page">
				<li><a href="/lzxBlog/index.html">首 页</a></li>
				<li><a href="/lzxBlog/collection.html?u=${userInfo.userId}" >收 藏</a></li>
				<li><a href="/lzxBlog/userArticles.html?u=${userInfo.userId}" >我的文章</a></li>
			</ul>
			<div class="nav_user">
					<div class="user_top">
					<a href="/lzxBlog/profile.html">
						<img src="${userInfo.userLogo}" />
						<span class="user_name">${userInfo.userName}</span>
						</a>
					</div>
				
				<ul class="user_option">
					<li><a href="">回复</a></li>
					<li><a href="">私信</a></li>
					<li onclick="doCancellation()" >[退 出]</li>
				</ul>
			</div>
		</div>
	</c:if>
	
	<c:if test="${userInfo==null}">	<!-- 为空时  -->
		<div class="navigation">
			
			<ul class="nav_page">
				<li><a href="/lzxBlog/index.html">首 页</a></li>
			</ul>
			<div class="nav_user">
				<div class="index_login"><a href="/lzxBlog/auth/Login.html">登 录&nbsp;&nbsp;|</a></div>
				<div class="index_register"><a href="/lzxBlog/auth/register.html">|&nbsp;&nbsp;注  册</a></div>
			</div>
		</div>
	</c:if>

</div>
<section id="button-top" >Top↑</section>
<style type="text/css">

.center{
/* 	padding-left:160px; */
	padding-left:0px;
	/* background: red; */
	width: -moz-fit-content;
	width:-webkit-fit-content;
	width: fit-content;
	margin: 0px auto;
	text-align: center;
}
#button-top {
	position: fixed;
    bottom: 25px;
    right: 40px;
    width: 50px;
    height: 50px;
    background-color: #FFFFFF;
    border: 1px solid rgba(0,0,0,.02);
    box-shadow: 0 1px 5px 0 rgba(0,0,0,.06);
    border-radius: 5px;
    cursor: pointer;
    z-index: 10;
    display: none;
    color: #fe9a2e;
    text-align: center;
    line-height: 50px;
    font-weight: bold;
    font-family: "黑体";
}
#button-top:hover {
	 color: #FFFFFF;
	background-color: #fe9a2e;
}
.myfollow{
	height: 100%;
	width: 100%;
	overflow-y:auto; 
	position: absolute;
	border-right: 1px solid #888888;
	border-top: 1px solid #888888;
	background-color: white;
}
.myfollow:HOVER{
	border-right: 1px solid #FE9A2E;
	border-top: 1px solid #FE9A2E;
}
</style>

<script type="text/javascript" src="/lzxBlog/page/js/nav.js"></script>
<!-- 
${empty user.name}
${not empty user.name} 
-->

<link rel="stylesheet" href="/lzxBlog/page/css/nav.css" />
