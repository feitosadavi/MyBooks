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
              <img src="${pageContext.request.contextPath}/imagens/${usuario.capa}"
                   alt="">
            </div>

          </div>
          <div class="info-complementar">
            <p>${usuario.email}</p>
            <p>${usuario.status == 0 ? "Inativo" : "Ativo"}</p>
            <p>${usuario.matricula}</p>
          </div>
        </div>
      </section>

      <aside>

        <h3 class="titulo">Histórico de Locações</h3>

        <%@include file="../componentes/campo-pesquisa.jsp"%>

        <div class="card-container mt-4">

          <div class="livro-thumb hover-thumb me-2">
            <img src="${pageContext.request.contextPath}/imagens/ladrao-de-raios-capa.jpg"
                 alt="">
          </div>

          <div class="card">
            <div class="card-body">
              <div class="card-title-mybooks">
                <div class="card-title-decoration"></div>
                <h3>Percy Jackson e o Ladrão de Raios</h3>
              </div>
              <p class="card-text text-success">
                Devolvido
              </p>
              <p class="text-secondary text-end">14/04/21 - 12/05/21</p>
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
      </aside>
    </main>
  </div>

</body>
</html>
