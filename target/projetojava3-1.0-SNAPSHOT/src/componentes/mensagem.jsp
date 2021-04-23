<c:choose>
  <c:when test="${sessionScope.mensagem != null}">
    <div class="alert ${sessionScope.mensagem.contains("sucesso") ? "alert-success" : "alert-danger"} alert-dismissible fade show" role="alert">
      <strong>${sessionScope.mensagem}</strong>
      <button class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="${sessionScope.mensagem = null}"></button>
    </div>
  </c:when>

  <c:when test="${sessionScope.msg_acessoNegado != null}">
    <div class="alert ${sessionScope.msg_acessoNegado.contains("sucesso") ? "alert-success" : "alert-danger"} alert-dismissible fade show" role="alert">
      <strong>${sessionScope.msg_acessoNegado}</strong>
      <button class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="${sessionScope.msg_acessoNegado = null}"></button>
    </div>
  </c:when>
</c:choose>

