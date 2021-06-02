package model;

import java.util.ArrayList;

public class Data {
  private int id, status;
  private String data;
  private Horario horario;
  private ArrayList<Horario> horarios = new ArrayList<Horario>();

  public Horario getHorario() {
    return horario;
  }

  public void setHorario(Horario horario) {
    this.horario = horario;
  }

  public ArrayList<Horario> getHorarios() {
    return this.horarios;
  }
  
  public void addHorario(Horario horario) {
    this.horarios.add(horario);
  }

  public void setHorarios(ArrayList<Horario> horarios) {
    this.horarios = horarios;
  }

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

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Data{" +
      "id=" + id +
      ", status=" + status +
      ", data='" + data + '\'' +
      ", horario=" + horario +
      ", horarios=" + horarios +
      '}';
  }
}
