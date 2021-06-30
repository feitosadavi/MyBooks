<%@page import="model.LocacaoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<%@include file="../componentes/head.jsp"%>
<body>
<%@include file="../componentes/navbar.jsp"%>

<div class="container">
  <c:set var="usuario" value="${sessionScope.ulogado}"/>
  <div class="row">
    <div class="d-flex justify-content-center mt-5">
      <div class="col-12 col-lg-5 form-container" id="referencia">
        <h1 class="titulo">Alterar Senha</h1>
    
        <form class="form" action="${pageContext.request.contextPath}/gerenciar_usuario.do?acao=alterar_senha" method="POST">
          <div class="row mb-3 mt-5">
            <input type="password"
                   name="senha_atual"
                   class="form-control form-mybooks mb-3"
                   placeholder="Insira a sua senha antiga"
                   required>
    
            <input type="password"
                   name="senha_nova"
                   class="form-control form-mybooks mb-3"
                   placeholder="Nova senha"
                   required>
          </div>
          <button type="submit" class="btn btn-dark bg-azul-escuro w-100 mt-4">Alterar Senha</button>      
        </form>
      </div>
    </div>  
</div>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>

</body>
</html>
