package utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;

public class GerenciadorDeArquivos {
  public static boolean uploadImagem (String nomeDoArquivo, InputStream conteudoDoArquivo) throws Exception{
    try {
      String path = "/home/eu/Documentos/Projetos/TCC(parte-prática)/MyBooks/src/main/webapp/imagens/fotosUsuario/";
      OutputStream out = new FileOutputStream(path + File.separator + nomeDoArquivo);

      int read = 0;
      final byte[] bytes = new byte[1024];

      while ((read = conteudoDoArquivo.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      return true;
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public static void procurarArquivo (String nomeDoArquivo, HttpServletRequest request) throws Exception {
    File diretorio = new File(request.getContextPath() + "/imagens/foto-usuario");
    
    File[] listaFiles = diretorio.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().equals(nomeDoArquivo);
      }
    });
    // if true capa else nomeDoArquivo
    System.out.println(Arrays.toString(listaFiles));
  }
}
