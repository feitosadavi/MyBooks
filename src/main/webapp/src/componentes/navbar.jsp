<nav class="navbar navbar-expand-md navbar-light bg-light">
  <div class="container">
    <a href="#" class="navbar-brand mb-2 mb-lg-0">Projeto</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/" aria-current="page" class="nav-link">Home</a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/src/perfil/listar-perfil.jsp" class="nav-link">Perfis</a>
        </li>
        <li class="nav-item"><a href="#" class="nav-link">Menus</a></li>
        <li class="nav-item"><a href="#" class="nav-link">Usuarios</a></li>
      </ul>
    </div>
  </div>
</nav>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>