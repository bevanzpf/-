<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%if(session.getAttribute("message")!= null) {%>
<h3><%= session.getAttribute("message") %></h3>
<% session.removeAttribute("message"); %>
<%} %>
<form action="/BlackBoardNewsPaper/ResetPassword_controller?step=sendResetEmail" method ="post">
	<h3>密码重置</h3>
	email:
	<input type="text" name = "email">
	<input type = "submit" value="提交">
</form>
</body>
</html>