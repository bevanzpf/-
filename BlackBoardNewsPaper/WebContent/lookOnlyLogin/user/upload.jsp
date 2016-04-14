<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传作品</title>
</head>
<body>
<% Object message = session.getAttribute("message"); %>
<% String root = request.getContextPath(); %>
<%if(message != null){ %>
	<h4>*error:<%=message%></h4>
	<% session.removeAttribute("message");%>
<%} %>
<form action="<%=root%>/FileUpload_controller?action=uploadWork" enctype="multipart/form-data" method ="post">
请选择文件：<br>
<input type="file" name="fileName"><br><br>
主题：<br>
<input type="text" name="subject"><br><br>
描述：<br>
<textarea name="info" rows="20" cols="68">介绍一下你的作品吧...</textarea> <br><br><br>
<input type="submit" value="提交">
</form>

</body>
</html>