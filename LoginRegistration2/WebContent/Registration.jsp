<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration of New User</title>
</head>
<body style="background-color: white">
<h1>Registration Page</h1>
	<form style="text-align: center" action="login" method="post">

		<table style="background-color: skyblue">
			
		<tr>
				<td>FirstName:<input type="text" name="FirstName" /></td>
			</tr>
			<tr>
				<td>LastName:<input type="text" name="LastName" /></td>
				
			</tr> 
			<tr>
				<td>Username:<input type="email" name="Username" /></td>
			</tr>
			<tr>
				<td>Email-id:<input type="email" name="Emailid" /></td>
			</tr>

			<tr>
				<td>Password:<input type="password" name="UserPass" /></td>
			</tr>
			<tr>
				<td>Retype-Password:<input type="password" name="ReuserPass" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Signup" /></td>
				<td><a href="login.jsp">login</a></td>
			</tr>
			

		</table>
	</form>
</body>
</html>