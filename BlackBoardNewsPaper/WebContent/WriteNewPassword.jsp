<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<%if(request.getAttribute("message")!= null) {%>
<h3><%= request.getAttribute("message") %></h3>
<%} %>
<% String email = request.getParameter("email");
   String validateCode= request.getParameter("validateCode");
%>
<body>
<h3>填写你的新密码</h3>
<form action="/BlackBoardNewsPaper/ResetPassword_controller?step=validateReset" method="post">
<input type ="hidden" value=<%=email%> name="email">
<input type = "hidden" value =<%=validateCode%> name="validateCode">
<input type = "password" name= "newPassword"><br>
<input type="submit" value="确定">
</form>
</body>
</html>