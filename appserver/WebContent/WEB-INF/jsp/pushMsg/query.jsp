<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推送信息列表</title>
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

	<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
					<!-- <a class="mini-button" iconCls="icon-edit" onclick="editRow()"
					plain="true">编辑</a> <span class="separator"> --></span><a
					class="mini-button" iconCls="icon-remove" onclick="removeRow()"
					plain="true">删除</a> <%
 	/*<span class="separator"></span>
 	 <a class="mini-button" iconCls="icon-save" onclick="saveData()"
 	 plain="true">保存</a><span class="separator"> <a
 	 class="mini-button" iconCls="icon-save" onclick="uploadImg()"
 	 plain="true">为消息添加图片</a>*/
 %></td>
				<td style="white-space: nowrap;"><input id="key"
					class="mini-textbox" emptyText="请输入标题"
					style="width: 250px;" onenter="onKeyEnter" /> <a
					class="mini-button" onclick="search()">查询</a></td>
			</tr>
		</table>
	</div>

	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" url="loadPushMsg" idField="p_id"
			allowResize="true" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div type="checkcolumn"></div>
				<div name="p_id" field="p_id" headerAlign="center" allowSort="false"
					width="150" style="display: none;">
					主键 <input property="editor" class="mini-Hidden"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="p_type" field="p_type" headerAlign="center"
					type="comboboxcolumn" allowSort="false" width="150">
					消息类型 <input property="editor" class="mini-combobox"
						style="width: 100%;" minWidth="200" textField="type_text"
						valueField="type_id" url="../pushMsg/getComboxType?type=p_type" />
				</div>
				<div name="p_title" field="p_title" headerAlign="center"
					allowSort="false" width="150">标题</div>
				<div name="p_desc" field="p_desc" headerAlign="center"
					allowSort="false" width="150">消息简介</div>
				<div name="p_mode" field="p_mode" width="100" allowSort="false"
					type="comboboxcolumn">
					推送方式<input property="editor" class="mini-combobox" value="25"
						textField="mode_text" valueField="mode_id" style="width: 100%;"
						url="../pushMsg/getComboxType?type=p_mode" />
				</div>
				<div name="p_range" field="p_range" width="100" allowSort="false"
					type="comboboxcolumn">
					推送范围 <input property="editor" class="mini-combobox"
						textField="range_text" valueField="range_id" style="width: 100%;"
						url="../pushMsg/getComboxType?type=p_range" />

				</div>
				<div name="p_imgurl" field="p_imgurl" width="100" allowSort="false"
					headerAlign="center">消息图片</div>
				<div name="showReplyinfo" width="100">回复数</div>

				<div name="p_pushtime" field="p_pushtime" width="120"
					allowSort="false" dateFormat="yyyy-MM-dd HH:mm">
					发送时间 <input property="editor" class="mini-datepicker"
						format="yyyy-MM-dd H:mm" showTime="true" style="width: 100%;" />
				</div>
				<div name="p_createtime" field="p_createtime" width="120"
					allowSort="false" dateFormat="yyyy-MM-dd HH:mm">创建时间</div>
				<div name="p_lastupdatetime" field="p_lastupdatetime" width="120"
					allowSort="false" dateFormat="yyyy-MM-dd HH:mm">最后修改时间</div>


				<!--ComboBox：本地数据 状态-->
				<div type="comboboxcolumn" autoShowPopup="true" name="status"
					field="status" width="100" allowSort="false" align="center"
					headerAlign="center">
					本地数据状态 <input property="editor" class="mini-combobox"
						style="width: 100%;" data="Statuses" />
				</div>
			</div>
		</div>
	</div>


	<div id="IMGWND" class="mini-window borderWindow" title="图片预览"
		style="width: 1000px; height: 500px;" allowResize="true">
		<img src="" id="imgShow">
	</div>



	<script type="text/javascript">
		//性别
		var Sexes = [ {
			id : 1,
			text : '男'
		}, {
			id : 2,
			text : '女'
		} ];
		/*-1：未注册 0：删除 1：正常 2：禁用*/
		var Statuses = [ {
			id : 0,
			text : '删除'
		}, {
			id : 1,
			text : '正常'
		}, {
			id : 2,
			text : '禁用'
		} ];
		/*0：未与数据库同步，同步时执行insert操作  1：已经与数据库同步，同步时执行update操作*/
		var IsTranses = [ {
			id : 0,
			text : '未同步'
		}, {
			id : 1,
			text : '已同步'
		} ];

		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		grid.on("drawcell", function(e) {

			var record = e.record, column = e.column;
			if (column.name == 'p_imgurl') {
				e.cellStyle = "text-align:center";
				if (record.p_imgurl != null) {
					e.cellHtml = '<a href="javascript:lookImg(\''
							+ record.p_imgurl + '\')">预览</a>&nbsp; ';
				}
			}
		});

		// var editorId="editor1"

		//////////////////////////////////////////////////////

		function search() {
			var key = mini.get("key").getValue();

			grid.load({
				key : key
			});
		}

		function onKeyEnter(e) {
			search();
		}

		function addRow() {

			mini.open({
				url : "../pushMsg/editPushMsg",
				title : "发布新消息",
				width : 900,
				height : 530,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "new"
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {

					grid.reload();
				}
			});
		}
		function removeRow() {
			if (confirm("确定删除选中的数据吗?")) {
				var rows = grid.getSelecteds();
				if (rows.length > 0) {
					grid.removeRows(rows, false);
					saveData();
				}
			}
		}
		function editRow() {

			var row = grid.getSelected();
			if (row) {
				mini.open({
					url : "../pushMsg/editPushMsg",
					title : "编辑消息",
					width : 900,
					height : 470,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit",
							id : row.p_id
						};
						iframe.contentWindow.SetData(data);

					},
					ondestroy : function(action) {
						grid.reload();

					}
				});

			} else {
				alert("请选中一条记录");
			}
		}
		function saveData() {
			var data = grid.getChanges();
			var json = mini.encode(data);

			grid.loading("操作提交中，请稍后......");
			$.ajax({
				url : "../pushMsg/saveData",
				data : {
					data : json
				},
				type : "post",
				success : function(text) {
					alert(text);
					grid.reload();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		}

		function lookImg(imgUrl) {

			var imgPath = "http://" + window.location.host + "/mHealth/images/"
					+ imgUrl;

			$("#imgShow").attr("src", imgPath);

			mini.get("IMGWND").show();

		}
	</script>
</body>
</html>