<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

  </head>
  
  <body>
    <form action=http://localhost:8080/SSM/user/regist method="post">
 		<table>
 			<tr>
 				<td>name:</td>
 				<td>
 					<input type="text" name="name"/>
 				</td>
 			</tr>
 			<tr>
 				<td>gender:</td>
 				<td>
 					<input type="text" name="gender"/>
 				</td>
 			</tr>
 			<tr>
 				<td>age:</td>
 				<td>
 					<input type="text" name="age"/>
 				</td>
 			</tr>
 			</>
 			
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
 			<tr>
 				<td>password2:</td>
 				<td>
 					<input type="password" name="password2"/>
 				</td>
 			</tr>
 			<tr>
 				<td>mail:</td>
 				<td>
 					<input type="text" name="mail"/>
 				</td>
 			</tr>
 			<tr>
 				<td>phonenum:</td>
 				<td>
 					<input type="text" name="phonenum"/>
 				</td>
 			</tr>
 			<tr><input type="submit"></tr>
 		</table>	   
    </form>
  </body>
</html>
