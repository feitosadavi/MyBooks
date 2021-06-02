package model;

public class Horario {
  private int id, status, qtd;
  private String horario;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getQtd() {
    return qtd;
  }

  public void setQtd(int qtd) {
    this.qtd = qtd;
  }

  public String getHorario() {
    return horario;
  }

  public void setHorario(String horario) {
    this.horario = horario;
  }

  @Override
  public String toString() {
    return "Horario{" +
      "id=" + id +
      ", status=" + status +
      ", qtd=" + qtd +
      ", horario='" + horario + '\'' +
      '}';
  }
}
