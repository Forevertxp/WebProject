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
<title>咨询信息列表</title>
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
					iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a> <span
					class="separator"></span> <a class="mini-button"
					iconCls="icon-save" onclick="saveData()" plain="true">保存</a></td>
				<td style="white-space: nowrap;"><input id="key"
					class="mini-textbox" emptyText="请输入提问人/咨询标题" style="width: 250px;"
					onenter="onKeyEnter" /> <a class="mini-button" onclick="search()">查询</a></td>
			</tr>
		</table>
	</div>

	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" url="../consult/loadListData"
			idField="p_id" allowResize="true" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div type="checkcolumn"></div>
				<div name="c_id" field="c_id" headerAlign="center" allowSort="false"
					width="150" style="display: none;">主键</div>
				<div name="c_type" field="c_type" headerAlign="center"
					allowSort="false" width="150">类型</div>
				<div name="c_title" field="c_title" headerAlign="center"
					allowSort="false" width="150">标题</div>
				<div name="showReplyinfo" width="100" headerAlign="center">回复数</div>
				<div name="u_name" field="u_name" headerAlign="center"
					allowSort="false" width="150">咨询人</div>
				<div name="u_phone" field="u_phone" headerAlign="center"
					allowSort="false" width="150">咨询人手机</div>
				<div name=t_id field="t_id" width="100" allowSort="false">
					咨询相关</div>
				<div name="c_createtime" field="c_createtime" width="100"
					allowSort="false" dateFormat="yyyy-MM-dd">咨询时间</div>



			</div>

			<div name="lastreplytime" field="lastreplytime" width="100"
				allowSort="false" dateFormat="yyyy-MM-dd">最后回复时间</div>

			<!--ComboBox：本地数据 状态-->
			<div type="comboboxcolumn" autoShowPopup="true" name="status"
				field="status" width="100" allowSort="false" align="center"
				headerAlign="center">数据状态</div>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		/*-1：未注册 0：删除 1：正常 2：禁用*/
		var Statuses = [ {
			id : 0,
			text : '删除'
		}, {
			id : 1,
			text : '正常'
		}, {
			id : 2,
			text : '已同步'
		} ];

		mini.parse();

		var grid = mini.get("datagrid1");

		grid.load();
		grid.on("drawcell", function(e) {

			var record = e.record, column = e.column;
			if (column.name == 'showReplyinfo') {
				e.cellStyle = "text-align:center";
				e.cellHtml = '<a href="javascript:onShowReplyList(\''
						+ record.c_id + '\')">' + record.replycount
						+ '</a>&nbsp; ';
			}
		});
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
		function onShowReplyList(id) {
			 
			window.location.href = "consultReplyList?id=" + id;
		}
		function removeRow() {

			var rows = grid.getSelecteds();
			if (rows.length > 0) {
				if (confirm("确定删除选中记录？")) {
					var ids = [];
					for ( var i = 0, l = rows.length; i < l; i++) {
						var r = rows[i];
						ids.push(r.c_id);
					}
					var idStr = ids.join(',');
					alert(idStr);
					grid.loading("操作中，请稍后......");

					$.ajax({
						type : "POST",
						url : "../consult/batchDeleteConsultData",
						data : "ids=" + idStr,
						success : function(msg) {
							alert("操作完成:" + msg);
							grid.reload();
						}
					});
				}
			} else {
				alert("请选中一条记录");
			}

		}
	</script>


</body>
</html>