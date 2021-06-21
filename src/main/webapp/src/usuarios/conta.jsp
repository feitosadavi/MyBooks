<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%

  int id = Integer.parseInt(request.getParameter("id"));

%>

<!DOCTYPE html>
<html lang="pt-br">
<%@include file="../componentes/head.jsp"%>
<body>
  <%@include file="../componentes/navbar.jsp"%>
  
  <div class="container">
    <jsp:useBean class="model.UsuarioDAO" id="usuarioDAO"/>
    <c:set var="usuario" value="${usuarioDAO.getById(param.id)}"/>
    <main class="wrapper">
      <section>
        <div class="container-conta">
          <div class="foto-conta-container">
            <div class="card-title-mybooks ms-3 mt-1">
              <div class="card-title-decoration bg-azul-escuro"></div>
              <h3 class="text-white">Admin</h3>
            </div>
          </div>

          <div class="info-conta-container">
            <div class="card-title-mybooks">
              <div class="card-title-decoration"></div>
              <h3 class="text-white">${usuario.nome}</h3>
            </div>
            <div class="foto-thumb hover-thumb">
              <img src="${pageContext.request.contextPath}/imagens/fotosUsuario/${usuario.capa}"
                   alt="">
            </div>

          </div>
          <div class="info-complementar row">
            <div class="col-8">
              <p>${usuario.email}</p>
              
              <c:choose>
                <c:when test="${sessionScope.ulogado.perfil.nome == 'Bibliotecario'}">
                  <form action="/gerenciar_usuario.do?acao=alterar_status" method="POST">
                    <input name="id" type="text" value="${usuario.id}" hidden>
                    <div class="d-flex justify-content-between gap-4">
                      <select name="status" id="status" class="form-select">
                        <option value="1" ${usuario.status == 1 ? "selected" : null}>Ativo</option>
                        <option value="0" ${usuario.status == 0 ? "selected" : null}>Inativo</option>
                      </select>
                      <button class="btn btn-outline-mybooks">Ok</button>
                    </div>
                  </form>
                </c:when>
                
                <c:otherwise>
                  <p>${usuario.status == 1 ? "Ativo" : "Inativo"}</p>
                </c:otherwise>
              </c:choose>
              <p>${usuario.matricula}</p>
            </div>
          </div>
        </div>
      </section>

      <aside id="referencia">
        <%@include file="../componentes/mensagem.jsp"%>

        <h3 class="titulo">Histórico de Locações</h3>

        <%@include file="../componentes/campo-pesquisa.jsp"%>

        <jsp:useBean class="model.LocacaoDAO" id="livroDAO" />
        <c:forEach var="locacao" items="${livroDAO.getHistoricoLocacoesPorUsuario(usuario.id)}">
          <div class="card-container mt-4">
            <div class="livro-thumb hover-thumb me-2">
              <img src="${pageContext.request.contextPath}/imagens/fotosLivro/${locacao.getLivros()[0].getCapa()}"
                   alt="capa do livro">
            </div>

            <div class="card">
              <div class="card-body">
                <div class="card-title-mybooks">
                  <div class="card-title-decoration"></div>
                  <h3>${locacao.getLivros()[0].getNome()}</h3>
                </div>

                <form action="${pageContext.request.contextPath}/gerenciar_locacao.do" method="GET">
                  <input name="acao" type="text" value="alterar_status" hidden>
                  <input name="idConta" type="text" value="${usuario.id}" hidden>
                  <input name="idLocacao" type="text" value="${locacao.id}" hidden>
                  <div class="d-flex  gap-4">
                    <select class="form-select w-25" name="statusLocacao" id="statusLivro">
                      <option value="pendente" ${locacao.status == "pendente" ? "selected" : null}>Pendente</option>
                      <option value="confirmado" ${locacao.status == "confirmado" ? "selected" : null}>Confirmado</option>
                      <option value="devolvido" ${locacao.status == "devolvido" ? "selected" : null}>Devolvido</option>
                      <option value="n_devolvido" ${locacao.status == "n_devolvido" ? "selected" : null}>Não Devolvido</option>
                    </select>

                    <button class="btn btn-outline-mybooks">Ok</button>
                  </div>
                </form>
                
                <p class="text-secondary text-end">${locacao.getHorarioColeta()}</p>
                <p class="text-secondary text-end">${locacao.getDataLocacao()} § ${locacao.getDataDevolucao()}</p>
              </div>

              <div class="card-footer">
                <button class="btn btn-outline-danger"
                        onclick="confirmarExclusao('$locacao.livro.nome}', '/projetojava3_war_exploded/gerenciar_locacao.do?id='+'${locacao.id}')">
                  <img src="${pageContext.request.contextPath}/imagens/lixeira.svg"
                       alt="lixeira dentro de um quadrado vermelho">
                </button>
              </div>
            </div>
          </div>
        </c:forEach>

      </aside>
    </main>
  </div>

</body>
</html>
