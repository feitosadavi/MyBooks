<%@ page import="controller.GerenciarLogin" %>
<%@ page import="model.Usuario" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  Usuario ulogado =  GerenciarLogin.autenticar(request, response);
  request.setAttribute("ulogado", ulogado);
%>

<nav class="navbar navbar-expand-md navbar-light bg-light">
  <div class="container">
    <a href="#" class="navbar-brand mb-2 mb-lg-0">Projeto</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="menu">
      <c:choose>
        <c:when test="${ulogado != null && ulogado.perfil != null}">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <c:forEach var="menu" items="${ulogado.perfil.menus}">
              <c:if test="${menu.exibir == 1}">
                <li class="nav-item">
                  <a href="${pageContext.request.contextPath}/${menu.link}" aria-current="page" class="nav-link">${menu.nome}</a>
                </li>
              </c:if>            
            </c:forEach>
          </ul>
          <a href="${pageContext.request.contextPath}/gerenciar_login.do?logout=true" class="nav-link ms-auto">Sair</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
        </c:otherwise>
      </c:choose>

    </div>
  </div>
</nav>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>