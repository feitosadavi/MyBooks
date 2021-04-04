package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO extends DatabaseDAO {
  public UsuarioDAO() throws Exception{}

  public ArrayList<Usuario> getList() throws Exception {
    ArrayList<Usuario> list = new ArrayList<Usuario>();
    String SQL = "SELECT * FROM usuario";
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
    while (rs.next()) {
      Usuario usuario = new Usuario();
      usuario.setId(rs.getInt("id"));
      usuario.setNome(rs.getString("nome"));
      usuario.setUsername(rs.getString("username"));
      usuario.setSenha(rs.getString("senha"));
      usuario.setStatus(rs.getInt("status"));
      usuario.setIdPerfil(rs.getInt("idPerfil"));

      list.add(usuario);
    }
    this.disconnect();
    return list;
  }

  public Usuario getById(int id) throws Exception {
    Usuario usuario = new Usuario();

    String SQL = "SELECT * FROM usuario WHERE id=?";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    if (rs.next()) {      
      usuario.setId(rs.getInt("id"));
      usuario.setNome(rs.getString("nome"));
      usuario.setUsername(rs.getString("username"));
      usuario.setSenha(rs.getString("senha"));
      usuario.setStatus(rs.getInt("status"));
      usuario.setIdPerfil(rs.getInt("idPerfil"));
    }

    return usuario;
  }

  public boolean gravar (Usuario usuario) throws Exception {
    try {
      String SQL;
      this.connect();
      if (usuario.getId() == 0) {
        SQL = "INSERT INTO usuario (nome, username, senha, status, idPerfil) VALUES (?, ?, ?, ?, ?)";
      } else {
        SQL = "UPDATE usuario SET nome=?, username=?, senha=?, status=?, idPerfil=? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL);

      pstm.setString(1, usuario.getNome());
      pstm.setString(2, usuario.getUsername());
      pstm.setString(3, usuario.getSenha());
      pstm.setInt(4, usuario.getStatus());
      pstm.setInt(5, usuario.getIdPerfil());

      System.out.println(usuario);
      if (usuario.getId() > 0) {
        pstm.setInt(6, usuario.getId());
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
      String SQL = "DELETE FROM usuario WHERE id=?";
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
