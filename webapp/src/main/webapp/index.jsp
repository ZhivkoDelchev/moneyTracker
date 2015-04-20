<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <header>
        <link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/resources/css/main.css">
        <style>#main{ background-color: #ffffff }</style>
        <script src="<%=request.getContextPath() %>/resources/main.js"></script>
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
    </header>

    <body class=" ">
		<div class="overlay"></div>
        <form id="header_form">
            <fieldset>
                <div id="nav_top">
                    <ul id="header">
                    </ul>
                </div>
                <div id="body"></div>
            </fieldset>
        </form>
        <script>
            initializeNavigation()
			createHomeBody()
        </script>
    </body>
</html>

