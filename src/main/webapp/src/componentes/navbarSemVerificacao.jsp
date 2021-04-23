<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 20/04/2021
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-expand-md navbar-light bg-light">
  <div class="container">
    <a href="#" class="navbar-brand mb-2 mb-lg-0">Projeto</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">
      <a href="${pageContext.request.contextPath}/src/login/form-login.jsp" class="nav-link ms-auto">Login</a>
    </div>
  </div>
</nav>

<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
