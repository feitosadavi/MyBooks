<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="../componentes/head.jsp"%>

<body>

<%@include file="../componentes/navbar.jsp"%>

<div class="container" id="referencia">
  <%@include file="../componentes/mensagem.jsp"%>

  <div class="row mt-3">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
      <h2 class="titulo mt-4 mb-4">Usuários</h2>


      <%@include file="../componentes/filtro.jsp"%>

      <%@include file="../componentes/campo-pesquisa.jsp"%>

      <jsp:useBean class="model.UsuarioDAO" id="usuarioDAO" />
        <c:forEach var="usuario" items="${usuarioDAO.list}">
          <c:choose>
            <c:when test="${param.conta.equals('ativa') && usuario.status == 1}">
              <%@include file="../componentes/usuario-card.jsp"%>
            </c:when>

            <c:when test="${param.conta.equals('inativa') && usuario.status == 0}">
              <%@include file="../componentes/usuario-card.jsp"%>
            </c:when>

            
            
            <c:when test="${param.conta.equals('todos') || param.conta == null || param.conta.equals('')}">
              <%@include file="../componentes/usuario-card.jsp"%>
            </c:when>

            <c:when test="${param.pesquisa != null && usuario.nome == param.pesquisa}">
              <%@include file="../componentes/usuario-card.jsp"%>
            </c:when>
          </c:choose>
        </c:forEach>
       
    </div>
    <div class="col-sm-2"></div>
  </div>
  
  
</div>
  

<script>
  function abrirConta(id) {
    location.href = '${pageContext.request.contextPath}/src/usuarios/conta.jsp?id='+id;
  }
</script>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
