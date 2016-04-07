<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SignIn success</title>
</head>
<body>
<h3> 注册成功，请登录注册所用邮箱确认注册</h3>
<br>
<% if(session.getAttribute("email") != null){ %>
<a href="SignUp_controller?action=reSendEmail&email=<%= session.getAttribute("email").toString() %>"><button>重发邮件</button></a>
<%session.removeAttribute("email");%>
<%} %>
</body>
</html>