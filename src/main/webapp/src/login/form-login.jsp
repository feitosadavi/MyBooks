<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="../componentes/head.jsp"%>

<body>

<%@include file="../componentes/navbarSemVerificacao.jsp"%>

<div class="container">

  <div class="row mt-3">
    <div class="col-sm-3"></div>

    <div id="referencia" class="col-sm-6">
      <%@include file="../componentes/mensagem.jsp"%>

      <h2 class="titulo mt-4 mb-4">Login</h2>

        <form class="form" action="${pageContext.request.contextPath}/gerenciar_login.do" method="POST">

          <div class="row mb-3 mt-5">
            <input type="email"
                   name="email"
                   class="form-control form-mybooks mb-3"
                   placeholder="Seu email"
                   required>

            <input type="password"
                   name="senha"
                   class="form-control form-mybooks mb-3"
                   placeholder="Sua senha"
                   required>
            <p class="text-white">Não possui uma conta?
              <a href="${pageContext.request.contextPath}/src/usuarios/cadastrar-usuario.jsp">Cadastre-se</a>
            </p>
          </div>

          <input type="checkbox"
                 id="lembrar"
                 name="lembrar">
          <label class="ms-1 text-white"
                 for="lembrar">Lembre-se de mim</label>

          <button type="submit"
             class="btn btn-dark bg-azul-escuro w-100 mt-4">Entrar</button>

        </form>
      </div>
    </div>
    <div class="col-sm-3"></div>
  </div>
<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

</body>
</html>
