<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="../../resource/bootstrap/js/jquery-3.1.1.js"></script>
  </head>
  
  <body>
    <form action=http://localhost:8080/SSM/user/login id="dialog-login">
 		<table>
 			
 			<tr>
 				<td>username:</td>
 				<td>
 					<input type="text" name="username"/>
 				</td>
 			</tr>
 			<tr>
 				<td>password:</td>
 				<td>
 					<input type="password" name="password"/>
 				</td>
 			</tr>
 			
 			<tr><input type="submit" name="登录"></tr>
 		</table>	   
    </form>
  </body>
</html>
