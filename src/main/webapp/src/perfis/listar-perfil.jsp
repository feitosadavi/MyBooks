<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <%@include file="../componentes/head.jsp"%>
  
  <body>
    <%@include file="../componentes/navbar.jsp"%>
    <div class="container">

      <div class="row mt-3">
        <div class="col-sm-2"></div>
        
        <div id="referencia" class="col-sm-8">
          <%@include file="../componentes/mensagem.jsp"%>

          <h2 class="titulo mt-4 mb-4">Perfis</h2>
          <a href="cadastrar-perfil.jsp" class="btn btn-outline-mybooks">Novo Cadastro</a>

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
              <c:choose>
                <c:when test="${param.get('pesquisa') != null && perfil.nome.toLowerCase().contains(param.get('pesquisa').toLowerCase())}">
                  <%@include file="./perfil-tr.jsp"%>
                </c:when>

                <c:when test="${param.get('pesquisa') == null}">
                  <%@include file="./perfil-tr.jsp"%>
                </c:when>
              </c:choose>
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
