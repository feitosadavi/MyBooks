<%@ page import="controller.GerenciarLogin" %>
<%@ page import="model.Usuario" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  Usuario ulogado =  GerenciarLogin.autenticar(request, response);
  GerenciarLogin.gerenciarPendencias(ulogado, request);
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-blue">
  <div class="container">
    <img src="${pageContext.request.contextPath}/imagens/mybooks.svg"
         class="d-none d-lg-block"
         alt="">

    <button class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse"
         id="navbarSupportedContent">
      <c:choose>
        <c:when test="${ulogado != null && ulogado.perfil != null}">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <c:forEach var="menu" items="${ulogado.perfil.menus}">
              <c:if test="${menu.exibir == 1}">
                <li class="nav-item mt-lg-0 mt-3 ms-lg-3">
                  <a class="nav-link active"
                     aria-current="page"
                     href="${pageContext.request.contextPath}/${menu.link}">${menu.nome}</a>
                </li>
              </c:if>
            </c:forEach>
          </ul>
          <%@include file="/src/componentes/icone-conta.jsp"%>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
        </c:otherwise>
      </c:choose>
    </div>

  </div>
</nav>

<img src="${pageContext.request.contextPath}/imagens/mybooks.svg"
     class="d-lg-none mt-3 ms-3"
     alt="">

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>