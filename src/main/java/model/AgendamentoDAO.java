//package model;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//public class AgendamentoDAO extends DatabaseDAO {
//  private DataDAO dataDAO;
//  private HorarioDAO horarioDAO;
//  
//  public AgendamentoDAO () throws Exception {}
//  
//  public ArrayList<Agendamento> getList() throws Exception {
//    ArrayList<Agendamento> list = new ArrayList<Agendamento>();
//    String SQL = "SELECT `data`, horario, qtd, " +
//      "d.id as idData, d.status as statusData, " +
//      "h.id as idHorario, h.status as statusHorario " +
//      "FROM datas as d, horarios as h;";
//    this.connect();
//    Statement stm = conn.createStatement();
//    ResultSet rs = stm.executeQuery(SQL);
//    while (rs.next()) {
//      Agendamento agendamento = new Agendamento();
//      agendamento.setIdData(rs.getInt("idData"));
//      agendamento.setIdHorario(rs.getInt("idHorario"));
//      agendamento.setHorario(rs.getString("horario"));
//      agendamento.setHorario(rs.getString("data"));
//      agendamento.setQtdHorario(rs.getInt("qtd"));
//      agendamento.setStatusData(rs.getInt("statusData"));
//      agendamento.setStatusHorario(rs.getInt("statusHorario"));
//      list.add(agendamento);
//    }
//    this.disconnect();
//    return list;
//  }
//
//  public Data getById(String campo, int id ) throws Exception { // idData ou idHorario
//    this.connect();
//    
//    String SQL = "SELECT d.id as idData, d.`data`, d.`status` as statusData,\n" +
//      "h.id as idHorario, h.horario, h.qtd, h.`status` as statusHorarios\n" +
//      "FROM datas as d\n" +
//      "JOIN datas_horarios as dh ON dh.idData = d.id\n" +
//      "JOIN horarios as h ON h.id = dh.idHorario\n" +
//      "WHERE " + campo + ".id = ?;";
//
//    System.out.println(SQL);
//    
//    PreparedStatement pstm = conn.prepareStatement(SQL);
//    pstm.setInt(1, id);
//    
//    ResultSet rs = pstm.executeQuery();
//    
//    Data data = new Data();
//    while (rs.next()) {
//      
//      if (campo.equals("data")) {
//        ArrayList<Horario> horarios = new ArrayList<Horario>();
//        if (data.getId() < 1) {
//          data.setId(rs.getInt("idData"));
//          data.setData(rs.getString("data"));
//          data.setStatus(rs.getInt("statusData"));
//        }
//        
//        if (data.getId() > 0) {
//          Horario horario = new Horario();
//          horario.setId(rs.getInt("idHorario"));
//          horario.setHorario(rs.getString("horario"));
//          horario.setQtd(rs.getInt("qtd"));
//          horario.setStatus(rs.getInt("statusHorario"));
//          
//          horarios.add(horario);
//        }
//        
//        if (data.getHorarios().isEmpty()) data.setHorarios(horarios);
//        
//      }
//
//    }
//    
//    return data;
//  }
//
//  public boolean gravar (Agendamento agendamento) throws Exception {
//    try {
////      this.dataDAO.gravar(agendamento.getClassData());
//      this.horarioDAO.gravar(agendamento.getClassHorario());
//      
//      return true;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return false;
//    }
//  }
//}
