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

<%@include file="../componentes/navbarSemVerificacao.jsp"%>

<div class="container">
  <div class="row">
    <h3 class="mt-5 mb-3">Login</h3>
    
    <div class="col-sm-4"></div>
    <div id="referencia" class="col-sm-4">
    <%@include file="/src/componentes/mensagem.jsp"%>

      <form action="${pageContext.request.contextPath}/gerenciar_login.do" method="POST">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control mb-3" id="email" name="email" required>

        <label for="senha" class="form-label">Senha</label>
        <input type="password" class="form-control mb-3" id="senha" name="senha" required>
        
        <p class="text">
          Não possui uma conta? 
          <a href="${pageContext.request.contextPath}/src/usuarios/cadastrar-usuario.jsp">Cadastre-se</a>
        </p>
        
        <button type="submit" class="btn btn-primary">Entrar</button>
      </form>
      
    </div>
    <div class="col-sm-4"></div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

</body>
</html>
