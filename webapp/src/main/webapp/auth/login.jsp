<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Bar</title>
	<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/resources/css/main.css">
	<style>#login{ background-color: #ffffff }</style>
<head/>
<body class=" ">
	<form id="login" action="j_security_check" method="post">
		<legend>Login</legend>
		<h1 style="margin-bottom: 30px;">Login</h1>

		<label>Email:</label>
		<input type = "text"name = "j_username"  class="textInput"/>

		<label>Password:</label>
		<input type = "password"name = "j_password"  class="textInput"/>

		<input type="submit" id="loginButton" value="Login" class="button"/>
	</form>
</body>
</html>