<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<%if(session.getAttribute("message") !=null){ %>
	<h6><%=session.getAttribute("message") %></h6>
	<% session.removeAttribute("message"); %>
	<%} %>
	<h2>登录</h2>
	<form action="Login_controller" method="post">
	email:<input type="text" name ="email"><br>
	Password:<input type="password" name = "password"><br>
	<input type="checkbox" value="1" name="remember_me" checked="checked">
	<input type="submit" value="login">
	<span><a href="/BlackBoardNewsPaper/ResetPassword.jsp">忘记密码</a></span>
	</form>
</body>
</html>