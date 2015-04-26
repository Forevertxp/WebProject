<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统主界面</title>
<script src="<%=request.getContextPath()%>/scripts/boot.js"
	type="text/javascript"></script>
	
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }        
    </style> 
    
    <script type="text/javascript"> 
	
	
	//iframe.src = "../datagrid/datagrid.html#1"
	
	function onItemClick(e) {
		
		var iframe = document.getElementById("mainframe");
	    var item = e.item;
	 
	    iframe.src = item.url;
	    var leftTree = mini.get("leftTree");     
	    var node=leftTree.getSelected();
	    var href=window.location.href
	    
	    var index=href.indexOf('?');
	    if(index!=-1){
	    	 var mianHref=href.substring(0,index);
	    	 mianHref=mianHref+'?id='+node.id
	    	 window.location.href=mianHref;
	    }else{
	    	 href=href+'?id='+node.id
	    	 window.location.href=href;
	    }
	}

	</script>
	   
	
</head>
<body>
	<div id="mainLayout" class="mini-layout"
		style="width: 100%; height: 100%;">

		<div class="header" region="north" height="55" showSplit="false"
			showHeader="false"
			bodyStyle="background:url(../images/bg1.png);background-repeat:repeat-x">
			<h1
				style="float: left; margin: 0; padding: 15px; cursor: default; font-family: 'Trebuchet MS', Arial, sans-serif;">手机APP管理后台</h1>

			<div style="float: right;">
				<div style="float: right;">
					<a href="javascript:void(0);" onclick="logout()"><h2>【注销】</h2></a>
				</div>
			</div>

		</div>

		<div title="south" region="south" showSplit="false" showHeader="false"
			height="30">
			<div style="background:green;line-height: 28px; text-align: center; cursor: default">Copyright
				© 田晓鹏</div>
		</div>

		<div showHeader="false" region="west" width="180" maxWidth="250"
			minWidth="100">
			<!--OutlookMenu-->
			<div id="leftTree" class="mini-outlookmenu"
				url="<%=request.getContextPath()%>/scripts/menuData.txt"
				onitemclick="onItemClick" idField="id" parentField="pid"
				textField="text" borderStyle="border:0"></div>

		</div>

		<div title="center" region="center" bodyStyle="overflow:hidden;">
			<iframe id="mainframe" frameborder="0" name="main" src="../user/query"
				style="width: 100%; height: 100%;" border="0"></iframe>
		</div>
	</div>
	<!-- <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/index.js"></script> -->
<script type="text/javascript">
$(function(){
	var mainHref=window.location.href;
	var index=mainHref.indexOf('?');
	if(index!=-1){
		 var nodeIdStr=mainHref.substring(index+1);
		 var leftTree=mini.get("leftTree");   
		 var indexMain=nodeIdStr.indexOf('=')
		 var nodeIdMain=nodeIdStr.substring(indexMain+1);
		 var nodes = leftTree.findNodes(function(node){
			 if(node.id==nodeIdMain){
				 return true; 
			 }
			   // if(node.name.indexOf("abc") != -1) return true;
		 });
		 if(nodes.length==1){
			 leftTree.selectNode(nodes[0])
			 var iframe = document.getElementById("mainframe");
			iframe.src = nodes[0].url;
		 }
		
		/*  var nodeMain=leftTree.findNodes(function(node){
				 return true;
		 })
		 alert(nodeMain)
		 leftTree.selectNode(nodeMain) */
	}
});

function logout() {

	$.ajax({
		url : "../user/userLogout",
		type : "get",
		success : function(text) {
			if (text == "1") {
				window.location = "../";
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {

			alert(jqXHR.responseText);
		}
	});
}

</script>
</body>
</html>