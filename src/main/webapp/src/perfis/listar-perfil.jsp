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
<html>
  <%@include file="../componentes/head.jsp"%>
  
  <body>
    <div class="container">
      <%@include file="../componentes/navbar.jsp"%>

      <div class="row mt-4">
        <div class="col-sm-2"></div>
        
        <div id="referencia" class="col-sm-8">
          <c:if test="${sessionScope.mensagem != null}">
            <div class="alert ${sessionScope.mensagem != "Gravado com sucesso" ? "alert-danger" : "alert-success"} alert-dismissible fade show" role="alert">
              <strong>${sessionScope.mensagem}</strong>
              <%session.invalidate();%>
              <button class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
          </c:if>

          <h2>Lista de Perfis</h2>
          <a href="cadastrar-perfil.jsp" class="btn btn-primary">Novo Cadastro</a>
          
          <table class="table table-hover table-striped table-bordered display mt-4 text-center">
            <thead>
            <tr>
              <th scope="col">id</th>
              <th scope="col">nome</th>
              <th scope="col">menus vinculados</th>
              <th scope="col">opções</th>
            </tr>
            </thead>

            <jsp:useBean class="model.PerfilDAO" id="perfilDAO"/>
            <tbody>
            <c:forEach var="perfil" items="${perfilDAO.list}">
              <tr>
                <th scope="row">${perfil.id}</th>
                <td>${perfil.nome}</td>

                <td>
                  <c:forEach var="menu" items="${perfil.menus}">
                    ${menu.nome}
                  </c:forEach>
                </td>
                
                <td>
                  <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_perfil.do?id=${perfil.id}">
                    <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
                  </a>
                  
                  <button class="btn btn-outline-danger" onclick="confirmarExclusao('${perfil.nome}', '/projetojava3_war_exploded/gerenciar_perfil.do?id='+'${perfil.id}'+'+&deletar=true')">
                    <img src="${pageContext.request.contextPath}/imagens/lixeira.svg" alt="lixeira dentro de um quadrado vermelho">
                  </button>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
          
        </div>
      </div>
      <div class="col-sm-2"></div>
    </div>

    <script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>
  
  </body>
</html>
