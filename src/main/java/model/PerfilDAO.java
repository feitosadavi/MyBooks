package model;


import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class PerfilDAO extends DatabaseDAO {
    public PerfilDAO() throws Exception{}
    
    public ArrayList<Perfil> getList() throws Exception {
        ArrayList<Perfil> list = new ArrayList<Perfil>();
        String SQL = "SELECT perfil.* FROM perfil";
        this.connect();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(SQL);
        while (rs.next()) {
            Perfil perfil = new Perfil();
            perfil.setId(rs.getInt("id"));
            perfil.setNome(rs.getString("nome"));
            perfil.setMenus(getMenusVinculadosPorPerfil(rs.getInt("id")));
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
        perfil.setMenus(getMenusVinculadosPorPerfil(id));
      }
      this.disconnect();
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
    
    public ArrayList<Menu> getMenusVinculadosPorPerfil (int idPerfil) throws Exception {
      ArrayList<Menu> list = new ArrayList<Menu>();
      String SQL = "SELECT menu.* FROM menu, menu_perfil " +
        "WHERE menu.id = menu_perfil.idMenu AND menu_perfil.idPerfil = ?";
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setInt(1, idPerfil);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        Menu menu = new Menu();
        menu.setId(rs.getInt("id"));
        menu.setNome(rs.getString("nome"));
        menu.setLink(rs.getString("link"));
        menu.setIcone(rs.getString("icone"));
        menu.setExibir(rs.getInt("exibir"));
        list.add(menu);
      }
      this.disconnect();
      return list;
    }

    public boolean vincular (int idMenu, int idPerfil) throws Exception {
      String SQL = "INSERT INTO menu_perfil (idMenu, idPerfil) VALUES (?, ?)";

      try {
        this.connect();
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, idMenu);
        pstm.setInt(2, idPerfil);
        pstm.execute();
        this.disconnect();
  
        return true;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
    }
    
    public boolean desvincular (int idMenu, int idPerfil) throws Exception {
      String SQL = "DELETE FROM menu_perfil WHERE idMenu=? AND idPerfil=?";
      try {
        this.connect();
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, idMenu);
        pstm.setInt(2, idPerfil);
        pstm.execute();
        this.disconnect();
        return true;
        
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
    }
}
