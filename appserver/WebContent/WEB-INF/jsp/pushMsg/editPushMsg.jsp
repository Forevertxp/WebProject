<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑推送信息</title>
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
	<form action="pushNewMsg" enctype="multipart/form-data" method="post"
		name="fileForm">
		<table width="100%" align="center">
			<tr>
				<td align="right">标题:</td>
				<td aligh="left" colspan="3"><input class="mini-textbox"
					id="p_title" name="p_title" emptyText="请输入标题..."
					style="width: 95%;" required="true"></td>
			</tr>
			<tr>

				<td align="right">消息类型:</td>
				<td align="left"><input id="p_type2" name="p_type2"
					class="mini-combobox" style="width: 200px;" textField="type_text"
					valueField="type_id" emptyText="请选择..." allowInput="false"
					url="../pushMsg/getComboxType?type=p_type" showNullItem="true"
					required="true" nullItemText="请选择..." /> <input
					class="mini-hidden" id="p_type" name="p_type" required="true">

				</td>
				<td align="right">发送时间:</td>
				<td align="left"><input id="p_pushtime2" name="p_pushtime2"
					class="mini-datepicker" style="width: 200px;"
					format="yyyy-MM-dd HH:mm" showTime="true" /></td>
			</tr>
			<tr>
				<td align="right">推送方式:</td>
				<td align="left"><input id="p_mode2" name="p_mode2"
					class="mini-combobox" style="width: 200px;" textField="mode_text"
					valueField="mode_id" emptyText="请选择..." allowInput="false"
					url="../pushMsg/getComboxType?type=p_mode" showNullItem="true"
					nullItemText="请选择..." required="true" /> <input
					class="mini-hidden" id="p_mode" name="p_mode" required="true">

				</td>
				<td align="right">推送范围:</td>
				<td align="left"><input id="p_range2" name="p_range2"
					class="mini-combobox" style="width: 200px;" textField="range_text"
					valueField="range_id" emptyText="请选择..." allowInput="false"
					url="../pushMsg/getComboxType?type=p_range" showNullItem="true"
					required="true" nullItemText="请选择..." /> <input
					class="mini-hidden" id="p_range" name="p_range" required="true">

				</td>

			</tr>
			<tr>
				<td align="right">消息图片:</td>
				<td colspan="3"><input name="imgFile" id="imgFile" type="file"
					style="width: 95%" /></td>
			</tr>
			<tr>
				<td align="right">消息简介:</td>
				<td colspan="3"><textarea id="p_desc" style="width: 95%;"
						name="p_desc" class="mini-textarea" emptyText="请输入简介"></textarea>

				</td>
			</tr>
			<tr>
				<td align="right">内容:</td>
				<td colspan="3"><textarea id="editor1" name="content">
					 </textarea> <input class="mini-hidden" id="p_content" name="p_content"
					required="true"></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit" value="发布"
					onclick="return Add_DO();">&nbsp;&nbsp;&nbsp; <input
					type="button" value="取消" onclick="onCancel()"></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();

		var editor = CKEDITOR.replace('editor1');
		var form = new mini.Form("form1");
		function onFileSelect(e) {
			//alert("选择文件");
		}
		function onUploadSuccess(e) {

			alert("上传成功：" + e.serverData);

			this.setText("");
		}
		function onUploadError(e) {
			alert(e.message);
		}

		function startUpload() {
			var fileupload = mini.get("fileupload1");

			fileupload.startUpload();
		}

		////////////////////
		//标准方法接口定义
		function SetData(data) {
			if (data.action == "edit") {
				//跨页面传递的数据对象，克隆后才可以安全使用
				data = mini.clone(data);

				$.ajax({
					url : "loadPushMsgInfoById?id=" + data.id,
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						
						if(o)
						{
							$("#p_id").val(o.p_id);
							mini.get("p_type2").setValue(o.p_type);
							mini.get("p_title").setValue(o.p_title);
							mini.get("p_desc").setValue(o.p_desc);
							mini.get("p_mode2").setValue(o.p_mode); 
							mini.get("p_range2").setValue(o.p_range);
							$("#imgFile").val(o.p_imgurl);
							editor.setData(o.p_content); 
							mini.get("p_pushtime2").setValue(o.p_pushtime);
						} 
					}
				});
			}
		}

		function GetData() {
			var o = form.getData();
			return o;
		}
		function CloseWindow(action) {
			if (action == "close" && form.isChanged()) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		function onOk(e) {
			SaveData();
		}
		function onCancel(e) {
			CloseWindow("cancel");
		}
		//////////////////////////////////

		function Add_DO() {
			var text = editor.getData();
			 
			var p_type = mini.get("p_type2").getValue();
			var p_mode = mini.get("p_mode2").getValue();
			var p_title = mini.get("p_title").getValue();
			var p_desc =  mini.get("p_desc").getValue();
			var p_range = mini.get("p_range2").getValue();
			var p_pushtime = mini.get("p_pushtime2").getValue();
			var img = $("#imgFile").val();

			if (text == null || text == "" || p_type == "" || p_type == null
					|| p_mode == "" || p_mode == null || p_title == ""
					|| p_title == null || p_range == "" || p_range == null
					|| p_pushtime == null || p_pushtime == "" || img == null
					|| img == ""|| p_desc == ""
					|| p_desc == null) {
				alert("数据没有全部填写！");
				return false;
			} else {
				mini.get("p_type").setValue(p_type);
				mini.get("p_mode").setValue(p_mode); 
				mini.get("p_range").setValue(p_range);
				$("#p_content").val(text);
				return true;
	<%/*var p_id = $("#p_id").val();

			 $.ajax({
			 url : 'pushNewMsg',
			 type : "post",
			 data : {
			 p_content : text,
			 p_type : p_type,
			 p_mode : p_mode,
			 p_title : p_title,
			 p_range : p_range,
			 p_pushtime : p_pushtime,
			 p_id : p_id
			 },
			 success : function(text) {
			 alert(text);
			 CloseWindow("save");
			 },
			 error : function(jqXHR, textStatus, errorThrown) {
			 alert(jqXHR.responseText);
			 //CloseWindow();
			 }
			 });*/%>
		}

		}
		function onDeptChanged(e) {
			var deptCombo = mini.getbyName("dept_id");
			var positionCombo = mini.getbyName("position");
			var dept_id = deptCombo.getValue();

			positionCombo
					.load("../data/AjaxService.aspx?method=GetPositionsByDepartmenId&id="
							+ dept_id);
			positionCombo.setValue("");
		}
	</script>

</body>
</html>