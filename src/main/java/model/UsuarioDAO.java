package model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDAO extends DatabaseDAO {
  public UsuarioDAO() throws Exception{}

  public ArrayList<Usuario> getList() throws Exception {
    ArrayList<Usuario> list = new ArrayList<Usuario>();
    String SQL = "SELECT * FROM usuarios";
    this.connect();
    Statement stm = conn.createStatement();
    ResultSet rs = stm.executeQuery(SQL);
    while (rs.next()) {
      Usuario usuario = new Usuario();
      usuario.setId(rs.getInt("id"));
      usuario.setNome(rs.getString("nome"));
      usuario.setEmail(rs.getString("email"));
      usuario.setSenha(rs.getString("senha"));
      usuario.setStatus(rs.getInt("status"));
      usuario.setMatricula(rs.getInt("matricula"));
      usuario.setCapa(rs.getString("capa"));


      PerfilDAO perfilDAO = new PerfilDAO();
      Perfil perfil = perfilDAO.getById(rs.getInt("idPerfil"));
      usuario.setPerfil(perfil);

      list.add(usuario);
    }
    this.disconnect();
    return list;
  }

  public Usuario getById(int id) throws Exception {
    Usuario usuario = new Usuario();
    String SQL = "SELECT * FROM usuarios WHERE id=?";
    
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    if (rs.next()) {
      usuario.setId(rs.getInt("id"));
      usuario.setNome(rs.getString("nome"));
      usuario.setEmail(rs.getString("email"));
      usuario.setSenha(rs.getString("senha"));
      usuario.setStatus(rs.getInt("status"));
      usuario.setMatricula(rs.getInt("matricula"));
      usuario.setCapa(rs.getString("capa"));
      
      PerfilDAO perfilDAO = new PerfilDAO();
      Perfil perfil = perfilDAO.getById(rs.getInt("idPerfil"));
      usuario.setPerfil(perfil);    
    }
    this.disconnect();
    return usuario;
  }

  public Map<String, Object> getLivrosLocados(int id) throws Exception {
    String SQL = "SELECT livros.*, " +
      "u.status as statusUsuario, u.id as idUsuario, " +
      "l.id as idLocacao, l.dataDevolucao, l.status as statusLocacao " +
      "FROM livros " +
      "JOIN usuarios as u ON u.id = ? " +
      "JOIN locacoes as l ON l.idUsuario = u.id " +
      "JOIN locacoes_livros as ll ON livros.id = ll.idLivros;";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    ArrayList<Livro> livros = new ArrayList<>();
    ArrayList<Locacao> locacoes = new ArrayList<>();
    
    Usuario usuario = new Usuario();
    while (rs.next()) {
      Livro livro = new Livro();
      livro.setId(rs.getInt("id"));
      livro.setNome(rs.getString("nome"));
      livro.setGenero(rs.getString("genero"));
      livro.setPaginas(rs.getInt("paginas"));
      livro.setLancamento(rs.getInt("lancamento"));
      livro.setEstoque(rs.getInt("estoque"));
      livro.setCapa(rs.getString("capa"));
      livros.add(livro);
      
      usuario.setId(rs.getInt("idUsuario"));
      usuario.setStatus(rs.getInt("statusUsuario"));

      Locacao locacao = new Locacao();
      locacao.setId(rs.getInt("idLocacao"));
      locacao.setDataDevolucao(rs.getDate("dataDevolucao"));
      locacao.setStatus(rs.getString("statusLocacao"));
      locacoes.add(locacao);
    }
    this.disconnect();
    
    Map<String, Object> resultado = new HashMap<>();
    resultado.put("livros", livros);
    resultado.put("locacoes", locacoes);
    resultado.put("usuario", usuario);
    return resultado;
  }


  public boolean gravar (Usuario usuario) throws Exception {
    try {
      String SQL;
      this.connect();
      if (usuario.getId() == 0) {
        SQL = "INSERT INTO usuarios (nome, email, senha, capa, matricula, idPerfil) VALUES (?, ?, ?, ?, ?, ?)";
      } else {
        SQL = "UPDATE usuarios SET nome=?, email=?, senha=?, capa = ?, matricula = ?, idPerfil=?, status = ? WHERE id=?";
      }
      PreparedStatement pstm = conn.prepareStatement(SQL);

      pstm.setString(1, usuario.getNome());
      pstm.setString(2, usuario.getEmail());
      pstm.setString(3, usuario.getSenha());
      pstm.setString(4, usuario.getCapa());
      pstm.setInt(5, usuario.getMatricula());
      pstm.setInt(6, usuario.getPerfil().getId());

      if (usuario.getId() > 0) {
        pstm.setInt(7, usuario.getStatus());
        pstm.setInt(8, usuario.getId());
      }
      pstm.execute();
      this.disconnect();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public boolean alterarStatus(int status) throws Exception {
    try {
      String SQL = "UPDATE usuarios SET status=?";
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setInt(1, status);
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
      String SQL = "DELETE FROM usuarios WHERE id=?";
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
  
  public Usuario getRecuperarUsuario (String email) throws Exception {
    String SQL = "SELECT * FROM usuarios WHERE email = ?";
    try {
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setString(1, email);

      ResultSet rs = pstm.executeQuery();

      Usuario usuario = new Usuario();
      if (rs.next()) {
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setStatus(rs.getInt("status"));
        usuario.setMatricula(rs.getInt("matricula"));
        usuario.setCapa(rs.getString("capa"));
        
        PerfilDAO perfilDAO = new PerfilDAO();
        Perfil perfil = perfilDAO.getById(rs.getInt("idPerfil"));
        usuario.setPerfil(perfil);
      }
      this.disconnect();
      return usuario;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
