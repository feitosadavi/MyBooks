<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 29/04/2021
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="/src/componentes/head.jsp" %>
<%@include file="/src/componentes/navbar.jsp" %>

<body>
  <div class="container">
    <div class="foto-perfil">
      <img src="${pageContext.request.contextPath}/imagens/${requestScope.ulogado.capa}" alt="">
    </div>
    <h3>${requestScope.ulogado.nome}</h3>
    <p>${requestScope.ulogado.email}</p>
    <p>${requestScope.ulogado.matricula}</p>
    <p>${requestScope.ulogado.perfil.nome}</p>
  </div>
</body>
</html>
