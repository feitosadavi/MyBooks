package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PerfilDAO extends DatabaseDAO {
    public PerfilDAO() throws Exception{}
    
    public ArrayList<Perfil> getList() throws Exception {
        ArrayList<Perfil> list = new ArrayList<Perfil>();
        String SQL = "SELECT * FROM perfil";
        this.connect();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(SQL);
        while (rs.next()) {
            Perfil perfil = new Perfil();
            perfil.setId(rs.getInt("id"));
            perfil.setNome(rs.getString("nome"));
            list.add(perfil);
        }
        this.disconnect();
        return list;
    }
    
    public Perfil getById(int id) throws Exception {
      Perfil perfil = new Perfil();
      
      String SQL = "SELECT * FROM perfil WHERE id=?";
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setInt(1, id);
      
      ResultSet rs = pstm.executeQuery();
      if (rs.next()) { // se achou alguém
        perfil.setId(rs.getInt("id"));
        perfil.setNome(rs.getString("nome"));
      }

      return perfil;
    }
    
    public boolean gravar (Perfil perfil) throws Exception {
      try {
        String SQL;
        this.connect();
        if (perfil.getId() == 0) {
          SQL = "INSERT INTO perfil (nome) VALUES (?)";
        } else {
          SQL = "UPDATE perfil SET nome=? WHERE id=?";
        }
        PreparedStatement pstm = conn.prepareStatement(SQL);
        
        pstm.setString(1, perfil.getNome());
        if (perfil.getId() > 0) {
          pstm.setInt(2, perfil.getId());
        }
        pstm.execute();
        this.disconnect();
        return true;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
    }
    
    public boolean deletar (int id) throws Exception {
      try {
        String SQL = "DELETE FROM perfil WHERE id=?";
        this.connect();
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, id);
        pstm.execute();
        this.disconnect();
        
        return true;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
    }
}
