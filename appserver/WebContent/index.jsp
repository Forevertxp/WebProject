<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<script type="text/javascript" src="scripts/boot.js"></script>
<script type="text/javascript" src="scripts/jquery-1.6.2.min.js"></script>
<!--MiniUI-->
<link href="scripts/miniui/themes/default/miniui.css" rel="stylesheet"
	type="text/css" />
<script src="scripts/miniui.js" type="text/javascript"></script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}
 input{ width:200px; border-color: #788db0; border: 1px #788db0; height: 15px;}
</style>
</head>
<body>
<%
 Object loginUser=session.getAttribute("LoginUser");
 if(loginUser!=null){
	 response.sendRedirect("main/index");
 }else{
	 %>
	 <div id="loginWindow" style="width:1024px;height:620px;background: url('images/loginlog.png'); background-repeat: no-repeat;">
<div style="border-width: 1px; padding: 0px; left: 478px; top: 280px; width: 350px; height: 180px; position: absolute; z-index: 1000;"
		 >
		 <div id="loginForm" style="padding: 5px 5px 5px 5px;" style="width:300px;">
						<table width="290px">
							<tbody>
								<tr>
									<td style="width: 40px;"><label for="username$text">帐号：</label></td>
									<td style="width:140px;" align="left"><input id="username" onkeydown="keyEvent(event)" name="username"
										 type="text" /></td>
								</tr>
								<tr>
									<td style="width: 40px;"><label for="pwd$text">密码：</label></td>
									<td><input type="password" value=""  onkeydown="keyEvent(event)" name="pwd"
										id="pwd" emptyText="请输入密码" maxlength="20"/></td>
								</tr>
								<tr>
									<td></td>
									<td style="padding-top: 5px;"><a class="mini-button"
										style="width: 60px;" hidefocus="" id='loginbtn'
										href="javascript:onLoginClick()"><span
											class="mini-button-text ">登录</span></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="mini-button"
										style="width: 60px;" hidefocus=""
										href="javascript:onResetClick(0)"><span
											class="mini-button-text ">重置</span></a></td>
								</tr>
							</tbody>
						</table>
					</div>
	</div>
</div>
	
	<script type="text/javascript">
		mini.parse();

		var loginWindow = $("#loginWindow");
		 

		function onLoginClick() {

			var nm =$("#username").val();
			var pwd = $("#pwd").val();
			if (nm == "" || pwd == "") {
				alert("用户名密码不能为空.");
				return;
			}

			$.ajax({
				url : "user/validateLoginUser",
				data : {
					u_name : nm,
					u_password : pwd
				},
				type : "post",
				success : function(text) {

					if (text == "1") {
						loginWindow.hide();
						mini.loading("登录成功，马上转到系统...", "登录成功");

						setTimeout(function() {

							window.location = "main/index";
						}, 1500);
					} else {
						$("#pwd").val('');
						alert("账号或密码错误.");
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {

					alert(jqXHR.responseText);
				}
			});

		}
		
		function keyEvent(event) //
		{
		    if(event.keyCode==13)
		    {
		    	var nm =$("#username").val();
				var pwd = $("#pwd").val();
				if (nm != "" && pwd != "") {
					onLoginClick();
					return;
				}else{
					event.returnValue=false;//
				}
		        
		    }
		}
		function onResetClick(e) {
			 $("#username").val("");
			 $("#pwd").val("");
		}
		/////////////////////////////////////
		function isEmail(s) {
			if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
				return true;
			else
				return false;
		}
		function onUserNameValidation(e) {
			if (e.isValid) {
				if (isEmail(e.value) == false) {
					e.errorText = "必须输入邮件地址";
					e.isValid = false;
				}
			}
		}
		function onPwdValidation(e) {
			if (e.isValid) {
				if (e.value.length < 5) {
					e.errorText = "密码不能少于5个字符";
					e.isValid = false;
				}
			}
		}
	</script>
	<div class="mini-modal" id="__modalmini-1"
		style="display: block; z-index: 999;">
		<iframe frameborder="0"
			style="left: 0px; top: 0px; width: 100%; height: 100%; position: absolute; z-index: -1; scrolling: no;"></iframe>
	</div>
	 
	 
	 <%
 }
%>


</body>
</html>