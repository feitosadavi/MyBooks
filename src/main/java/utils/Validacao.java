package utils;

import java.util.ArrayList;
import java.util.Stack;

public class Validacao {
  //private String[] fields;
  public Validacao() {}
  public Stack<String> camposRequeridos(String[] fieldNames, String[] fields) {
    int i = 0;
    Stack<String> camposNencontrados = new Stack<String>();
    for (String field : fields) {

      if (field.trim().length() < 1) { // se for vazio ou só tiver espaços
        camposNencontrados.push(fieldNames[i]);
      } 
      i++;
    }
    return camposNencontrados;
  }
}
