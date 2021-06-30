<tr>
  <th scope="row">${perfil.id}</th>
  <td>${perfil.nome}</td>

  <td>
    <div class="accordion" id="menus-vinculados">
      <div class="accordion-item">
        <h2 class="accordion-header" id="one">
          <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#${perfil.nome}" aria-expanded="true" aria-controls="collapseOne">
            Mostrar
          </button>
        </h2>

        <div id="${perfil.nome}" class="accordion-collapse collapse show" aria-labelledby="one" data-bs-parent="#menus-vinculados">
          <div class="accordion-body">
            <c:forEach var="menu" items="${perfil.menus}">
              <ul class="list-group list-group-flush">
                <li class="list-group-item">${menu.nome}</li>
              </ul>
            </c:forEach>
          </div>
        </div>

      </div>
    </div>
  </td>

  <td>
    <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_perfil.do?acao=alterar&id=${perfil.id}">
      <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
    </a>

    <button class="btn btn-outline-danger" onclick="confirmarExclusao('${perfil.nome}', '/projetojava3_war_exploded/gerenciar_perfil.do?acao=deletar&id='+'${perfil.id}')">
      <img src="${pageContext.request.contextPath}/imagens/lixeira.svg" alt="lixeira dentro de um quadrado vermelho">
    </button>
  </td>
</tr>