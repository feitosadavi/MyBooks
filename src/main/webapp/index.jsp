<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@include file="src/componentes/head.jsp"%>
  <body>
    <%@include file="/src/componentes/navbar.jsp"%>
    <div class="container">
      <%@include file="/src/componentes/mensagem.jsp"%>

		  <%
			  java.util.Date data = new java.util.Date();
	      out.print(data.getDate());
		  
		  %>
      <h2>
      
      
      
      </h2>
      <iframe width="560" height="315" src="https://www.youtube.com/embed/se1rkJFQGcA?start=1117&end=1227&autoplay=1&controls=0" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </div>
  </body>
</html>
