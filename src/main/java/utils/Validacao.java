package utils;

import java.util.Stack;

public class Validacao {
  public Validacao() {}
  public Stack<String> camposRequeridos(String[] fieldNames, String[] fields) {
    int i = 0;
    Stack<String> camposNencontrados = new Stack<String>();
    for (String field : fields) {

      if (field.trim().length() < 1) { // se o campo for vazio, eu adiciona à pilha de campos não encontrados
        camposNencontrados.push(fieldNames[i]);
      } 
      i++;
    }
    return camposNencontrados;
  }
}
