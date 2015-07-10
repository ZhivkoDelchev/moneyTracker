<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Bar</title>
	<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/resources/css/main.css">
	<style>#login{ background-color: #ffffff }</style>
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