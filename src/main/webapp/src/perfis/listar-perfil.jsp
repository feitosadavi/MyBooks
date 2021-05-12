<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@page import="model.PerfilDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Perfil"%>
<%@ page import="java.util.Enumeration" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <%@include file="../componentes/head.jsp"%>
  
  <body>
    <%@include file="../componentes/navbar.jsp"%>
    <div class="container">

      <div class="row mt-4">
        <div class="col-sm-2"></div>
        
        <div id="referencia" class="col-sm-8">
          <%@include file="../componentes/mensagem.jsp"%>

          <h2 class="titulo">Lista de Perfis</h2>
          <a href="cadastrar-perfil.jsp" class="btn btn-primary">Novo Cadastro</a>

          <%@include file="../componentes/campo-pesquisa.jsp"%>

          <table class="table table-hover table-striped table-bordered display mt-4">
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
                  <div class="accordion" id="menus-vinculados">
                    <div class="accordion-item">
                      <h2 class="accordion-header" id="one">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#${perfil.nome}" aria-expanded="true" aria-controls="collapseOne">
                          Mostrar
                        </button>
                      </h2>
                      
                      <div id="${perfil.nome}" class="accordion-collapse collapse show" aria-labelledby="one" data-bs-parent="#menus-vinculados">
                        <div class="accordion-body">
                          <c:forEach var="menu" items="${perfil.menus}">
                            <ul class="list-group list-group-flush">
                              <li class="list-group-item">${menu.nome}</li>
                            </ul>
                          </c:forEach>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </td>
                
                <td>
                  <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_perfil.do?acao=alterar&id=${perfil.id}">
                    <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
                  </a>
                  
                  <button class="btn btn-outline-danger" onclick="confirmarExclusao('${perfil.nome}', '/projetojava3_war_exploded/gerenciar_perfil.do?acao=deletar&id='+'${perfil.id}')">
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
