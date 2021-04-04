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
  <a href="${pageContext.request.contextPath}/src/perfil/listar-perfil.jsp">
    <img src="${pageContext.request.contextPath}/imagens/voltar.svg" alt="seta de voltar">
  </a>

  <div class="ms-auto me-auto mt-5">
    <h3 class="mt-5 mb-3">Atualizar Usuário</h3>
    <form action="${pageContext.request.contextPath}/gerenciar_usuario.do" method="POST">

      <div class="form-group">
        <input value="${sessionScope.usuario.id}" type="text" class="form-control" name="id" hidden>

        <label class="mt-2" for="nome">Nome: </label>
        <input value="${sessionScope.usuario.nome}" type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do menu">

        <label class="mt-2" for="username">Username: </label>
        <input value="${sessionScope.usuario.username}" type="text" class="form-control" id="username" name="username" placeholder="insira o username">

        <label class="mt-2" for="senha">Senha: </label>
        <input value="${sessionScope.usuario.senha}" type="password" class="form-control" id="senha" name="senha" placeholder="insira a senha">

        <label class="mt-2" for="status">Status: </label>
        <select class="form-select" name="status" id="status">
          <option value="1" ${sessionScope.usuario.status == "1" && "selected"}>Ativo</option>
          <option value="0" ${sessionScope.usuario.status == "0" && "selected"}>Inativo</option>
        </select>
        
        <label class="mt-2" for="idPerfil">Perfil: </label>
        <select id="idPerfil" class="form-select" name="idPerfil">
          <jsp:useBean class="model.PerfilDAO" id="perfilDAO"/>
          <c:forEach var="perfilDAO" items="${perfilDAO.list}">
            <option value="${perfilDAO.id}" ${sessionScope.usuario.idPerfil == perfilDAO.id ? "selected" : null }>${perfilDAO.nome}</option>
          </c:forEach>
        </select>
      </div>

      <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
    </form>

  </div>

</div>
</body>
</html>
