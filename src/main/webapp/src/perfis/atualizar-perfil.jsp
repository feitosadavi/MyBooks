<%@ page import="model.MenuDAO" %>
<%@ page import="model.Menu" %>
<%@ page import="java.util.ArrayList" %><%-- 
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
<%@include file="../componentes/navbar.jsp"%>
<div class="container row-cols-sm-4">
  <a href="${pageContext.request.contextPath}/src/perfis/listar-perfil.jsp">
    <img src="${pageContext.request.contextPath}/imagens/voltar.svg" alt="seta de voltar">
  </a>

  <div class="ms-auto me-auto mt-5">
    <h3 class="mt-5 mb-3">Atualizar</h3>
    <form action="${pageContext.request.contextPath}/gerenciar_perfil.do" onsubmit="checarCheckboxes()" method="POST">
      <div class="form-group" onsubmit="checarCheckboxes()">
        <input value="${sessionScope.perfil.id}" type="text" class="form-control" name="id" hidden>

        <label class="mt-2" for="nome">Nome: </label>
        <input value="${sessionScope.perfil.nome}" type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do perfil" required>

        <jsp:useBean class="model.MenuDAO" id="menuDAO"/>
        <c:set var="i" value="0"/>
        <c:set var="menuGravadoArray" value="${menuDAO.list}" />
        <c:forEach var="menuGravado" items="${menuGravadoArray}">
          <div class="form-check">
            <label for="${menuGravado.nome}" class="form-check-label">${menuGravado.nome}</label>

            <input type="checkbox" class="form-check-input menus" 
                   value="${menuGravado.id}" id="${menuGravado.nome}" 
                   name="${menuGravado.nome}"
                   ${sessionScope.idsMenus.contains(menuGravado.id) ? "checked" : ""}
            >
          </div>
          <c:set var="i" value="${i+1}"/>
        </c:forEach>
      </div>
      <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
    </form>
  </div>
</div>

<script>
  function checarCheckboxes() {
    let checkboxes = document.getElementsByClassName("menus");
    for (let checkbox of checkboxes) {
      if (checkbox.checked !== true) {
        checkbox.checked = true; // preciso colocar o checkbox como true para enviar os dados para o controlador
        checkbox.value += '#desvincular'
      }  
    }
  }
</script>

</body>
</html>
