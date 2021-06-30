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
<script>
  $(document).ready( function () {
    $('#table_id').DataTable();
  } );
</script>
<%@include file="../componentes/navbar.jsp"%>
  <div class="container">
  
    <div class="row mt-4">
      <div class="col-sm-2"></div>
  
      <div id="referencia" class="col-sm-8">
        <%@include file="/src/componentes/mensagem.jsp"%>


        <h2 class="titulo mt-4 mb-4">Menus</h2>        
        <a href="${pageContext.request.contextPath}/src/menus/cadastrar-menu.jsp" class="btn btn-outline-mybooks">Novo Menu</a>

        <%@include file="../componentes/campo-pesquisa.jsp"%>

  
        <table id="table-id" class="table table-hover table-striped table-bordered display mt-4 text-center">
          <thead>
          <tr>
            <th scope="col">id</th>
            <th scope="col">nome</th>
            <th scope="col">link</th>
            <th scope="col">exibir</th>
            <th scope="col">opções</th>
          </tr>
          </thead>
  
          <jsp:useBean class="model.MenuDAO" id="modelDAO"/>
          <tbody>
          <c:forEach var="menu" items="${modelDAO.list}">
            <c:choose>
              <c:when test="${param.get('pesquisa') != null && menu.nome.toLowerCase().contains(param.get('pesquisa').toLowerCase())}">
                <%@include file="../menus/menu-tr.jsp"%>
              </c:when>

              <c:when test="${param.get('pesquisa') == null}">
                <%@include file="../menus/menu-tr.jsp"%>
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
