package model;


import utils.Distincao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataDAO extends DatabaseDAO {
  public DataDAO() throws Exception{}

  public ArrayList<Data> getList() throws Exception {
    ArrayList<Data> list = new ArrayList<Data>();
    this.connect();

    String SQL = "SELECT d.id as idData, d.`data`, d.`status` as statusData,\n" +
      "h.id as idHorario, h.horario, h.qtd, h.`status` as statusHorario\n" +
      "FROM datas as d\n" +
      "JOIN datas_horarios as dh ON dh.idData = d.id\n" +
      "JOIN horarios as h ON h.id = dh.idHorario;";
    
    Statement stm = conn.createStatement();

    ResultSet rs = stm.executeQuery(SQL);

    while (rs.next()) {
      Data data = new Data();
      data.setId(rs.getInt("idData"));
      data.setData(rs.getString("data"));
      data.setStatus(rs.getInt("statusData"));

    
      Horario horario = new Horario();
      horario.setId(rs.getInt("idHorario"));
      horario.setHorario(rs.getString("horario"));
      horario.setQtd(rs.getInt("qtd"));
      horario.setStatus(rs.getInt("statusHorario"));
        
      
      data.setHorario(horario);
      list.add(data);
    }
    this.disconnect();

    return this.agruparDatas(list);
  }

  public Data getById(int idData) throws Exception {
    this.connect();

    String SQL = "SELECT d.id as idData, d.`data`, d.`status` as statusData,\n" +
      "h.id as idHorario, h.horario, h.qtd, h.`status` as statusHorario\n" +
      "FROM datas as d \n" +
      "JOIN datas_horarios as dh ON dh.idData = d.id \n" +
      "JOIN horarios as h ON h.id = dh.idHorario \n" +
      "WHERE d.id = ?;";

    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, idData);
    ResultSet rs = pstm.executeQuery();

    ArrayList<Data> datas = new ArrayList<Data>();
    while (rs.next()) {
      Data data = new Data();
      data.setId(rs.getInt("idData"));
      data.setData(rs.getString("data"));
      data.setStatus(rs.getInt("statusData"));

      Horario horario = new Horario();
      horario.setId(rs.getInt("idHorario"));
      horario.setHorario(rs.getString("horario"));
      horario.setQtd(rs.getInt("qtd"));
      horario.setStatus(rs.getInt("statusHorario"));
      
      data.setHorario(horario);
      datas.add(data);
    }
    
    this.disconnect();
    
    return this.agruparDatas(datas).get(0);
  }
  
  private ArrayList<Data> agruparDatas(ArrayList<Data> lista) {
    ArrayList<Data> datasDistintas = (ArrayList<Data>) lista.stream()
      .filter( Distincao.distinguirPelaChave(Data::getId) )
      .collect(Collectors.toList());

    lista.forEach(data -> {
      datasDistintas.forEach(dataDistinta -> {
        if (data.getId() == dataDistinta.getId()) {
          dataDistinta.addHorario(data.getHorario());
        }
      });
    });
    
    return datasDistintas;
  }
  
  public boolean gravar (Data data) throws Exception {
    try {
      String SQL;
      this.connect();
      if (data.getId() == 0) {
        SQL = "INSERT INTO datas (data, status) VALUES (?, ?);";
      } else {
        SQL = "UPDATE datas SET data=?, status=? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

      pstm.setString(1, data.getData());
      pstm.setInt(2, data.getStatus());
      
      if (data.getId() > 0) {
        pstm.setInt(3, data.getId());
      }
      pstm.execute();

      ResultSet rs = pstm.getGeneratedKeys();
      if (rs.next()) {
        for (Horario horario: data.getHorarios()) {
          this.vincularDataHorario(rs.getInt(1), horario.getId());
        }
      }
      
      this.disconnect();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public void vincularDataHorario (int idData, int idHorario) throws Exception {
    String SQL = "INSERT INTO datas_horarios (idHorario, idData) VALUES (?, ?)";

    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, idData);
    pstm.setInt(2, idHorario);
    pstm.execute();
    this.disconnect();
  }
  
//
//  public boolean deletar (int id) throws Exception {
//    try {
//      String SQL = "DELETE FROM datas WHERE id=?";
//      this.connect();
//      PreparedStatement pstm = conn.prepareStatement(SQL);
//      pstm.setInt(1, id);
//      pstm.execute();
//      this.disconnect();
//
//      return true;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return false;
//    }
//  }
}
