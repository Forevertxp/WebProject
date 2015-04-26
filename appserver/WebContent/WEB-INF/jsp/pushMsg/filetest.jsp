<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="uploadMsgImg" enctype="multipart/form-data" method="post"
		name="fileForm">
		<table width="100%" align="center">
			<tr>
				<td align="right">消息图片:</td>
				<td><input name="imgFile" id="imgFile" type="file" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="上传" />
					<input type="button" value="关闭" onclick="closeWND('IMGWND')" /></td>
			</tr>
		</table>

	</form>
</body>
</html>