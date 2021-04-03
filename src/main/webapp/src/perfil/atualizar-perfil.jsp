<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@page import="model.PerfilDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Perfil"%>
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
    <h3 class="mt-5 mb-3">Atualizar</h3>
    <form action="${pageContext.request.contextPath}/gerenciar_perfil.do" method="POST">
      
      <div class="form-group">
        <input value="${sessionScope.id}" type="text" class="form-control" name="id" hidden>

        <label class="mt-2" for="nome">Nome: </label>
        <input value="${sessionScope.nome}" type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do perfil" required>
      </div>

      <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
    </form>

  </div>

</div>
</body>
</html>
