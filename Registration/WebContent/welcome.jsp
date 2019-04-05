<!DOCTYPE html>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to </title>
</head>
<h1>Welcome to our site</h1>
<body>
	<% String username = request.getParameter("username"); %>
<a>Welcome   <% out.println(username); %> User!!!! You have logged in.</a>
<br>
 
         <h2>Display Current Date & Time</h2>
     
      <%
         Date date = new Date();
         out.print( "<h2 align = \"center\">" +date.toString()+"</h2>");
      %>
<a href ="login.jsp">Logout</a>
</body>
</html>