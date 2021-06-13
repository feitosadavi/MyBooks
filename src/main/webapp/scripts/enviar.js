// Só será possível definir datas para o mes atual
function enviar() {
  let dias = document.getElementsByClassName('dia__ativo');
  let horarios = document.querySelectorAll('.horario-wrapper > input');

  let agendamentos = {dias:[]};
  horarios.forEach((horario) => {
    let arrValues = horario.value.split(',');
    let dia = arrValues[0];
    let hora = arrValues[1];

    // configuro todos os horários possíveis
    if (Number(dia) === 1) { // eu só preciso adicionar os horários existentes uma vez
      if (agendamentos.horariosTotais) {
        agendamentos.horariosTotais.push(hora);
      } else {
        agendamentos.horariosTotais = [hora];
      }
    }
    
    // configuro os dias
    if (agendamentos.dias[dia]) {
      horario.checked === true && agendamentos.dias[dia].horariosDisponiveis.push(hora);
    } else {
      agendamentos.dias[dia] = horario.checked === true && {horariosDisponiveis: [hora], dia: dia};
    }
  })

  
  // informo no objeto se o dia está disponível ou não
  for (let diaElemento of dias) {
    let dia = diaElemento.innerText[0] === '0' ? diaElemento.innerText.replace('0', '') : diaElemento.innerText;
    agendamentos.dias[dia].status = diaElemento.className.includes('selecionado') ? 1 : 0
  }

  // agendamentos.forEach(agendamento => console.log(agendamento));
  
  const url = 'http://localhost:8080/projetojava3_war_exploded/gerenciar_agendamento.do?acao=dias';
  (async () => {
    const rawResponse = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(agendamentos)
    });

  })();
}