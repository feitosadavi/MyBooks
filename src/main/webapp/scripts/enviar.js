// Só será possível definir datas para o mes atual
function enviar() {
  let dias = document.getElementsByClassName('dia__ativo');
  let horarios = document.querySelectorAll('.horario-wrapper > input');

  let agendamentos = {};
  horarios.forEach((horario, i, j) => {

    let arrValues = horario.value.split(',');
    let key = arrValues[0];
    let value = arrValues[1];

    if (Number(key) === 1) { // eu só preciso adicionar os horários existentes uma vez
      if (agendamentos.horariosTotais) {
        agendamentos.horariosTotais.push(value);
      } else {
        agendamentos.horariosTotais = [value];
      }
    }
    
    if (agendamentos[key]) {
      horario.checked === true && agendamentos[key].horariosDisponiveis.push(value);
    } else {
      agendamentos[key] = horario.checked === true && {horariosDisponiveis: [value]};
    }
  })

  for (let dia of dias) {
    let key = dia.innerText[0] === '0' ? dia.innerText.replace('0', '') : dia.innerText;
    agendamentos[key].status = dia.className.includes('selecionado') ? 1 : 0
  }
  
  let xmlHttp = new XMLHttpRequest();
  xmlHttp.open('GET', 'gerenciar_agendamento.do?acao=dias');
  xmlHttp.send(JSON.stringify(agendamentos));
}