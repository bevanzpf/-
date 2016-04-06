<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<%Object before_filter = session.getAttribute("before_filter");
	if(before_filter != null){
		session.removeAttribute("before_filter");
		response.sendRedirect(before_filter.toString()+ "?from_login=true");
	}
%>
<% String root = request.getContextPath();
   Object user = session.getAttribute("user");
   String email = null;
   if(user!=null)
	   email = user.toString();
%>
<body>

	<h1> welcome:</h1> <h2><%=session.getAttribute("user")%></h2>
	<span><a href="<%=root%>/Login_controller?action=logout&email=<%=email%>">注销登陆</a></span>
		
		
</body>
</html>