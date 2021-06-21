<%@ page import="model.Data" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.YearMonth" %>
<%@ page import="model.Horario" %>
<jsp:useBean class="model.DataDAO" id="dataDAO"/>

<%
  Date date = new Date();
  YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
  int diasNoMes = yearMonth.lengthOfMonth();
  int horaAtual = Integer.parseInt("" + date.getHours() + date.getMinutes());

  pageContext.setAttribute("diasNoMes", diasNoMes);
  pageContext.setAttribute("horaAtual", horaAtual);
%>


<div id="agendamento" class="calendario flex-column" style="display: none">
  <div class="sidebar d-flex flex-column">
    <div class="slider">
      <div class="slides">
        <c:forEach var="dia" begin="1" end="${diasNoMes}">
          <%
            Data data = new Data();

            try {
              int dia = (int) pageContext.getAttribute("dia");
              data = dataDAO.getPorData(dia + "");
            }   catch (Exception ignored) {}
            pageContext.setAttribute("horarios", data.getHorarios());
          %>
          
          <div class="d-flex align-items-center justify-content-center gap-4" id="slide-${dia}">
            <div class="slide-dia">
              <a>${dia}</a>
            </div>

            <div class="d-flex justify-content-center align-items-center">
              <form class="d-flex justify-content-center align-items-center" id="form-horarios-${dia}" method="POST" action="gerenciar_agendamento.do">
                <label for="horario-${horario}-slide-${dia}">${horario}</label>
                <select class="mybooks-form-select">
                  <c:forEach var="horario" items="${horarios}">
                    <%
                      Horario horario = (Horario) pageContext.getAttribute("horario");
                      String[] partesHorario = horario.getHorario().split(":");
                      int somaHorario = Integer.parseInt( "" + partesHorario[0] + partesHorario[1]);
                      pageContext.setAttribute("somaHorario", somaHorario);
                    %>
                    
                    <c:if test="${horaAtual <= somaHorario}">
                      <option value="${horario.horario}">${horario.horario}</option>
                    </c:if>
                  </c:forEach>
                </select>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>


  <div class="calendario__main">
    <nav class="calendario__nav">
      <div class="meses">
        <a href="?mes=1"
           class="mes">Jan</a>
        <a href="?mes=2"
           class="mes">Fev</a>
        <a href="?mes=3"
           class="mes">Mar</a>
        <a href="?mes=4"
           class="mes">Abr</a>
        <a href="?mes=5"
           class="mes">Mai</a>
        <a href="?mes=6"
           class="mes">Jun</a>
        <a href="?mes=7"
           class="mes">Jul</a>
        <a href="?mes=8"
           class="mes">Ago</a>
        <a href="?mes=9"
           class="mes">Set</a>
        <a href="?mes=10"
           class="mes">Out</a>
        <a href="?mes=11"
           class="mes">Nov</a>
        <a href="?mes=12"
           class="mes">Dez</a>
      </div>

      <hr />

      <div class="dias__semana">
        <p>SEG</p>
        <p>TER</p>
        <p>QUA</p>
        <p>QUI</p>
        <p>SEX</p>
        <p>SAB</p>
        <p>DOM</p>
      </div>
    </nav>
    
    <c:forEach begin="1" end="6">
      <div class="semana">
        <c:forEach begin="1" end="7">
          <div class="dia"><a></a></div>
        </c:forEach>
      </div>
    </c:forEach>
  </div>

  <a onclick="confirmar()" class="btn btn-cadastro mt-3 px-5 py-2">
    Confirmar
  </a>
</div>

<script src="${pageContext.request.contextPath}/scripts/calendario.js"></script>
<script src="${pageContext.request.contextPath}/scripts/horarios.js"></script>

<script>
  let dias = document.getElementsByClassName('dia');
  let urlParams = new URLSearchParams(window.location.search); // parâmetros da url
  let mes = urlParams.get('mes');
  let primeiroDiaDoMes = getPrimeiroDiaDoMes(mes);
  let mesAnterior = Number(mes) === 0 ? 11 : mes - 1;
  let data = new Date();
  let diasMesAnterior = getDiasEmUmMes(data.getFullYear(), mesAnterior);
  let diasDoMes = getDiasEmUmMes(data.getFullYear(), mes);

  for (let dia of dias) {
    dia.setAttribute("data-click", "false");
    dia.addEventListener('click', (e) => {
      document.addEventListener('click', (e) => {
        if (e.target.parentNode.className.includes('dia') && e.target !== dia) {
          let clicouDentro = dia.contains(e.target);
          if (clicouDentro) {
            dia.className += " selecionado";
          } else {
            if (dia.className.includes('dia__inativo')) {
              dia.className = "dia dia__inativo"
            } else {
              dia.className = "dia dia__ativo"
            }
          }
        }
      })
      
    })
  }
  
  function confirmar() {
    let url = window.location.href; // parâmetros da url
    
    let slide = url.split('#')[1];

    if (!slide) {
      alert ("Selecione um dia para efetuar a locação!");
      return null;
    }

    let elementoSlide = document.getElementById(slide);
    let horarioColeta = elementoSlide.querySelector('select').value;
    let dia = elementoSlide.querySelector('.slide-dia').innerText;
    
    // coloco o 0 se o número tiver apenas um algarismo
    dia = dia.length === 1 ? '0' + dia : dia;
    let mesNaoFormatado = data.getMonth()+1;
    let mes = data.getMonth().toString().length === 1 ? '0' + mesNaoFormatado : mesNaoFormatado  ;

    let dataColeta = data.getFullYear() + '-' + mes + '-' + dia;
    
    let locacao = {dataColeta, horarioColeta, livrosId: []};
    
    for (let id of ${sessionScope.carrinho}) {
      locacao.livrosId.push(id);
    }
    enviar(locacao);
  }
  
  function enviar(locacao) {
    const url = 'http://localhost:8080/projetojava3_war_exploded/gerenciar_locacao.do';
    (async () => {
      await fetch(url, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(locacao)
      });

    })();
  }
  
  comecarNo(primeiroDiaDoMes, dias, diasDoMes);
  preencherAntes(primeiroDiaDoMes, diasMesAnterior);

</script>
