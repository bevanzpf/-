<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="model.User.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%Object before_filter = session.getAttribute("before_filter");
	if(before_filter != null){
		session.removeAttribute("before_filter");
		response.sendRedirect(before_filter.toString()+ "?from_login=true");
	}
%>
<h1>一起来出黑板报</h1>
<%User user =(User)session.getAttribute("user"); %>
<% String root = request.getContextPath(); %>
<% if(user != null) {%>
<span><a href="<%=root%>/a/user/index.jsp">我的质料</a></span>
<span><a href="<%=root%>/Login_controller?action=logout&email=<%=user.getEmail()%>">注销登陆</a></span>
<a herf="<%=root%>/a/user/upload">上传</a>
<%} else{%>
<span><a href="<%=root %>/Login.jsp">登录</a></span>
<Span><a href="<%=root %>/SignUp.jsp">注册</a></Span>
<%} %>
</body>
</html>