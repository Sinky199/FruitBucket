<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
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
     <table border="1">
         <thead>
             <tr>
                 <th>姓名</th>
                <th>年龄</th>
             </tr>
		</thead>
		<tbody>
             <c:if test="${!empty userList}">
                <c:forEach items="${userList}" var="user">
                     <tr>
                         <td>${user.name}</td>
                         <td>${user.age}</td>
                        
                     </tr>                
                </c:forEach>
            </c:if>
        </tbody>
     </table>
  </body>
</html>
