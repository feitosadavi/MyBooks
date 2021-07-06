package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HorarioDAO extends DatabaseDAO {
  public HorarioDAO() throws Exception{}

  public ArrayList<Horario> getList() throws Exception {
    ArrayList<Horario> list = new ArrayList<Horario>();
    String SQL = "SELECT * FROM horarios";
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
    while (rs.next()) {
      Horario horario = new Horario();
      horario.setId(rs.getInt("id"));
      horario.setHorario(rs.getString("horario"));
      horario.setQtd(rs.getInt("qtd"));
      horario.setStatus(rs.getInt("status"));

      list.add(horario);
    }
    this.disconnect();
    return list;
  }

  public Horario getPorNome(String horarioNome) throws Exception {
    Horario horario = new Horario();
    
    String SQL = "SELECT * FROM horarios WHERE horario=" + horarioNome;
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
    if (rs.next()) {
      horario.setId(rs.getInt("id"));
      horario.setHorario(rs.getString("horario"));
      horario.setQtd(rs.getInt("qtd"));
      horario.setStatus(rs.getInt("status"));
    }
    this.disconnect();

    return horario;
  }

  public boolean gravar (Horario horario) throws Exception {
    try {
      String SQL;
      this.connect();
      if (horario.getId() == 0) {
        SQL = "INSERT INTO horarios (horario, status, qtd) VALUES (?, ?, ?)";
      } else {
        SQL = "UPDATE horarios SET horario=?, status=?, qtd=? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL);

      pstm.setString(1, horario.getHorario());
      pstm.setInt(2, horario.getStatus());
      pstm.setInt(3, horario.getQtd());

      if (horario.getId() > 0) {
        pstm.setInt(4, horario.getId());
      }
      pstm.execute();
      this.disconnect();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deletar (int id) throws Exception {
    try {
      String SQL = "DELETE FROM horarios WHERE id=?";
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setInt(1, id);
      pstm.execute();
      this.disconnect();

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  private void deletarDatasHorarios() throws Exception {
    try {
      String SQL = "DELETE FROM datas_horarios;";

      this.connect();
      Statement stm = conn.prepareStatement(SQL);
      stm.execute(SQL);
      
      this.disconnect();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deletarTodos () throws Exception {
    try {
      this.deletarDatasHorarios();
      
      String SQL = "DELETE FROM horarios;";
      this.connect();
      PreparedStatement stm = conn.prepareStatement(SQL);
      stm.execute(SQL);
      this.disconnect();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  }
