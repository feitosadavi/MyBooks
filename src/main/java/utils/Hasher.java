package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Hasher {
  public Hasher() {}
  
  public static String criarHash (String senha, byte[] salt, int iteracoes, int larguraDaChaveDerivada) throws Exception {
    try {
      KeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, iteracoes, larguraDaChaveDerivada);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] hash = factory.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(hash);
      
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
