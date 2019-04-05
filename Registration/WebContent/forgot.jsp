

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot</title>
</head>
<body>
	<h1>forgot Password</h1>
	<form action="forgot" method="post" >
	<table style="with: 50%">
		<tr>
			<td>UserName</td>
			<td><input type="email" name="username" /></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
				title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" /></td>
		</tr>
		
		<tr>
			<td>Retype-Password</td>
			<td><input type="password" name="Retype-password"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
				title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" /></td>
		</tr>
		<tr><td><input type="submit" value="forgot" /></td></tr>
		</table>
	</form>
</body>
</html>

  