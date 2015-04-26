<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>咨询回复列表</title>

<script type="text/javascript" src="../scripts/boot.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.6.2.min.js"></script>
<!--MiniUI-->
<link href="../scripts/miniui/themes/default/miniui.css"
	rel="stylesheet" type="text/css" />
<script src="../scripts/miniui.js" type="text/javascript"></script>
<!-- <script src="http://ckeditor.com/apps/ckeditor/4.3.1/ckeditor.js"
	type="text/javascript"></script> -->
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}

.tabMainConsult {
	
	border:solid 1px gray;
}
.maintd td
{
	background-color: #a4d6e8;
}

.tabReplyConsult {
	
	border:solid 1px gray;
}
.replytd td
{
	background-color: #eff2fe;
}
</style>
</head>
<body>
	<div class="mini-fit">
		<table width="95%" cellpadding="5" cellspacing="0">
			<tr>
				<!-- 咨询主题信息 -->
				<td width="120px" align="right" >
					<!-- 信息填写人 -->咨询人: <span>${mainConsult.u_name }</span>
				</td>
				<td>
					<table width="100%" cellpadding="5" cellspacing="0" border="0"
						class="tabMainConsult">
						<tr class='maintd'>
							<td>主题:<span>${mainConsult.c_title }</span> <input
								type="hidden" id="hdmid" value="${mainConsult.c_id }" /></td>
							<td align="right">咨询时间: <fmt:formatDate
									value="${ mainConsult.c_createtime }" type="both" />
								${mainConsult.c_createtime}
							</td>
						</tr>
						<tr>
							<td colspan="2"><span>${mainConsult.c_content }</span></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 咨询回复循环 -->
			<c:forEach items="${data}" var="consult">
				<tr>
					<td align="right" >
						<!-- 信息填写人 --> 答复用户:<span>${consult.u_name }</span>
					</td>
					<td>
						<table width="100%" cellpadding="5" cellspacing="0" border="0"
							class="tabReplyConsult">
							<tr class='replytd'>
								<td><span>${consult.c_title }</span></td>
								<td align="right">答复时间:  
									${consult.c_createtime}

								</td>
							</tr>

							<tr>
								<td colspan="2"><span>${consult.c_content }</span></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div id="form1">
			<table width="95%" align="center">
				<tr>
					<td>新增回复</td>
				</tr>
				<tr>
					<td>标题:<input type="text" id="replyTitle" name="title"
						style="width: 95%;" maxlength="50" /></td>
				</tr>

				<tr>
					<td><textarea id="replyContent" name="content" style="width:95%; height:150px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="button" value="回复"
						onclick="doReply()"></td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		// var editorId = "editor1";
		//var editor = CKEDITOR.replace('replyContent');
		var editor = $("#replyContent");
		function doReply() {
			var title = $("#replyTitle").val();
			if (title.length > 50) {
				alert("标题过长，最多支持50个字符.目前字符为" + title.length + "个.");
				return false;
			}

			var text = editor.val();//editor.getData();
			if (text.length > 4000) {
				alert("内容过长，最多支持4000个字符.目前字符为" + text.length + "个.");
				return false;
			}

			if (text != "" && title != "") {

				//grid.loading("操作提交中，请稍后......");
				$.ajax({
					url : "../consult/submitConsultReply",
					data : {
						content : text,
						title : title,
						mid : $("#hdmid").val()
					},
					type : "post",
					success : function(text) {
						alert(text);
						$("#replyTitle").val("");
						//editor.setData("");
						editor.val("");
						window.location.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(jqXHR.responseText);
					}
				});

			} else {
				alert("回复内容及标题不能为空.");
			}

		}
	</script>
</body>
</html>