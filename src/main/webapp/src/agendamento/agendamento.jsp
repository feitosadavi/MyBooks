<%@ page import="java.util.Date" %>
<%@ page import="java.time.YearMonth" %>
<%@page contentType="text/html"
        pageEncoding="UTF-8"
%>
<%@ taglib
  prefix="c"
  uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../componentes/head.jsp"
%>


<body>

<%@include file="../componentes/navbar.jsp"%>
<link href="${pageContext.request.contextPath}/src/estilo/agendamento.css" rel="stylesheet">

<div class="calendario__wrapper d-flex">
  <div class="calendario">
    <div class="sidebar">
      <div class="slider">
        <div class="slides">
            <%
              Date date = new Date(); 
              int hoje = date.getDate();
              YearMonth yearMonthObject = YearMonth.of(date.getYear(), date.getMonth()+1);
              int ultimoDiaDoMes = yearMonthObject.lengthOfMonth();
              pageContext.setAttribute("hoje", hoje);
              pageContext.setAttribute("ultimoDiaDoMes", ultimoDiaDoMes);
            %>  
          <c:forEach var="dia" begin="${hoje}" end="${ultimoDiaDoMes}">
            <div id="slide-${dia}">
              <a>${dia}</a>
              <button onclick="enviar()" class="btn btn-success">Enviar</button>
              <div class="form-horarios">
                <form id="form-horarios-${dia}" method="POST" action="gerenciar_agendamento.do">
                  <div class="horarios d-flex flex-column">
                    <c:forEach var="horario" items="${sessionScope.horarios}">
                      <div class="horario-wrapper">
                        <label for="horario-${horario}-slide-${dia}">${horario}</label>
                        <input type="checkbox" name="horario" value="${[dia, horario]}" id="horario-${horario}-slide-${dia}">
                      </div>
                    </c:forEach>
                  </div>
                </form>
              </div>
            </div>
            
          </c:forEach>
        </div>
      </div>
    </div>


    <div class="calendario__main">
      <div onclick="selecionarTodos()" class="p-3">
        <label for="todos">Selecionar todos</label>
        <input type="checkbox"
               id="todos">
      </div>

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
  </div>
  
  <div class="configuracoes d-flex flex-column justify-self-end">
    <label>Selecione o horário inicial e final: </label>
    <div class="d-flex justify-content-between">
      <input type="time" value="10:00:00" id="inicio"> <!--dps tire o horários, pls-->
      <input type="time" value="12:00:00" id="fim">
    </div>
    
    <label for="intervalo">Selecione o intervalo entre os horários: </label>
    <input id="intervalo" value="30" placeholder="Ex: 30min">
    <button onclick="gerarHorarios()" class="btn btn-primary">Enviar</button>
  </div>

</div>

<script src="${pageContext.request.contextPath}/scripts/calendario.js"></script>
<script src="${pageContext.request.contextPath}/scripts/horarios.js"></script>
<script src="${pageContext.request.contextPath}/scripts/enviar.js"></script>

<script>

  let dias = document.getElementsByClassName('dia');
  let urlParams = new URLSearchParams(window.location.search); // parâmetros da url
  let mes = urlParams.get('mes') ? urlParams.get('mes') : date.getMonth() + 1 ;
  let primeiroDiaDoMes = getPrimeiroDiaDoMes(mes);
  let mesAnterior = Number(mes) === 0 ? 11 : mes - 1;
  let data = new Date();
  let diasMesAnterior = getDiasEmUmMes(data.getFullYear(), mesAnterior);
  let diasDoMes = getDiasEmUmMes(data.getFullYear(), mes);

  for (let dia of dias) {
    dia.ondblclick = selecionarUm;
  }
  comecarNo(primeiroDiaDoMes, dias, diasDoMes);
  preencherAntes(primeiroDiaDoMes, diasMesAnterior);
  
  
</script>

</body>

</html>