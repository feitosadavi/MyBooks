<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@page import="model.MenuDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Menu"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="../componentes/head.jsp"%>

<body>
<div class="container">
  <%@include file="../componentes/navbar.jsp"%>

  <div class="row mt-4">
    <div class="col-sm-2"></div>

    <div id="referencia" class="col-sm-8">
    <%@include file="/src/componentes/mensagem.jsp"%>

      <h2>Lista de Usuário</h2>
      <a href="cadastrar-usuario.jsp" class="btn btn-primary">Novo Usuário</a>

      <jsp:useBean class="model.UsuarioDAO" id="usuarioDAO"/>
      <c:forEach var="usuario" items="${usuarioDAO.list}">
        <div class="card mt-3 h-10">
          <div class="card-horizontal">
            <div class="img-square-wrapper">
              <img class="img-thumbnail img-fluid w-25 h-100" src="${pageContext.request.contextPath}/imagens/${usuario.capa}" alt="Card image cap">
            </div>
            <div class="card-body">
              <h4 class="card-title">${usuario.nome}</h4>
              <p class="card-text">id: ${usuario.id}</p>
              <p class="card-text">matrícula: ${usuario.matricula}</p>
              <p class="card-text">status: ${usuario.status == 1 ? "Ativo" : "Inativo"}</p>
              <p class="card-text">Perfil: ${usuario.perfil.nome}</p>
            </div>
          </div>
          <div class="card-footer">
            <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_usuario.do?acao=alterar&id=${usuario.id}">
              <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
            </a>

            <button class="btn btn-outline-danger" onclick="confirmarExclusao('${usuario.nome}', '/projetojava3_war_exploded/gerenciar_usuario.do?acao=deletar&id='+'${usuario.id}')">
              <img src="${pageContext.request.contextPath}/imagens/lixeira.svg" alt="lixeira dentro de um quadrado vermelho">
            </button>
          </div>
        </div>
      </c:forEach>
      

    </div>
  </div>
  <div class="col-sm-2"></div>
</div>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
