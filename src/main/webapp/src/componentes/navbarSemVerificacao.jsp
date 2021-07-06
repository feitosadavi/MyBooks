<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 20/04/2021
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/src/estilo/navbar.css">

<nav class="navbar navbar-expand-lg navbar-dark bg-blue">
  <div class="container">
    <a class="nav-logo" href="${pageContext.request.contextPath}/src/livros/listar-livro.jsp">
      <div class="col-4 col-lg-7">
        <h1 class="display-6 mt-2 ms-2 logo">My<span>Books</span></h1>
      </div>
    </a>

    <button class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

  </div>
</nav>

<img src="/imagens/mybooks.svg"
     class="d-lg-none mt-3 ms-3"
     alt="">

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
