<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<%@include file="../componentes/head.jsp"%>
<body>
<%@include file="../componentes/navbar.jsp"%>

<div class="container">
  <c:set var="usuario" value="${sessionScope.ulogado}"/>
  <main class="wrapper">
    <section>
      <div class="container-conta">
        <div class="foto-conta-container">
          <div class="card-title-mybooks ms-3 mt-1">
            <div class="card-title-decoration bg-azul-escuro"></div>
            <h3 class="text-white">${usuario.perfil.nome}</h3>
          </div>
        </div>

        <div class="info-conta-container">
          <div class="card-title-mybooks">
            <div class="card-title-decoration"></div>
            <h3 class="text-white">${usuario.nome}</h3>
          </div>
          <div class="foto-thumb hover-thumb">
            <img src="${pageContext.request.contextPath}/imagens/fotosUsuario/${usuario.capa}"
                 alt="foto do usuário">
          </div>
        </div>
        <div class="info-complementar">
          <form action="${pageContext.request.contextPath}/gerenciar_usuario.do?acao=alterar" method="POST">
            <input name="email" type="email" value="${usuario.email}">
            <p>${usuario.status == 0 ? "Inativo" : "Ativo"}</p>
            <p>${usuario.matricula}</p>
            <button type="submit" class="btn btn-dark bg-azul-escuro w-100 mt-4">Enviar Alteração</button>
          </form>
          <hr>
          <a href="./alterar-senha.jsp">Alterar Senha</a>
        </div>
      </div>
    </section>

    <aside id="referencia">
      <%@include file="../componentes/mensagem.jsp"%>

      <h3 class="titulo">Histórico de Locações</h3>

      <%@include file="../componentes/campo-pesquisa.jsp"%>

  		<jsp:useBean class="model.LocacaoDAO" id="livroDAO" />
      <c:forEach var="locacoes" items="${livroDAO.getHistoricoLocacoesPorUsuario(usuario.id)}">
        <c:forEach var="livro" items="${locacoes.livros}">
          <div class="card-container mt-4">
            <div class="livro-thumb hover-thumb me-2">
              <img src="${pageContext.request.contextPath}/imagens/fotosLivro/${livro.capa}"
                   alt="capa do livro">
            </div>
    
            <div class="card">
              <div class="card-body">
                <div class="card-title-mybooks">
                  <div class="card-title-decoration"></div>
                  <h3>${livro.nome}</h3>
                </div>
                <p class="card-text text-success">
                  ${locacoes.getStatus()}
                </p>
                <p class="text-secondary text-end">${locacoes.getHorarioColeta()}</p>
                <p class="text-secondary text-end">${locacoes.getDataLocacao()} § ${locacoes.getDataDevolucao()}</p>
              </div>
    
              <div class="card-footer">
                <button class="btn btn-outline-danger"
                        onclick="confirmarExclusao('${livro.nome}', '/projetojava3_war_exploded/gerenciar_locacao.do?acao=cancelar&idLocacao='+'${locacoes.id}&idLivro=${livro.id}')">
                  <img src="${pageContext.request.contextPath}/imagens/lixeira.svg"
                       alt="lixeira dentro de um quadrado vermelho">
                </button>
              </div>
            </div>
          </div>
        </c:forEach>
	    </c:forEach>
            
    </aside>
  </main>
</div>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
