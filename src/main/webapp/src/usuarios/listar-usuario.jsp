<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="../componentes/head.jsp"%>

<body>


<%

	int filtro;
	String conta = request.getParameter("conta");
	if (conta != null) {
  	  if (conta.equals("inativa")) {
	    filtro = 0;
		  
	  } else if (conta.equals("ativa")) {
	    filtro = 1;
		  
	  } else {
	    filtro = 2;
	  }
	}
%>

<%@include file="../componentes/navbar.jsp"%>

<div class="container" id="referencia">
  <%@include file="../componentes/mensagem.jsp"%>

  <div class="row mt-3">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
      <h2 class="titulo mt-4 mb-4">Usuários</h2>
      
      <h5>Filtrar por: </h5>
      <a href="?conta=">Todos</a>
      <a href="?conta=inativa">Inativo</a>
      <a href="?conta=ativa">Ativo</a>

      <%@include file="../componentes/campo-pesquisa.jsp"%>

      <jsp:useBean class="model.UsuarioDAO" id="usuarioDAO" />
        <c:forEach var="usuario" items="${usuarioDAO.list}">
          <c:choose>
        	<c:when test="${param.conta.equals('ativa')}">
              <div class="card-container mt-3">

                <div class="foto-thumb hover-thumb me-2" onclick="abrirConta(${usuario.id})">
                  <img src="${pageContext.request.contextPath}/imagens/fotosUsuario/${usuario.capa}"
                       alt="">
                </div>

                <div class="card w-50">
                  <div class="card-body">
                    <div class="card-title-mybooks">
                      <div class="card-title-decoration"></div>
                      <h3>${usuario.nome}</h3>
                    </div>
                    <p class="card-text">
                        ${usuario.matricula}
                    </p>
                  </div>

                  <div class="card-footer">
                    <a class="btn btn-outline-info"
                       href="${pageContext.request.contextPath}/gerenciar_usuario.do?acao=alterar&id=${usuario.id}">
                      <img src="${pageContext.request.contextPath}/imagens/editar.svg"
                           alt="caneta dentro de um quadrado verde">
                    </a>

                    <button class="btn btn-outline-danger"
                            onclick="confirmarExclusao('${usuario.nome}', '/projetojava3_war_exploded/gerenciar_usuario.do?acao=deletar&id='+'${usuario.id}')">
                      <img src="${pageContext.request.contextPath}/imagens/lixeira.svg"
                           alt="lixeira dentro de um quadrado vermelho">
                    </button>
                  </div>
                </div>
              </div>
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
