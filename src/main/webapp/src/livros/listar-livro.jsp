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
  <%@include file="/src/componentes/carrinho.jsp"%>
  
  <h2 class="titulo mt-4 mb-4">Livros</h2>
  
  <a href="${pageContext.request.contextPath}/src/livros/cadastrar-livro.jsp" class="btn btn-primary">Novo Livro</a>
  
  <h5>Filtrar por: </h5>
  	${request.getAttribute("carrinho")}

  <a href="?conta=">Todos</a>
  <a href="?conta=inativa">Inativo</a>
  <a href="?conta=ativa">Ativo</a>

  <%@include file="../componentes/campo-pesquisa.jsp"%>

  <jsp:useBean class="model.LivroDAO" id="livroDAO" />
   <c:forEach var="livro" items="${livroDAO.list}">
    <div class="container">
      <div class="livro-container mt-5">
        <a data-bs-toggle="modal"
          data-bs-target="#livro-info-${livro.id}">          
        <img class="thumb-livro"
            src="${pageContext.request.contextPath}/imagens/fotosLivro/${livro.capa}"
            alt="capa do livro">
        </a>
      </div>
      <%@include file="../componentes/livro-modal.jsp"%>
    </div>
   </c:forEach>  
 
</div>
  

<script>
  function abrirConta(id) {
    location.href = '${pageContext.request.contextPath}/src/livros/livro.jsp?id='+id;
  }
</script>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
