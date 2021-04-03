public class Validation {
  private String...fields;
  public Validacao(String...fields) {
    
  }
  public boolean camposRequeridos(String...fields) {
    boolean retorno = fields.forEach(field -> field.isEmpty());
    return retorno;
  }
}