/*
* EVENT LISTNERS
* */

  let date = new Date();


/*
* FUNCÕES PARA A CONSTRUÇÃO DO CALENDÁRIO
* */

// determina o dia da semana que o mês deve começar no calendário
function comecarNo(dia, dias, diasDoMes) {
  let diaSemana = 1;
  dia = dia === 0 ? 7 : dia; // A classe Date do js define o domingo como 0, mas no html o domingo é o º7 elemento

  for (let i = 0; i < diasDoMes + dia; i++) {
    if (i === dia){
      preencherAntes(dia);
    }

    let index = i === 0 ? 0 : i - 1;
    if (i >= dia) {
      let diaA = dias[index].querySelector('a');
      diaA.innerText = diaSemana < 10 ? '0' + diaSemana : diaSemana;
      diaA.href = '#slide-' + diaSemana;
      dias[index].className += ' dia__ativo';
      diaSemana++;
    }
  }
}

// determina quais números deve ser inseridos antes do dia inicial
function preencherAntes(primeiroDiaDoMes, diasMesAnterior) {
  let diasAnteriores = [];
  let espacos = primeiroDiaDoMes === 0 ? 6 : primeiroDiaDoMes  - 1; // número de espaços anteriores ao dia inicial
  let i = primeiroDiaDoMes === 0 ? 6 : primeiroDiaDoMes; // se for domingo
  for ( i >= 0; i--;) {
    diasAnteriores.push(diasMesAnterior - i);
  }
  
  let diasAnterioresSliced = diasAnteriores.splice(diasAnteriores.length - espacos);
  for (const [i, diaAnterior] of diasAnterioresSliced.entries()) {
    dias[i].innerText = diaAnterior;
    dias[i].className += ' dia__inativo';
  }
}

// determina quais números deve ser inseridos depois do dia final
function preencherDepois() {

}




/*
* FUNCÕES PARA A MANIPULAÇÃO DO CALENDÁRIO
* */

function selecionarUm() {
  if (Number(mes) !== data.getMonth()) alert('Só é possível definir agendamento para o mês atual');

  if (this.className.includes('dia__inativo')) return;
  if (this.className.includes('selecionado')) {
    this.className = 'dia dia__ativo';
  } else {
    this.className += ' selecionado';
  }
}

function selecionarTodos() {
  if (Number(mes) !== data.getMonth()) alert('Só é possível definir agendamento para o mês atual');

  let checked = document.getElementById("todos").checked;
  if (!checked) {
    for (let dia of dias) {
      if (dia.className.includes('selecionado')) {
        dia.className = 'dia dia__ativo';
      }
    }
  } else {
    for (let dia of dias) {
      if (dia.className.includes('dia__ativo')) {
        dia.className += ' selecionado';
      }
    }
  }
}

/*
* FUNCÕES AUXILIARES
* */

// retorna todos os dias que um mês específico possui
function getDiasEmUmMes(ano, mes) {
  return new Date(ano, mes, 0).getDate();
}

function getPrimeiroDiaDoMes(mes) {
  return new Date(date.getFullYear(), mes - 1, 1).getDay()
}