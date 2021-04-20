<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 14/04/2021
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="../componentes/head.jsp"%>

<body>

<nav class="navbar navbar-expand-md navbar-light bg-light">
  <div class="container">
    <a href="#" class="navbar-brand mb-2 mb-lg-0">Projeto</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">

      <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
    </div>
  </div>
</nav>

<div class="container">
  <div class="row">
    <h3 class="mt-5 mb-3">Login</h3>
    
    <div class="col-sm-4"></div>
    <div id="referencia" class="col-sm-4">
    <%@include file="/src/componentes/mensagem.jsp"%>

      <form action="${pageContext.request.contextPath}/gerenciar_login.do" method="POST">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control mb-3" id="username" name="username" required>

        <label for="senha" class="form-label">Senha</label>
        <input type="password" class="form-control mb-3" id="senha" name="senha" required>
        
        <button type="submit" class="btn btn-primary">Entrar</button>
      </form>
      
    </div>
    <div class="col-sm-4"></div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

</body>
</html>
