<div class="card-container mt-3">

  <div class="foto-thumb hover-thumb me-2" onclick="abrirConta(${usuario.id})">
    <img src="${pageContext.request.contextPath}/imagens/fotosUsuario/${usuario.capa}"
         alt="thumbnail do usuário">
  </div>

  <div class="card">
    <div class="card-body">
      <div class="card-title-mybooks">
        <div class="card-title-decoration"></div>
        <h3>${usuario.nome}</h3>
      </div>
      <p class="card-text">
        ${usuario.matricula}
      </p>
    </div>

    <div class="card-footer">
      <button class="btn btn-outline-danger"
              onclick="confirmarExclusao('${usuario.nome}', '/projetojava3_war_exploded/gerenciar_usuario.do?acao=deletar&id='+'${usuario.id}')">
        <img src="${pageContext.request.contextPath}/imagens/lixeira.svg"
             alt="lixeira dentro de um quadrado vermelho">
      </button>
    </div>
  </div>
</div>
