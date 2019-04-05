 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration Form</title>
<h1>REGISTRATION FORM FOR NEW USER</h1>
<form action="register" method="post">
	<table style="with: 50%">
		<tr>
			<td>First Name</td>
			<td><input type="text" name="first_name"  pattern="[a-zA-Z]+"/></td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td><input type="text" name="last_name" pattern="[a-zA-Z]+" /></td>
		</tr>
		<tr>
			<td>UserName</td>
			<td><input type="email" name="username" /></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"  title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"/></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><input type="text" name="address"  pattern="[a-zA-Z]+"/></td>
		</tr>
		<tr>
			<td>Contact No</td>
			<td><input type="text" name="contact" pattern="[789][0-9]{9}"/></td>
		</tr>
	</table>
	<input type="submit" value="Submit" />
	<a href="login.jsp">Login</a>
</form>
</body>
</html>
