package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Validacao {
  public Validacao() {}
  public ArrayList<String> camposRequeridos(String[] camposObrigatorios, String[] camposDoUsuario) {
    int i = 0;
    ArrayList<String> camposNencontrados = new ArrayList<>();
    for (String campoDoUsuario : camposDoUsuario) {
      System.out.println(Arrays.toString(camposDoUsuario));
      if (campoDoUsuario != null && campoDoUsuario.trim().length() < 1) { // se o campo for vazio, eu adiciona à pilha de campos não encontrados
        camposNencontrados.add(camposObrigatorios[i]);
      } 
      i++;
    }
    return camposNencontrados;
  }
}
