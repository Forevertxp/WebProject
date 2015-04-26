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
<title>用户管理</title>
<script type="text/javascript" src="../scripts/boot.js"></script>
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
				<td style="width: 100%;"> <a
					class="mini-button" iconCls="icon-remove" onclick="removeRow()"
					plain="true">删除</a><a class="mini-button"
					iconCls="icon-download" onclick="exportSelections()" plain="true">导出列表</a></td>
				<td style="white-space: nowrap;">
					姓名
					<input id="userName"
					class="mini-textbox" emptyText="请输入姓名"
					style="width: 80px;" onenter="onKeyEnter" /> 
					手机
					<input id="userPhone"
					class="mini-textbox" emptyText="请输入手机号"
					style="width: 80px;" onenter="onKeyEnter" /> 
					userID
					<input id="userId"
					class="mini-textbox" emptyText="请输入userid"
					style="width: 80px;" onenter="onKeyEnter" /> 
					最后修改时间
					<input id="updatetime" class="mini-datepicker"/>
					
					<a
					class="mini-button" onclick="search()">查询</a></td>
			</tr>
		</table>
	</div>

	<!--撑满页面-->
	<div class="mini-fit" style="background: red; height: 100px;">
	
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" url="../user/loadUserList"
			idField="u_id" allowResize="false" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn"></div>
				<div type="checkcolumn"></div>
				<div name="u_id" field="u_id" headerAlign="center" allowSort="false"
					width="150" style="display: none;">
					用户主键 <input property="editor" class="mini-Hidden"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="u_phoneimei" field="u_phoneimei" headerAlign="center"
					allowSort="false" width="150">
					手机IMEI码 <input property="editor" class="mini-Hidden"
						style="width: 100%;" minWidth="200" />
				</div>
				<div name="u_name" field="u_name" headerAlign="center"
					allowSort="true" width="150">
					用户姓名 <input property="editor" class="mini-Hidden"
						style="width: 100%;" minWidth="200" />
				</div>

				<div field="u_phone" width="100" allowSort="false">
					手机号 <input property="editor" class="mini-Hidden" value="25"
						style="width: 100%;" />
				</div>
				<div field="u_password" width="100" allowSort="false">
					密码 <input property="editor" class="mini-Hidden" value="25"
						style="width: 100%;" />
				</div>
				<div name="u_birthdate" field="u_birthdate" width="100"
					allowSort="false" dateFormat="yyyy-MM-dd">
					出生日期 <input property="editor" class="mini-Hidden"
						style="width: 100%;" />
				</div>
				<div name="u_regtime" field="u_regtime" width="100"
					allowSort="false" dateFormat="yyyy-MM-dd">
					注册日期 <input property="editor" class="mini-Hidden"
						style="width: 100%;" />
				</div>
				<div name="u_lastupdatetime" field="u_lastupdatetime" width="100"
					allowSort="false" dateFormat="yyyy-MM-dd">
					最后修改时间 <input property="editor" class="mini-Hidden"
						style="width: 100%;" />
				</div>
				<div field="u_profession" width="120" headerAlign="center"
					allowSort="false">
					电子邮箱 <input property="editor" class="mini-Hidden"
						style="width: 200px;" minWidth="200" minHeight="50" />
				</div>
				<!--ComboBox：本地数据-->
				<div field="u_sex" width="100" align="center" renderer="onSexRenderer">
					性别 <input property="editor" class="mini-Hidden"
						style="width: 100%;" />
				</div>
				<!--ComboBox：本地数据 状态-->
				<div field="status" width="100" align="center" renderer="onStatusRenderer">
					账户状态 <input property="editor" class="mini-Hidden"
						style="width: 100%;" />
				</div>
				<!--ComboBox：本地数据 状态-->
				<div field="u_istrans" width="100" align="center"
					headerAlign="center" renderer="onIsTranRenderer">
					是否与数据库同步 <input property="editor" class="mini-Hidden"
						style="width: 100%;" data="IsTranses" />
				</div>
				<!--ComboBox：远程数据
            <div type="comboboxcolumn" field="country" width="100" headerAlign="center" >国家
                <input property="editor" class="mini-combobox" style="width:100%;" url="../data/countrys.txt" />                
            </div>   
            <div type="checkboxcolumn" field="married" trueValue="1" falseValue="0" width="60" headerAlign="center">婚否</div>   -->
			</div>
		</div>
	</div>


	<script type="text/javascript">
		//性别
		var Sexes = [ {
			id : 0,
			text : '男'
		}, {
			id : 1,
			text : '女'
		} ];
		/*-1：未注册 0：删除 1：正常 2：禁用*/
		var Status = [ {
			id : -1,
			text : '未注册'
		}, {
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

		
		function onStatusRenderer(e) {
            for (var i = 0, l = Status.length; i < l; i++) {
                var g = Status[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
				
		function onSexRenderer(e) {
            for (var i = 0, l = Sexes.length; i < l; i++) {
                var g = Sexes[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
		
		function onIsTranRenderer(e) {
            for (var i = 0, l = IsTranses.length; i < l; i++) {
                var g = IsTranses[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
		
		//////////////////////////////////////////////////////

		function search() {
			var userid = mini.get("userId").getValue();
			var userName = mini.get("userName").getValue();
			var userPhone = mini.get("userPhone").getValue();
			  
			var updatetime = mini.get("updatetime").getFormValue();
			grid.load({
				userid : userid,
				userName : userName,
				userPhone : userPhone,
				updatetime:updatetime
			});
		}

		function onKeyEnter(e) {
			search();
		}

		function addRow() {
			mini.open({
				url : bootPATH + "../user/addUser",
				title : "添加新用户",
				width : 850,
				height : 450
			});
		}
		function removeRow() {
			var rows = grid.getSelecteds();
			if (rows == 0) {
				alert("至少要选择一条记录");
			} else {
				if (confirm("确定删除选中的数据吗?")) {
					if (rows.length > 0) {
						grid.removeRows(rows, false);
						saveData();
					}
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

		function exportSelections() {

			var data = grid.getSelecteds();
			
			var userid = mini.get("userId").getValue();
			var userName = mini.get("userName").getValue();
			var userPhone = mini.get("userPhone").getValue();
			
			
			var json = mini.encode(data);
	
				$.ajax({
					url : "../user/exportUserList",
					data : {
						data : json,
						userid : userid,
						userName : userName,
						userPhone : userPhone
					},
					type : "post",
					success : function(text) {
						if(text == "1"){
							window.parent.location.href="../download?filepath=workbook.xls";
						}
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