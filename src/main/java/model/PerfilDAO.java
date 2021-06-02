package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PerfilDAO extends DatabaseDAO {
    public PerfilDAO() throws Exception{}
    
    public ArrayList<Perfil> getList() throws Exception {
      ArrayList<Perfil> list = new ArrayList<Perfil>();
      String SQL = "SELECT perfis.* FROM perfis";
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
      
      String SQL = "SELECT * FROM perfis WHERE id=?";
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
      String SQL;
      
      try {
        this.connect();
        if (perfil.getId() == 0) {
          SQL = "INSERT INTO perfis (nome) VALUES (?)";
        } else {
          SQL = "UPDATE perfis SET nome=? WHERE id=?";
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
        e.printStackTrace();
        return false;
      }
    }
    
    public boolean deletar (int id) throws Exception {
      String SQL = "DELETE FROM perfis WHERE id=?";
      
      try {
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
    
    public ArrayList<Menu> getMenusVinculadosPorPerfil (int idPerfil) throws Exception {
      ArrayList<Menu> list = new ArrayList<Menu>();
      String SQL = "SELECT menus.* FROM menus, menus_perfis " +
        "WHERE menus.id = menus_perfis.idMenu AND menus_perfis.idPerfil = ?";
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
      String SQL = "INSERT INTO menus_perfis (idMenu, idPerfil) VALUES (?, ?)";

      try {
        this.connect();
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, idMenu);
        pstm.setInt(2, idPerfil);
        pstm.execute();
        this.disconnect();
  
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    
    public boolean desvincular (int idMenu, int idPerfil) throws Exception {
      String SQL = "DELETE FROM menus_perfis WHERE idMenu=? AND idPerfil=?";
      
      try {
        this.connect();
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, idMenu);
        pstm.setInt(2, idPerfil);
        pstm.execute();
        this.disconnect();
        return true;
        
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
}
