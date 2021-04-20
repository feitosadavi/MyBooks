<c:if test="${sessionScope.mensagem != null}">
  <div class="alert ${sessionScope.mensagem != "Gravado com sucesso" ? "alert-danger" : "alert-success"} alert-dismissible fade show" role="alert">
    <strong>${sessionScope.mensagem}</strong>
      ${sessionScope.mensagem = null}
    <button class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
</c:if>
