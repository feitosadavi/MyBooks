<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 20/04/2021
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/src/estilo/navbar.css">

<nav class="navbar navbar-expand-lg navbar-dark bg-blue">
  <div class="container-fluid">
    <img src="${pageContext.request.contextPath}/imagens/mybooks.svg"
         class="d-none d-lg-block"
         alt="">

    <button class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse"
         id="navbarSupportedContent">
      <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
      
    </div>

  </div>
</nav>

<img src="/imagens/mybooks.svg"
     class="d-lg-none mt-3 ms-3"
     alt="">

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
