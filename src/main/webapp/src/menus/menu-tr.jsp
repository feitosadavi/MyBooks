<tr>
  <th scope="row">${menu.id}</th>
  <td>${menu.nome}</td>
  <td>${menu.link}</td>
  <td>${menu.exibir == 1 ? "Sim" : "Não"}</td>
  <td>
    <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/gerenciar_menu.do?acao=alterar&id=${menu.id}">
      <img src="${pageContext.request.contextPath}/imagens/editar.svg" alt="caneta dentro de um quadrado verde">
    </a>

    <button class="btn btn-outline-danger" onclick="confirmarExclusao('${menu.nome}', '/projetojava3_war_exploded/gerenciar_menu.do?acao=deletar&id='+'${menu.id}')">
      <img src="${pageContext.request.contextPath}/imagens/lixeira.svg" alt="lixeira dentro de um quadrado vermelho">
    </button>
  </td>
</tr>