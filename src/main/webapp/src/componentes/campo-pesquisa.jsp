<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="d-flex ms-auto me-auto mt-3">
  <input class="form-control navbar-input"
         name="pesquisa"
         type="search"
         placeholder="Pesquisar"
         aria-label="Pesquisar">
  <button class="btn btn-outline-success btn-pesquisa"
          type="submit">

    <img src="${pageContext.request.contextPath}/imagens/search.svg"
         alt="lupa">

  </button>
</form>
