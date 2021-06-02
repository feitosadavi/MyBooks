<div class="d-flex align-items-center">
  <label for="filtro"><h5>Filtrar por: </h5></label>
  <select class="form-select w-25 ms-2" id="filtro">
    <option value="?conta=todos" class="opcao">Todos</option>
    <option value="?conta=inativa" class="opcao">Inativo</option>
    <option value="?conta=ativa" class="opcao">Ativo</option>
  </select>
</div>

<script>
  
  let filtro = document.getElementById('filtro');

  window.addEventListener('load', () => {
    for (let opcao of filtro.children) {
      if (opcao.value === window.location.search) {
        opcao.selected = true;
      }
    }
  })
  
  filtro.addEventListener('change', () => {
    console.log(filtro.value)
    window.location.href = window.location.pathname + filtro.value;
    for (let opcao of filtro.children) {
      if (opcao.value === filtro.value) {
        console.log(opcao)
        opcao.selected = true;
      }
    }
  })

  
</script>
