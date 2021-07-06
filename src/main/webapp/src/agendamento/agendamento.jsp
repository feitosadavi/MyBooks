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

<div class="calendario__wrapper d-flex flex-column justify-content-start align-items-center">
  <div class="configuracoes mt-5 d-flex align-items-center justify-content-between gap-4">
    <div class="d-flex gap-4">
      <div class="d-flex flex-column">
        <label class="text-secondary" for="inicio">HORÁRIO INICIAL</label>
        <input type="time" value="10:00:00" class="form-control" id="inicio"> <!--dps tire o horários, pls-->
      </div>
      
      <div class="d-flex flex-column">
        <label class="text-secondary" for="fim">HORÁRIO FINAL</label>
        <input type="time" value="23:00:00" class="form-control" id="fim">
      </div>
    </div>

    <div class="d-flex">
      <div class="d-flex flex-column">
        <label class="text-secondary" for="intervalo">INTERVALO </label>
        <input class="form-control" id="intervalo" value="15" placeholder="Ex: 30min">
      </div>
      <button onclick="gerarHorarios()" class="btn btn-outline-mybooks h-50 mt-3 ms-3">Ok</button>
    </div>
  </div>
  
  <div class="calendario mt-5">
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
              <div class="form-horarios">
                <button onclick="enviar()" class="btn btn-outline-info">Enviar</button>
                <form id="form-horarios-${dia}" method="POST" action="${pageContext.request.contextPath}/gerenciar_agendamento.do">
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
               id="todos"
               class="form-check-inline">
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

</div>

<script src="${pageContext.request.contextPath}/scripts/calendario.js"></script>
<script src="${pageContext.request.contextPath}/scripts/horarios.js"></script>
<script src="${pageContext.request.contextPath}/scripts/enviar.js"></script>

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
    dia.ondblclick = selecionarUm;
  }
  console.log("Primeiro dia do mes: ", primeiroDiaDoMes)
  
  comecarNo(primeiroDiaDoMes, dias, diasDoMes);
  preencherAntes(primeiroDiaDoMes, diasMesAnterior);
  
  
</script>

</body>

</html>