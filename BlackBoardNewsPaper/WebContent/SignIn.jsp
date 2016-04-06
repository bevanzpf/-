<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="Utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%if(request.getAttribute("message") !=null){ %>
<h4><%=request.getAttribute("message") %><a href="Login.jsp">请登录</a></h4>
<%} %>
<h2>注册</h2>
<form action="SignIn_controller?action=register" method="post">
Email:
<input type="text" name="email"><br>
Password:
<input type="password" name="password"><br>

<input type="submit">
</form>
</body>
</html>