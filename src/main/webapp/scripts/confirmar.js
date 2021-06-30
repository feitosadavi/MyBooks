function confirmarExclusao(nome, acao) {
  let alert = document.createElement("div");
  alert.className = "alert alert-warning";
  alert.id = "delete-alert";
  alert.innerText = "Realmente deseja" + acao +"?";

  let btn = document.createElement("button");
  btn.className = "btn btn-outline-danger ms-2";
  btn.id = "confirmar";
  btn.innerText = "Sim"

  let btn2 = document.createElement("button");
  btn2.className = "btn btn-outline-success ms-2";
  btn2.setAttribute('data-bs-dismiss', 'alert');
  btn2.setAttribute('aria-label', 'Close');
  btn2.innerText = "Cancelar"

  btn.addEventListener('click', () => {
    location.href = path;
  })

  alert.append(btn, btn2);

  let referencia = document.getElementById('referencia');
  referencia.prepend(alert)
}

function inserirDepois(novoElemento, referencia) {
  referencia.parentNode.insertBefore(novoElemento, referencia.nextSibling);
}