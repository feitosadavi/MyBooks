<div id="container" onclick="abrirMenu()">
  <img src="${pageContext.request.contextPath}/imagens/fotosUsuario/${sessionScope.ulogado.capa}" alt="�cone do usu�rio" class="icone-usuario-navbar">
</div>

<script>
  let toogle = true;
  function abrirMenu() {
    if (toogle) {
      let container = document.getElementById('container');

      let div = document.createElement('div');
      div.id = 'mini-menu';

      let aConta = document.createElement('a');
      aConta.text = 'CONTA';
      aConta.href = '${pageContext.request.contextPath}/src/usuarios/minha-conta.jsp'

      let aSair = document.createElement('a');
      aSair.text = 'SAIR';
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
    top: .3rem;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    min-width: 5rem;
    min-height:6rem;
    max-width: 8rem;
    max-height: 9rem;
    border-radius: .5rem;
    align-items: center;
    border: 1px solid var(--roxo-claro);
    background-color: #fff;
    z-index: 99999;
  }
  #mini-menu a {
    color: var(--preto-fosco);
    text-decoration: none;
  }
</style>