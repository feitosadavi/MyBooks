package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LivroDAO extends DatabaseDAO {
  public LivroDAO() throws Exception{}

  public ArrayList<Livro> getList() throws Exception {
    ArrayList<Livro> list = new ArrayList<Livro>();
    String SQL = "SELECT * FROM livros";
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
    while (rs.next()) {
      Livro livro = new Livro();
      livro.setId(rs.getInt("id"));
      livro.setNome(rs.getString("nome"));
      livro.setGenero(rs.getString("genero"));
      livro.setPaginas(rs.getInt("paginas"));
      livro.setLancamento(rs.getInt("lancamento"));
      livro.setEstoque(rs.getInt("estoque"));
      livro.setCapa(rs.getString("capa"));

//      PerfilDAO perfilDAO = new PerfilDAO();
//      Perfil perfil = perfilDAO.getById(rs.getInt("idPerfil"));
//      livro.setPerfil(perfil);

      list.add(livro);
    }
    this.disconnect();
    return list;
  }

  public Livro getById(int id) throws Exception {
    Livro livro = new Livro();
    String SQL = "SELECT * FROM livros WHERE id=?";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    if (rs.next()) {
      livro.setId(rs.getInt("id"));
      livro.setNome(rs.getString("nome"));
      livro.setGenero(rs.getString("genero"));
      livro.setPaginas(rs.getInt("paginas"));
      livro.setLancamento(rs.getInt("lancamento"));
      livro.setEstoque(rs.getInt("estoque"));
      livro.setCapa(rs.getString("capa"));  
    }
    this.disconnect();

    return livro;
  }

  public boolean gravar (Livro livro) throws Exception {
    try {
      String SQL;
      this.connect();
      if (livro.getId() == 0) {
        SQL = "INSERT INTO livros (nome, lancamento, genero, estoque, paginas, capa) VALUES (?, ?, ?, ?, ?, ?)";
      } else {
        SQL = "UPDATE livros SET nome = ?,  lancamento = ?, genero = ?, estoque = ?, paginas = ?, capa = ? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL);

      pstm.setString(1, livro.getNome());
      pstm.setInt(2, livro.getLancamento());
      pstm.setString(3, livro.getGenero());
      pstm.setInt(4, livro.getEstoque());
      pstm.setInt(5, livro.getPaginas());
      pstm.setString(6, livro.getCapa());
      if (livro.getId() > 0) {
        pstm.setInt(7, livro.getId());
      }
      pstm.execute();
      this.disconnect();
      
      return true;
      
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean atualizarEstoque (int estoque, int livroId) throws Exception {
    try {
      String SQL;
      this.connect();
      SQL = "UPDATE livros SET estoque = ? WHERE id=?";
      PreparedStatement pstm = conn.prepareStatement(SQL);
      
      pstm.setInt(1, estoque);
      pstm.setInt(2, livroId);
      pstm.execute();
      this.disconnect();

      return true;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deletar (int id) throws Exception {
  	String SQL = "DELETE FROM livros WHERE id=?";
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
}
