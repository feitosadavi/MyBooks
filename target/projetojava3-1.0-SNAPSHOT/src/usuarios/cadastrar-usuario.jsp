<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<%@include file="../componentes/head.jsp"%>
<body>
<div class="container row-cols-sm-4">
  <%@include file="../componentes/navbar.jsp"%>
  <a href="${pageContext.request.contextPath}/src/menus/listar-menu.jsp">
    <img src="${pageContext.request.contextPath}/imagens/voltar.svg" alt="seta de voltar">
  </a>

  <div class="ms-auto me-auto mt-5">
    <c:if test="${sessionScope.mensagem != null}">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>${sessionScope.mensagem}</strong>
        <%session.invalidate();%>
        <button class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    </c:if>
    <h3 class="mt-5 mb-3">Novo Usuário</h3>
    <form action="${pageContext.request.contextPath}/gerenciar_usuario.do" method="POST">
      <div class="form-group">
        <input type="text" class="form-control" name="id" hidden>

        <label class="mt-2" for="nome">Nome: </label>
        <input type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do menu">

        <label class="mt-2" for="username">Username: </label>
        <input type="text" class="form-control" id="username" name="username" placeholder="insira o username">

        <label class="mt-2" for="senha">Senha: </label>
        <input type="password" class="form-control" id="senha" name="senha" placeholder="insira a senha">

        <label class="mt-2" for="status">Status: </label>
        <select class="form-select" name="status" id="status">
          <option value="1">Ativo</option>
          <option value="0">Inativo</option>
        </select>
        
        <label class="mt-2" for="idPerfil">Perfil: </label>
        <select id="idPerfil" class="form-select" name="idPerfil">
          <jsp:useBean class="model.PerfilDAO" id="perfilDAO"/>
          <c:forEach var="perfilDAO" items="${perfilDAO.list}">
            <option value="${perfilDAO.id}">${perfilDAO.nome}</option>
          </c:forEach>
        </select>
      </div>

      <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
    </form>
  </div>

</div>
</body>
</html>
