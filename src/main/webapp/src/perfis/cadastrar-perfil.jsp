<%@ page import="controller.GerenciarLogin" %>
<%

  if (!GerenciarLogin.verificarAcesso(request, response)) {
    request.getSession().setAttribute("msg_acessoNegado", "Acesso negado");
  }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
  <%@include file="../componentes/head.jsp"%>
  <body>
    <%@include file="../componentes/navbar.jsp"%>
    <div class="container row-cols-sm-4">
      <a href="${pageContext.request.contextPath}/src/perfis/listar-perfil.jsp">
        <img src="${pageContext.request.contextPath}/imagens/voltar.svg" alt="seta de voltar">
      </a>

      <div class="ms-auto me-auto mt-5">
        <%@include file="../componentes/mensagem.jsp"%>
        <h3 class="mt-5 mb-3">Novo cadastro</h3>
        <form action="${pageContext.request.contextPath}/gerenciar_perfil.do" method="POST">
          <div class="form-group">
            <input type="text" class="form-control" name="id" hidden>

            <label class="mt-2" for="nome">Nome: </label>
            <input type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do perfil">
          </div>

          <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
        </form>
      </div>
      
    </div>
  </body>
</html>
