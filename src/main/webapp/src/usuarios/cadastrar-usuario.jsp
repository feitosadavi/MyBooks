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
<div class="container">
  <c:choose>
    <c:when test="${ulogado != null && ulogado.perfil != null}">
      <%@include file="../componentes/navbar.jsp"%>
    </c:when>
    
    <c:otherwise>
      <%@include file="../componentes/navbarSemVerificacao.jsp"%>
    </c:otherwise>
  </c:choose>

  <a href="${pageContext.request.contextPath}/src/menus/listar-menu.jsp">
    <img src="${pageContext.request.contextPath}/imagens/voltar.svg" alt="seta de voltar">
  </a>

  <%@include file="../componentes/mensagem.jsp"%>
  <img src="" alt="">
  <h3 class="mt-5 mb-3">Cadastre-se</h3>

  <div class="row">
    <div class="col-sm-2"></div>
    <div class="col-sm-8">
      <form action="${pageContext.request.contextPath}/gerenciar_usuario.do" method="POST" enctype="multipart/form-data">
        <div class="form-group row">
          <input type="text" class="form-control" name="id" hidden>

          <div class="col-sm-5"></div>
          <div class="col-sm-4">
            <p class="text ms-2">Foto de perfil:</p>
            <div id="preview-container">
              <input onchange="lerURL(this)" type="file" class="form-control" id="preview" name="capa" hidden>
            </div>
          </div>
          <div class="col-sm-5"></div>
          
          <div class="row">
            <label class="mt-2" for="nome">Nome: </label>
            <input type="text" class="form-control" id="nome" name="nome" placeholder="insira o nome do menu" required>

            <div class="col">
              <div class="form-outline">
                <label class="mt-2" for="email">Email: </label>
                <input type="text" class="form-control" id="email" name="email" placeholder="insira o email" required>
              </div>  
            </div>
            <div class="col">
              <div class="form-outline">
                <label class="mt-2" for="senha">Senha: </label>
                <input type="password" class="form-control" id="senha" name="senha" placeholder="insira a senha" required>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col">
              <div class="form-outline">
                <label class="mt-2" for="matricula">Matricula: </label>
                <input type="text" class="form-control" id="matricula" name="matricula" placeholder="insira a matricula" required>
              </div>
            </div>
            <div class="col">
              <div class="form-outline">
                <label class="mt-2" for="idPerfil">Perfil: </label>
                <select id="idPerfil" class="form-select" name="idPerfil">
                  <jsp:useBean class="model.PerfilDAO" id="perfilDAO"/>
                  <c:forEach var="perfilDAO" items="${perfilDAO.list}">
                    <option value="${perfilDAO.id}" >${perfilDAO.nome}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
        </div>

        <button class="btn btn-primary mt-3 ms-auto me-auto" type="submit">Enviar</button>
      </form>
    </div>
    <div class="col-sm-2"></div>
  </div>

</div>

<script src="${pageContext.request.contextPath}/scripts/preview.js"></script>
<script>
  mostrarPreview()
</script>

</body>
</html>
