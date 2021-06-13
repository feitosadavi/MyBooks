function gerarHorarios() {
  let inicio = converterEmMinutos(document.getElementById('inicio').value);
  let fim = converterEmMinutos(document.getElementById('fim').value);
  let intervalo = document.getElementById('intervalo').value;
  let horarios = calcularHorarios(inicio, fim, Number(intervalo));
  // let diferenca = ((fim.horas*60+fim.minutos) - (inicio.horas*60+inicio.minutos)) / 60;
  
  listarHorarios(converterEmHoras(horarios));

}

// calcula os horarios possíveis entre a abertura e fechamento da escola
function calcularHorarios(inicio, fim, intervalo) {
  let horarios = [inicio];
  while (horarios[horarios.length-1] < fim) {
    horarios.push(horarios[horarios.length-1]+intervalo)
  }
  return horarios;
}

// mostra os horários calculados na tela do usuário
function listarHorarios(horarios) {
  let formsHorarios = document.getElementsByClassName('form-horarios');
  
  let i = 1;
  for (const formHorarios of formsHorarios) {
    let selector = '#form-horarios-' + i + ' .horarios';
    let divHorarios = document.querySelector(selector);
    
    // se tiver horarios, os remove
    if (divHorarios.childElementCount > 0) {
      divHorarios.innerHTML = '';
    }
    for (const horario of horarios) {
      let div = document.createElement('div');
      let label = document.createElement('label');
      let input = document.createElement('input');
      
      div.className = 'horario-wrapper';
      
      label.for = 'horario-' + horario + '-' + formHorarios.parentElement.id;
      label.innerText = horario;
      
      input.type = 'checkbox';
      input.checked = true;
      input.id = 'horario-' + horario + '-' + formHorarios.parentElement.id;
      // input.name = 'horario';
      input.value = i + ',' + horario;
      
      div.append(input);
      div.append(label);
      divHorarios.append(div);
    }
    i++;
  }
}

/*
* funções auxiliares
* */
function converterEmMinutos(tempo) {
  let arrHorario = tempo.split(':');
  let horas = Number(arrHorario[0]);
  let minutos = Number(arrHorario[1]);
  return horas*60+minutos;
}

function converterEmHoras(tempos) { // numero de digitos adicionar zero se tiver só um ;)
  return tempos.map(tempo => {
    let horaInt = tempo/60;
    let hora;
    if (Number.isInteger(horaInt)) {
      hora = horaInt + ':00';
    } else {
      let arr = horaInt.toString().split('.');

      arr[1] = arr[1].toString().length === 1 ? arr[1] * 10 : arr[1];
      
      let parteDecimal = (arr[1]/100)*60;
      hora = arr[0].toString() + ':' + parteDecimal.toString();
    }
    return hora;
  })
}

