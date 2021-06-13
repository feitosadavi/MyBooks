<!-- Modal -->
<%@page import="model.UsuarioDAO"%>
<div class="modal fade"
  id="livro-info-${livro.id}"
  tabindex="-1"
  aria-labelledby="livro-info-label"
  aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"></button>
      </div>
      <div class="modal-body d-flex flex-column">
        <h5 class="modal-title align-self-center mb-3"
          id="livro-info-label">${livro.nome}</h5>

        <img src="${pageContext.request.contextPath}/imagens/fotosLivro/${livro.capa}"
          alt="capa do livro"
          class="modal-capa align-self-center">

        <c:choose>
          <c:when test="${sessionScope.ulogado.perfil == 'Aluno' &&  sessionScope.ulogado.status == 0}">
            <p class="text-center text-secondary p-2">Voce nao pode realizar locacoes enquanto estiver inativo. Espere o Bibliotecario ativa-lo</p>
          </c:when>
          
          <c:otherwise>
            <c:choose>
              <c:when test="${livro.estoque >= 1}">
                <a href="${pageContext.request.contextPath}/gerenciar_usuario.do?acao=carrinho&livroId=${livro.id}"
                   class="btn btn-cadastro mt-3" disabled="${livro.estoque >= 1 ? false : true}">Adicionar ao Carrinho</a>
              </c:when>
              <c:otherwise>
                <button class="btn btn-cadastro mt-3" disabled>Livro fora de estoque</button>
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
		

        <div class="info-livro d-flex justify-content-between mt-4">
          <div class="">
            <p><span class="info-bold">Paginas:</span> ${livro.paginas}</p>
            <p><span class="info-bold">Estoque:</span> ${livro.estoque}</p>
            <p><span class="info-bold">Ano:</span> ${livro.lancamento}</p>
          </div>

          <div class="">
            <p>${livro.genero}</p>
          </div>
        </div>

      </div>
      <c:if test="${sessionScope.ulogado.perfil.nome.equals('Bibliotecario')}" >
        <div class="modal-footer d-flex">
          <a class="btn btn-outline-info"
             href="${pageContext.request.contextPath}/gerenciar_livro.do?acao=alterar&id=${livro.id}">
            <img src="${pageContext.request.contextPath}/imagens/editar.svg"
                 alt="caneta dentro de um quadrado verde">
          </a>

          <button class="btn btn-outline-danger"
                  onclick="confirmarExclusao('${livro.nome}', '/projetojava3_war_exploded/gerenciar_livro.do?acao=deletar&id='+'${livro.id}')">
            <img src="${pageContext.request.contextPath}/imagens/lixeira.svg"
                 alt="lixeira dentro de um quadrado vermelho">
          </button>
        </div>      
      </c:if>
    </div>
  </div>
</div>