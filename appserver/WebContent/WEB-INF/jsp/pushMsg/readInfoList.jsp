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
<title>阅读记录</title>
<script type="text/javascript" src="../scripts/boot.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.6.2.min.js"></script>
<!--MiniUI-->
<link href="../scripts/miniui/themes/default/miniui.css"
	rel="stylesheet" type="text/css" />
<script src="../scripts/miniui.js" type="text/javascript"></script>
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
					<a class="mini-button" iconCls="icon-remove" onclick="removeRow()"
					plain="true">删除</a> <a
					class="mini-button" iconCls="icon-save" onclick="saveData()"
					plain="true">保存</a></td>
				<td style="white-space: nowrap;"><input id="key"
					class="mini-textbox" emptyText="请输入消息标题"
					style="width: 250px;" onenter="onKeyEnter" /> <a
					class="mini-button" onclick="search()">查询</a></td>
			</tr>
		</table>
	</div>

	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" url="../pushMsg/loadListData"
			idField="r_id" allowResize="true" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div type="checkcolumn"></div>
				<div name="r_id" field="r_id" headerAlign="center" allowSort="false"
					width="150" style="display: none;">阅读记录</div>
				<div name="p_title" field="p_title" headerAlign="center"
					allowSort="false" width="150">消息标题</div>
				<div name="u_name" field="u_name" headerAlign="center"
					allowSort="false" width="150">用户姓名</div>
				<div name="u_id" field="u_id" headerAlign="center" allowSort="false"
					width="150">用户ID</div>
				<div type="comboboxcolumn" autoShowPopup="true" name="r_flg"
					field="r_flg" width="100" allowSort="false" align="center"
					headerAlign="center">
					阅读次数 
				</div>
				<div name="r_time" field="r_time" width="100" allowSort="false"
					dateFormat="yyyy-MM-dd">阅读时间</div>
				<div name="r_createtime" field="r_createtime" width="100"
					allowSort="false" dateFormat="yyyy-MM-dd">创建时间</div>

				<!--ComboBox：本地数据 状态-->
				<div type="comboboxcolumn" autoShowPopup="true" name="status"
					field="status" width="100" allowSort="false" align="center"
					headerAlign="center">
					状态<input property="editor" class="mini-combobox"
						style="width: 100%;" data="Statuses" />
				</div>
			</div>
		</div>
	</div>



	<script type="text/javascript">
		//性别
		var Flgs = [ {
			id : 1,
			text : '已阅读'
		}, {
			id : 0,
			text : '未阅读'
		} ];
		/*-1：未注册 0：删除 1：正常 2：禁用*/
		var Statuses = [ {
			id : 0,
			text : '删除'
		}, {
			id : 1,
			text : '正常'
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
			var newRow = {
				name : "New Row"
			};
			grid.addRow(newRow, 0);

			grid.beginEditCell(newRow, "LoginName");
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
		function saveData() {

			var data = grid.getChanges();
			var json = mini.encode(data);

			grid.loading("操作提交中，请稍后......");
			$.ajax({
				url : "../pushMsg/saveReadLog",
				data : {
					data : json
				},
				type : "post",
				success : function(text) {
					grid.reload();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		}

		grid.on("celleditenter", function(e) {
			var index = grid.indexOf(e.record);
			if (index == grid.getData().length - 1) {
				var row = {};
				grid.addRow(row);
			}
		});
		//树形变动
	</script>


</body>
</html>