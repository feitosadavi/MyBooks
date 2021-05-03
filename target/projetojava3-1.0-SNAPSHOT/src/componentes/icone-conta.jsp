<div id="container" onclick="abrirMenu()">
  <img src="${pageContext.request.contextPath}/imagens/${sessionScope.ulogado.capa}" alt="ícone do usuário" id="icone-usuario">
</div>

<script>
  let toogle = true;
  function abrirMenu() {
    if (toogle) {
      let container = document.getElementById('container');

      let div = document.createElement('div');
      div.id = 'mini-menu';

      let aConta = document.createElement('a');
      aConta.text = 'Conta';
      aConta.href = '${pageContext.request.contextPath}/src/usuarios/minha-conta.jsp'

      let aSair = document.createElement('a');
      aSair.text = 'Sair';
      aSair.href = '${pageContext.request.contextPath}/src/componentes/logoff.jsp';

      let wrapperIcone = document.createElement('div');
      wrapperIcone.id = 'wrapper-icone';

      div.append(aConta);
      div.append(aSair);
      wrapperIcone.append(div);

      container.append(wrapperIcone);
      
    } else {
      let miniMenu = document.querySelector('#mini-menu');
      miniMenu.remove();
    }
    toogle = !toogle;
  }
</script>

<style>
  #wrapper-icone {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  #mini-menu {
    position: absolute;
    display: flex;
    flex-direction: column;
    min-width: 5rem;
    min-height:6rem;
    max-width: 8rem;
    max-height: 9rem;
    border-radius: .5rem;
    justify-content: center;
    align-items: center;
    background-color: rgba(170,173,158);
    z-index: 1000;
  }
</style>