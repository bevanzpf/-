<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<% String from_login = request.getParameter("from_login");//在welcome.jsp 转发时附加该属性。
	if(from_login != null)
		response.sendRedirect("/BlackBoardNewsPaper/index.jsp");
%>
<h1>404页面</h1>
<h4>抱歉，您访问的页面不存在！</h4><br>
<h6><a href ="/BlackBoardNewsPaper/index.jsp">返回欢迎页面</a></h6>
</body>
</html>