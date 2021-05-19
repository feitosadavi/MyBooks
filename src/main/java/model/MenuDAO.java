package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuDAO extends DatabaseDAO {
  public MenuDAO() throws Exception{}

  public ArrayList<Menu> getList() throws Exception {
    ArrayList<Menu> list = new ArrayList<Menu>();
    String SQL = "SELECT * FROM menus";
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
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

  public Menu getById(int id) throws Exception {
    Menu menu = new Menu();

    String SQL = "SELECT * FROM menus WHERE id=?";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    if (rs.next()) {
      menu.setId(rs.getInt("id"));
      menu.setNome(rs.getString("nome"));
      menu.setLink(rs.getString("link"));
      menu.setIcone(rs.getString("icone"));
      menu.setExibir(rs.getInt("exibir"));
    }

    return menu;
  }

  public boolean gravar (Menu menu) throws Exception {
    try {
      String SQL;
      this.connect();
      if (menu.getId() == 0) {
        SQL = "INSERT INTO menus (nome, link, icone, exibir) VALUES (?, ?, ?, ?)";
      } else {
        SQL = "UPDATE menus SET nome=?, link=?, icone=?, exibir=? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL);

      pstm.setString(1, menu.getNome());
      pstm.setString(2, menu.getLink());
      pstm.setString(3, menu.getIcone());
      pstm.setInt(4, menu.getExibir());

      if (menu.getId() > 0) {
        pstm.setInt(5, menu.getId());
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
      String SQL = "DELETE FROM menus WHERE id=?";
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
}
