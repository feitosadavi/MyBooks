<%-- 
    Document   : index
    Created on : 27/03/2021, 18:55:40
    Author     : eu
--%>

<%@page import="model.MenuDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Menu"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="../componentes/head.jsp"%>

<body>
<div class="container">
  <%@include file="../componentes/navbar.jsp"%>

  <div class="row mt-4">
    <div class="col-sm-2"></div>

    <div id="referencia" class="col-sm-8">
      <c:if test="${sessionScope.mensagem != null}">
        <div class="alert ${sessionScope.mensagem != "Gravado com sucesso" ? "alert-danger" : "alert-success"} alert-dismissible fade show" role="alert">
          <strong>${sessionScope.mensagem}</strong>
          <%session.invalidate();%>
          <button class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </c:if>

      <h2>Lista de Usuário</h2>
      <a href="cadastrar-usuario.jsp" class="btn btn-primary">Novo Usuário</a>

      <table class="table table-hover table-striped table-bordered display mt-4 text-center">
        <thead>
        <tr>
          <th scope="col">id</th>
          <th scope="col">nome</th>
          <th scope="col">username</th>
          <th scope="col">status</th>
          <th scope="col">perfil</th>
          <th scope="col">opções</th>
        </tr>
        </thead>

        <jsp:useBean class="model.UsuarioDAO" id="usuarioDAO"/>
        <tbody>
        <c:forEach var="usuario" items="${usuarioDAO.list}">
          <tr>
            <th scope="row">${usuario.id}</th>
            <td>${usuario.nome}</td>
            <td>${usuario.username}</td>
            <td>${usuario.status == 1 ? "Ativo" : "Inativo"}</td>
            <td>${usuario.idPerfil}</td>
            <td>
              <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_usuario.do?id=${usuario.id}">
                <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
              </a>

              <button class="btn btn-outline-danger" onclick="confirmarExclusao(${usuario.id}, '${usuario.nome}')">
                <img src="${pageContext.request.contextPath}/imagens/lixeira.svg" alt="lixeira dentro de um quadrado vermelho">
              </button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

    </div>
  </div>
  <div class="col-sm-2"></div>
</div>

<script>
  function confirmarExclusao(id, nome) {
    let alert = document.createElement("div");
    alert.className = "alert alert-warning";
    alert.id = "delete-alert";
    alert.innerText = "Realmente deseja excluir "+nome+"?";

    let btn = document.createElement("button");
    btn.className = "btn btn-outline-danger ms-2";
    btn.id = "confirmar";
    btn.innerText = "Sim"

    let btn2 = document.createElement("button");
    btn2.className = "btn btn-outline-success ms-2";
    btn2.setAttribute('data-bs-dismiss', 'alert');
    btn2.setAttribute('aria-label', 'Close');
    btn2.innerText = "Cancelar"

    btn.addEventListener('click', () => {
      location.href = "${pageContext.request.contextPath}/gerenciar_usuario.do?id="+id+"+&deletar=true";
    })

    alert.append(btn, btn2);

    let referencia = document.getElementById('referencia');
    referencia.prepend(alert)
  }

  function inserirDepois(novoElemento, referencia) {
    referencia.parentNode.insertBefore(novoElemento, referencia.nextSibling);
  }

</script>

</body>
</html>
