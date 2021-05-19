<%@page contentType="text/html"
        pageEncoding="UTF-8"
%>
<%@ taglib
  prefix="c"
  uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../componentes/head.jsp"
%>

<body>

<%@include file="../componentes/navbar.jsp"%>
  
<div class="container-fluid">

  <div class="row">
    <div class="col-4 col-lg-7">
      <h1 class="display-6 mt-2 ms-2 logo">My<span>Books</span></h1>
    </div>

    <div id="referencia" class="col-12 col-lg-5 form-container-cadastro d-flex flex-column justify-content-start align-items-center">
      <%@include file="../componentes/mensagem.jsp"%>
      <img src="${pageContext.request.contextPath}/imagens/garota-lendo-livro.svg"
           class="me-auto"
           alt="desenho de uma garota lendo um garota lendo livro">

      <form class="form"
            action="${pageContext.request.contextPath}/gerenciar_livro.do"
            method="POST"
            enctype="multipart/form-data">

        <div id="preview-container"
             class="ms-auto me-auto">
          <input onchange="lerURL(this)"
                 type="file"
                 class="form-control form-mybooks"
                 id="preview"
                 name="capa"
                 hidden>
          <img src="${pageContext.request.contextPath}/imagens/camera.svg"
               class="camera-icone"
               id="camera-icone"
               alt="icone de uma câmera">
        </div>

        <div class="row mb-3 mt-5">
          <div class="col">
            <input class="form-control form-mybooks"
                   name="nome"
                   placeholder="Seu nome">
          </div>
          <div class="col">
            <input class="form-control form-mybooks"
                   name="lancamento"
                   placeholder="Data de lançamento">
          </div>
        </div>


        <input class="form-control form-mybooks mb-3"
               name="genero"
               placeholder="Gênero do livro">

        <div class="row mb-3 mt-5">
          <div class="col">
            <input class="form-control form-mybooks"
                   name="paginas"
                   placeholder="Qtd. pág.">
          </div>
          <div class="col">
            <input class="form-control form-mybooks"
                   name="estoque"
                   placeholder="Qtd. em estoque">
          </div>
        </div>

        

        <button class="btn btn-dark btn-cadastro w-100 mt-4">Cadastrar</button>

      </form>
    </div>

  </div>
</div>

<script src="${pageContext.request.contextPath}/scripts/confirmar.js"></script>
<script src="${pageContext.request.contextPath}/scripts/preview.js"></script>
<script>
  mostrarPreview()
</script>
</body>

</html>