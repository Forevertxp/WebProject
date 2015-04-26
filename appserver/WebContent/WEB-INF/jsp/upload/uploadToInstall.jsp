<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<script type="text/javascript" src="../scripts/boot.js"></script>
<script type="text/javascript" src="../scripts/swfupload.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.6.2.min.js"></script>
<!--MiniUI-->
<link href="../scripts/miniui/themes/default/miniui.css"
	rel="stylesheet" type="text/css" />
<script src="../scripts/miniui.js" type="text/javascript"></script>
<script src="http://ckeditor.com/apps/ckeditor/4.3.1/ckeditor.js"
	type="text/javascript"></script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}
</style>
</head>
<body>
	<input type="hidden" id="p_id" name="p_id" />
	<form action="uploadFile" enctype="multipart/form-data" method="post"
		name="fileForm">
		<table width="100%" align="center">
			<tr>
				<td align="right">上传文件说明:</td>
				<td colspan="3">默认保存地址为:/mHealth/scripts/install/文件名称.</td>
			</tr>
			<tr>
				<td align="right">选择文件:</td>
				<td colspan="3"><input name="imgFile" id="imgFile" type="file"
					style="width: 95%" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit" value="上传"
					onclick="return upload();"></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();

		var form = new mini.Form("form1");

		function onUploadSuccess(e) {

			alert("上传成功：" + e.serverData);

			this.setText("");
		}
		function onUploadError(e) {
			alert(e.message);
		}

		function upload() {

			var img = $("#imgFile").val();

			if (img == null || img == "") {
				alert("请选择文件！");
				return false;
			} else {
				var FileName=new String(img);
				var extension=new String (FileName.substring(FileName.lastIndexOf(".")+1,FileName.length));//文件扩展名
				if(extension=="xml"||extension=="apk"||extension=="XML"||extension=="APK"){
					return true;
				}else{
					alert('请选择xml和apk格式文件上传')
					return false;
				}
				

			}
		}
	</script>

</body>
</html>