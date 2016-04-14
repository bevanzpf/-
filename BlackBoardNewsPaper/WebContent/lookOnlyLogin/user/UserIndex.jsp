<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="model.User.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<% String root = request.getContextPath(); %>
<% User user =(User) session.getAttribute("user"); %>

<% if (session.getAttribute("message")!= null){ %>
	*error:<%=session.getAttribute("message") %>
	<%session.removeAttribute("message");%>
<%} %>
<h3>你的信息：</h3>
<img alt="" src="<%=root%>/upload/<%=user.getIcon() %>" height="70" width="70">
<form action="/BlackBoardNewsPaper/FileUpload_controller?action=uploadIcon" enctype="multipart/form-data" method ="post">
<input type="file" name="icon">
<input type="submit" value ="提交">
</form>
<form action="/BlackBoardNewsPaper/User_controller?action=updateProfile" method="post">
昵称：
<input type="text" value="<%=user.getName() %>" name="name"><br><br>

学校：
<input type="text" value="<%=user.getSchool() %>" name="school"><br><br>
年级：
<input type="text" value="<%=user.getGrade()%>" name="grade"><br>个性签名：<br>

<textarea rows="4" cols="36" maxlength="144" name="info"  ><%=user.getInfo()%></textarea><br><br>
<input type="submit" value="保存">
</form>
</body>
</html>