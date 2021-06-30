<%@ page import="model.Usuario" %>
<%@ page import="controller.GerenciarLogin" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  Usuario ulogado =  GerenciarLogin.autenticar(request, response);
  GerenciarLogin.gerenciarPendencias(ulogado, request);
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-azul-escuro">
  <div class="container">

    <a class="navbar-brand mt-3" href="#">
      <svg width="500" height="500" xmlns="http://www.w3.org/2000/svg" data-name="Layer 1">
        <title>logo completa</title>

        <g>
          <title>Layer 1</title>
          <g stroke="null" id="svg_11">
            <path stroke="null" class="cls-1" d="m227.28792,242.72642l50.11971,0l0,-31.55961q-0.49017,-0.25898 -0.96765,-0.53733a1.9607,1.86855 0 0 1 -0.84066,0.16943c-1.341,0 -1.95816,-1.05529 -1.86419,-2.0404a24.25729,23.11725 0 0 1 -1.84133,-1.65555a2.17658,2.07428 0 0 1 -0.50795,0.05567c-1.60513,0 -2.1715,-1.51517 -1.71942,-2.60677a25.9056,24.68809 0 0 1 -1.56449,-2.35021c-0.03048,0 -0.05587,0 -0.08635,0c-1.72196,0 -2.26039,-1.74995 -1.61529,-2.84639l-43.63569,0a9.74254,9.28466 0 0 0 -9.7146,9.25803l0,42.81689a9.74254,9.28466 0 0 0 9.7146,9.25803l51.55722,0c1.93276,0 2.65151,-1.00205 2.61342,-1.93632c-0.0381,-0.99478 -0.94987,-1.93632 -2.51183,-1.93632l-47.13549,0c-9.99651,0.02178 -9.99651,-14.08915 0,-14.08915zm42.66042,-34.08167c2.50675,0 2.50421,3.70805 0,3.70805s-2.50421,-3.70805 0,-3.70805zm-4.92206,-5.74119c2.50675,0 2.50421,3.70805 0,3.70805s-2.50421,-3.70805 0,-3.70805z" fill="#3500d3" fill-rule="evenodd" id="svg_1"/>
            <polygon stroke="null" class="cls-2" points="228.26318359375,246.7443084716797 241.11439514160156,246.7443084716797 241.11439514160156,261.4844970703125 234.49070739746094,258.98095703125 228.26318359375,261.4844970703125 228.26318359375,246.7443084716797 " fill-rule="evenodd" id="svg_2" fill="#ffffff"/>
            <path stroke="null" class="cls-2" d="m229.83023,248.08277a1.56703,1.49339 0 0 1 0,-2.98677l44.77351,0a1.56703,1.49339 0 0 1 0,2.98677l-44.77351,0z" fill-rule="evenodd" id="svg_3" fill="#ffffff"/>
            <path stroke="null" class="cls-2" d="m229.83023,253.54561a1.56957,1.49581 0 0 1 0,-2.9892l44.77351,0a1.56957,1.49581 0 0 1 0,2.9892l-44.77351,0z" fill-rule="evenodd" id="svg_4" fill="#ffffff"/>
            <rect stroke="null" class="cls-3" x="228.10827" y="208.89164" width="35.98338" height="15.87299" rx="7.65" fill="#fff" id="svg_5"/>
            <circle stroke="null" class="cls-4" cx="273.2221" cy="199.66023" r="1.54113" fill="#3500d3" id="svg_6"/>
            <circle stroke="null" class="cls-4" cx="275.47995" cy="205.73059" r="1.54113" fill="#3500d3" id="svg_7"/>
            <circle stroke="null" class="cls-4" cx="279.69596" cy="200.65501" r="1.54113" fill="#3500d3" id="svg_8"/>
            <g stroke="null" id="svg_12">
              <g stroke="null" id="svg_13">
                <text stroke="null" class="cls-5" fill="#ffffff" font-size="111.55px" font-family="'Rounded Mplus 1c Medium'" y="-994.307" x="1640.57979" id="svg_9" font-weight="normal" font-style="normal" transform="matrix(0.126501 0 0 0.136019 7.04696 407.753)">My</text>
                <text stroke="null" class="cls-6" font-family="'Rounded Mplus 1c'" font-size="130.96px" y="-975.25557" x="1779.79215" id="svg_10" font-weight="normal" font-style="normal" fill="#ffffff" stroke-width="0" transform="matrix(0.126501 0 0 0.136019 7.04696 407.753)">Books</text>
              </g>
            </g>
          </g>
        </g>
      </svg></a>
    
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
          <ul class="navbar-nav ms-auto me-2 mb-2 mb-lg-0">
            <c:forEach var="menu" items="${ulogado.perfil.menus}">
              <c:if test="${menu.exibir == 1}">
                <li class="nav-item mt-lg-0 mt-5 ms-lg-3">
                  <a class="nav-link active"
                     aria-current="page"
                     href="${pageContext.request.contextPath}/${menu.link}">${menu.nome}</a>
                </li>
              </c:if>
            </c:forEach>
          </ul>
          
          <div class="icones-direita">
            <c:if test="${sessionScope.ulogado.perfil.nome.equals('Aluno')}">
              <%@include file="/src/componentes/carrinho.jsp"%>
            </c:if>  
            <%@include file="/src/componentes/icone-conta.jsp"%>
          </div>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
        </c:otherwise>
      </c:choose>
    </div>

  </div>
</nav>

<img src="${pageContext.request.contextPath}/imagens/logo.svg"
     class="d-lg-none mt-3 ms-3"
     alt="logo do site">

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>