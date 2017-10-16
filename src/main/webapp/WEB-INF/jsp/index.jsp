<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/js/jquery-1.9.js"></script>
<link rel="stylesheet" type="text/css" href="/js/style.css"
	tppabs="/js/style.css" />
<style>
body {
	height: 100%;
	background: #16a085;
	overflow: hidden;
}

canvas {
	z-index: -1;
	position: absolute;
}
</style>
<!-- <script src=/js/jquery.js"></script> -->
<link rel="stylesheet" type="text/css"
	href="/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript"
	src="/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>

<script src="/js/verificationNumbers.js"
	tppabs="/js/verificationNumbers.js"></script>
<script src="/js/Particleground.js" tppabs="/js/Particleground.js"></script>
<script type="text/javascript">
	$(function() {
		$('#ff').form({
			success : function(data) {
				data = jQuery.parseJSON(data);
				if (data.status != 200) {
					$.messager.alert('错误', data.msg, 'info');
				} else {
					$(window).attr('location', '/home');
				}

			}
		});
	});

	$(document).ready(function() {
		//粒子背景特效
		$('body').particleground({
			dotColor : '#5cbdaa',
			lineColor : '#5cbdaa'
		});

	});
</script>
<title>系统登陆</title>
</head>
<body>
	<form id="ff" action="/login" method="post">
		<dl class="admin_login">
			<dt>
				<strong>签到管理系统</strong> <em></em>
			</dt>
			<dd class="user_icon">
				<input type="text"  name="username" placeholder="账号" class="login_txtbx" />
			</dd>
			<dd class="pwd_icon">
				<input type="password"  name="password" placeholder="密码" class="login_txtbx" />
			</dd>
			<%-- <dd class="val_icon">
  <div class="checkcode">
    <input type="text" id="J_codetext" placeholder="验证码" maxlength="4" class="login_txtbx">
    <canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
  </div>
  <input type="button" value="验证码核验" class="ver_btn" onClick="validate();">
 </dd> --%>
			<dd>
				<input type="submit" value="登陆" class="submit_btn" />
			</dd>
			<dd>
				<p></p>
				<p></p>
			</dd>
		</dl>
	</form>
</body>
</html>
