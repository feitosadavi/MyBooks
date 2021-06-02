package model;

public class Agendamento {
  private Horario horario;
  private Data data;
  
  public Agendamento(Horario horario, Data data) {
    this.horario = horario;
    this.data = data;
  }
  
  public Horario getClassHorario() {
    return this.horario;
  }

  public void setClassHorario(Horario horario) {
    this.horario = horario;
  }

  public Data getClassData() {
    return this.data;
  }

  public void setClassData(Data data) {
    this.data = data;
  }
  
}
