<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
</head>
<body>
	<form action="doadd" method="post">
	<input type="hidden" id="inputID" name="id" value="111"/>
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="inputUserName" name="username"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" id="inputPassword" name="password"></td>
			</tr>
			<tr>
				<td><input type="submit" id="inputSubmit" value="添加用户"/></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>