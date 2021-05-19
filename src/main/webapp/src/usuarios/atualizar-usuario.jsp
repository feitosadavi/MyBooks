<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

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
    <h3 class="mt-5 mb-3">Atualizar Usuário</h3>
    <form class="form"
    	action="${pageContext.request.contextPath}/gerenciar_usuario.do" 
    	method="POST" 
    	enctype="multipart/form-data">

      <div class="form-group">
        <input value="${sessionScope.usuario.id}" type="text" class="form-control" name="id" hidden>


        <div id="preview-container"
             class="ms-auto me-auto">
          <input name="capaAtual" value="${sessionScope.usuario.capa}" hidden>
          <input onchange="lerURL(this)"
                 type="file"
                 class="form-control form-mybooks"
                 id="preview"
                 name="capa"
                 hidden>
          <img  src="${pageContext.request.contextPath}/imagens/fotosUsuario/${sessionScope.usuario.capa}"
               class="camera-icone"
               id="camera-icone"
               alt="foto de perfil">
        </div>
        
        <label class="mt-2" for="nome">Nome: </label>
        <input value="${sessionScope.usuario.nome}" type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do menu">

        <label class="mt-2" for="email">Email: </label>
        <input value="${sessionScope.usuario.email}" type="email" class="form-control" id="email" name="email" placeholder="insira o email">
        
        <label class="mt-2" for="senha">Senha: </label>
        <input value="${sessionScope.usuario.senha}" type="password" class="form-control" id="senha" name="senha" placeholder="insira a senha">
        
        <c:if test="${sessionScope.ulogado.perfil.nome == 'bibliotecario' || sessionScope.ulogado.perfil.nome == 'admin'}" >
          <label class="mt-2" for="matricula">Matricula: </label>
          <input value="${sessionScope.usuario.matricula}" type="text" class="form-control" id="matricula" name="matricula" placeholder="insira a matricula">

          <label class="mt-2" for="status">Status: </label>
          <select class="form-select" name="status" id="status">
            <option value="1" ${sessionScope.usuario.status == "1" ? "selected" : null}>Ativo</option>
            <option value="0" ${sessionScope.usuario.status == "0" ? "selected" : null}>Inativo</option>
          </select>
        
          <label class="mt-2" for="idPerfil">Perfil: </label>
          <select id="idPerfil" class="form-select" name="idPerfil">
            <jsp:useBean class="model.PerfilDAO" id="perfilDAO"/>
            <c:forEach var="perfilDAO" items="${perfilDAO.list}">
              <option value="${perfilDAO.id}" ${sessionScope.usuario.perfil.id == perfilDAO.id ? "selected" : null }>${perfilDAO.nome}</option>
            </c:forEach>
          </select>
        </c:if>
        
      </div>

      <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
    </form>

  </div>

</div>

<script src="${pageContext.request.contextPath}/scripts/preview.js"></script>
<script>
  mostrarPreview()
</script>

</body>
</html>
