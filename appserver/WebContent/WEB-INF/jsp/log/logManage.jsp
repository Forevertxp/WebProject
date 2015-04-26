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
<title>日志查询</title>
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
					plain="true">删除</a> <span class="separator"></span> <a
					class="mini-button" iconCls="icon-save" onclick="saveData()"
					plain="true">保存</a></td>
				<td style="white-space: nowrap;"><input id="key"
					class="mini-textbox" emptyText="请输入日志名称"
					style="width: 250px;" onenter="onKeyEnter" /> <a
					class="mini-button" onclick="search()">查询</a></td>
			</tr>
		</table>
	</div>

	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" url="../log/loadListData"
			idField="v_id" allowResize="true" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div type="checkcolumn"></div>
				<div name="v_id" field="v_id" headerAlign="center" allowSort="false"
					width="150" style="display: none;">
					日志ID<input property="editor" class="mini-Hidden"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="u_name" field="u_name" headerAlign="center"
					allowSort="false" width="150">
					用户姓名 <input property="editor" class="mini-textbox"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="u_id" field="u_id" headerAlign="center" allowSort="false"
					width="150">
					用户ID <input property="editor" class="mini-textbox"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="t_id" field="t_id" headerAlign="center" allowSort="false"
					width="150">
					检测ID <input property="editor" class="mini-textbox"
						style="width: 100%;" minWidth="200" />
				</div>

				<div name="u_time" field="u_time" width="100" allowSort="false"
					dateFormat="yyyy-MM-dd">
					操作时间 <input property="editor" class="mini-datepicker"
						style="width: 100%;" />
				</div>

				<!--ComboBox：本地数据 状态-->
				<div type="comboboxcolumn" autoShowPopup="true" name="status"
					field="status" width="100" allowSort="false" align="center"
					headerAlign="center">
					状态 <input property="editor" class="mini-combobox"
						style="width: 100%;" data="Statuses" />
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		/*0:请求被关注  1:有效关注 2:取消被关注 3:取消关注*/
		var Statuses = [ {
			id : 0,
			text : '有效'
		}, {
			id : 1,
			text : '删除'
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
				url : "../user/saveUser",
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
		 
	</script>


</body>
</html>