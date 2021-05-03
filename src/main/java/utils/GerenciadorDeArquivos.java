package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class GerenciadorDeArquivos {
  public static boolean uploadImagem (String nomeDoArquivo, InputStream conteudoDoArquivo) throws Exception{
    try {
      String path = "/home/eu/Documentos/Projetos/TCC(parte-prática)/MyBooks/src/main/webapp/imagens";
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
}
