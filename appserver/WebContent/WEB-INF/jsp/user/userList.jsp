<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="../demo.css" rel="stylesheet" type="text/css" />

<script src="../scripts/boot.js" type="text/javascript"></script>
<script src="../scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
<link href="../scripts/miniui/themes/blue/skin.css" rel="stylesheet"
	type="text/css" />
  <link href="../demo.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>   
    
</head>
<body>
	<div class="mini-splitter" style="width: 100%; height: 100%;">
		<div size="240" showCollapseButton="true">
			<div class="mini-toolbar"
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<span style="padding-left: 5px;">名称：</span> <input
					class="mini-textbox" /> <a class="mini-button"
					iconCls="icon-search" plain="true">查找</a>
			</div>
			<div class="mini-fit">
				<ul id="tree1" class="mini-tree" url="../scripts/Tree.txt"
					style="width: 100%;" showTreeIcon="true" textField="name"
					idField="id" parentField="pid" resultAsTree="false">
				</ul>
			</div>
		</div>
		
		<div showCollapseButton="true">
			<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;"><a class="mini-button"
							iconCls="icon-add" onclick="addRow()" plain="true">增加</a><span
							class="separator"></span><a class="mini-button"
							iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a> <span
							class="separator"></span> <a class="mini-button"
							iconCls="icon-save" onclick="saveData()" plain="true">保存</a></td>
						<td style="white-space: nowrap;"><input id="key"
							class="mini-textbox" emptyText="请输入姓名/手机号/userid/imei码"
							style="width: 250px;" onenter="onKeyEnter" /> <a
							class="mini-button" onclick="search()">123</a></td>
					</tr>
				</table>
			</div>

			<div class="mini-fit">
			
				<div id="datagrid1" class="mini-datagrid"
					style="width: 100%; height: 100%;" url="../user/loadUserList"
					idField="u_id" allowResize="false" pageSize="20"
					allowCellEdit="true" allowCellSelect="true" multiSelect="true"
					editNextOnEnterKey="true" editNextRowCell="true">
					<div property="columns">
						<div type="indexcolumn"></div>
						<div type="checkcolumn"></div>
						<div name="u_id" field="u_id" headerAlign="center"
							allowSort="false" width="150" style="display: none;">
							用户主键 <input property="editor" class="mini-Hidden"
								style="width: 100%;" minWidth="200" />
						</div>
						<div name="u_phoneimei" field="u_phoneimei" headerAlign="center"
							allowSort="false" width="150">
							手机IMEI码 <input property="editor" class="mini-textbox"
								style="width: 100%;" minWidth="200" />
						</div>
						<div name="u_name" field="u_name" headerAlign="center"
							allowSort="true" width="150">
							用户姓名 <input property="editor" class="mini-textbox"
								style="width: 100%;" minWidth="200" />
						</div>
						<div name="u_privatepassword" field="u_privatepassword"
							headerAlign="center" allowSort="false" width="150">
							用户密码 <input property="editor" class="mini-textbox"
								style="width: 100%;" minWidth="200" />
						</div>
						<div field="u_phone" width="100" allowSort="false">
							手机号 <input property="editor" class="mini-textbox" value="25"
								style="width: 100%;" />
						</div>
						<div name="u_birthdate" field="u_birthdate" width="100"
							allowSort="false" dateFormat="yyyy-MM-dd">
							出生日期 <input property="editor" class="mini-datepicker"
								style="width: 100%;" />
						</div>
						<div name="u_regtime" field="u_regtime" width="100"
							allowSort="false" dateFormat="yyyy-MM-dd">
							注册日期 <input property="editor" class="mini-datepicker"
								style="width: 100%;" />
						</div>
						<div name="u_lastupdatetime" field="u_lastupdatetime" width="100"
							allowSort="false" dateFormat="yyyy-MM-dd">
							最后修改时间 <input property="editor" class="mini-datepicker"
								style="width: 100%;" />
						</div>
						<div field="u_profession" width="120" headerAlign="center"
							allowSort="false">
							邮箱 <input property="editor" class="mini-textarea"
								style="width: 200px;" minWidth="200" minHeight="50" />
						</div>
						<!--ComboBox：本地数据-->
						<div type="comboboxcolumn" autoShowPopup="true" name="u_sex"
							field="u_sex" width="100" allowSort="false" align="center"
							headerAlign="center">
							性别 <input property="editor" class="mini-combobox"
								style="width: 100%;" data=Sexes />
						</div>
						<!--ComboBox：本地数据 状态-->
						<div type="comboboxcolumn" autoShowPopup="true" name="u_status"
							field="u_status" width="100" allowSort="false" align="center"
							headerAlign="center">
							账户状态 <input property="editor" class="mini-combobox"
								style="width: 100%;" data="Status" />
						</div>
						<!--ComboBox：本地数据 状态-->
						<div type="comboboxcolumn" autoShowPopup="true" name="u_istrans"
							field="u_istrans" width="100" allowSort="false" align="center"
							headerAlign="center">
							是否与数据库同步 <input property="editor" class="mini-combobox"
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
		</div>
	
	
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