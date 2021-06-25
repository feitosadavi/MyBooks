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

      <%@include file="/src/componentes/mensagem.jsp"%>
<%--      <%@include file="/src/componentes/carrinho.jsp"%>--%>

      <h2 class="titulo mt-4 mb-4">Livros</h2>

      <c:if test="${sessionScope.ulogado.perfil.nome.equals('Bibliotecario')}" >
        <a href="${pageContext.request.contextPath}/src/livros/cadastrar-livro.jsp" class="btn btn-outline-mybooks">Novo Livro</a>
      </c:if>

      <%@include file="/src/componentes/filtro.jsp"%>


      <%@include file="../componentes/campo-pesquisa.jsp"%>
      
      <div class="d-flex gap-4">
        <jsp:useBean class="model.LivroDAO" id="livroDAO" />
        <c:forEach var="livro" items="${livroDAO.list}">
          <div class="mt-5">
            <a data-bs-toggle="modal"
               data-bs-target="#livro-info-${livro.id}">
              <img class="thumb-livro"
                   src="${pageContext.request.contextPath}/imagens/fotosLivro/${livro.capa}"
                   alt="capa do livro">
            </a>
          </div>
          <%@include file="../componentes/livro-modal.jsp"%>
        </c:forEach>
      </div>

    </div>
    <div class="col-sm-2"></div>
  </div>
</div>
  

<script>
  function abrirConta(id) {
    location.href = '${pageContext.request.contextPath}/src/livros/livro.jsp?id='+id;
  }
</script>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
