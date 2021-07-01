package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LocacaoDAO extends DatabaseDAO {
  public LocacaoDAO() throws Exception{}
	
  public Locacao getById(int id) throws Exception {
    String SQL = "SELECT * FROM locacoes WHERE id=?";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    Locacao locacao = new Locacao();
    if (rs.next()) {
			locacao.setId(rs.getInt("id"));
			locacao.setDataLocacao(rs.getDate("dataLocacao"));
			locacao.setDataDevolucao(rs.getDate("dataDevolucao"));
			locacao.setStatus(rs.getString("status"));
			locacao.setHorarioColeta(rs.getString("horarioColeta"));
			
			UsuarioDAO alunoDAO = new UsuarioDAO();
			Usuario aluno = alunoDAO.getById(rs.getInt("idUsuario"));
			locacao.setAluno(aluno);
			
			UsuarioDAO bibliotecarioDAO  = new UsuarioDAO();
			Usuario bibliotecario = bibliotecarioDAO.getById(rs.getInt("idBibliotecario"));
			locacao.setBibliotecario(bibliotecario);
    }
    this.disconnect();
    return locacao;
  }

  public ArrayList<Livro> getLivrosDaLocacaoPorId(int id) throws Exception {
    String SQL = "SELECT * FROM livros JOIN locacoes_livros as ll ON livros.id = ll.idLivros WHERE ll.idLocacoes = ?";
    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, id);

    ResultSet rs = pstm.executeQuery();
    ArrayList<Livro> livros = new ArrayList<>();
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
    }
    this.disconnect();
    return livros;
  }
  
  public ArrayList<Locacao> getHistoricoLocacoes () throws Exception {
  	String SQL = "SELECT * FROM locacoes";
		this.connect();
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(SQL);

		ArrayList<Locacao> locacoes = new ArrayList<Locacao>();
		while (rs.next()) {
			Locacao locacao = new Locacao();
			locacao.setId(rs.getInt("id"));
			locacao.setDataLocacao(rs.getDate("dataLocacao"));
			locacao.setDataDevolucao(rs.getDate("dataDevolucao"));
			locacao.setStatus(rs.getString("status"));
      locacao.setHorarioColeta(rs.getString("horarioColeta"));

      UsuarioDAO alunoDAO = new UsuarioDAO();
			Usuario aluno = alunoDAO.getById(rs.getInt("idUsuario"));
			locacao.setAluno(aluno);

			UsuarioDAO bibliotecarioDAO  = new UsuarioDAO();
			Usuario bibliotecario = bibliotecarioDAO.getById(rs.getInt("idBibliotecario"));
			locacao.setBibliotecario(bibliotecario);

			locacoes.add(locacao);
		}
		this.disconnect();
		return locacoes;  		
  }
	
  public ArrayList<Locacao> getHistoricoLocacoesPorUsuario (int idUsuario) throws Exception {
  	String SQL = "SELECT * FROM locacoes WHERE idUsuario = ?";
		this.connect();
		PreparedStatement pstm = conn.prepareStatement(SQL);
		pstm.setInt(1, idUsuario);
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Locacao> locacoes = new ArrayList<Locacao>();
		while (rs.next()) {
			Locacao locacao = new Locacao();
			locacao.setId(rs.getInt("id"));
			locacao.setDataLocacao(rs.getDate("dataLocacao"));
			locacao.setDataDevolucao(rs.getDate("dataDevolucao"));
			locacao.setDataColeta(rs.getDate("dataColeta"));
			locacao.setStatus(rs.getString("status"));
      locacao.setHorarioColeta(rs.getString("horarioColeta"));

      UsuarioDAO alunoDAO = new UsuarioDAO();
			Usuario aluno = alunoDAO.getById(idUsuario);
			locacao.setAluno(aluno);
			
			UsuarioDAO bibliotecarioDAO  = new UsuarioDAO();
			Usuario bibliotecario = bibliotecarioDAO.getById(rs.getInt("idBibliotecario"));
			locacao.setBibliotecario(bibliotecario);
			
			locacao.setLivros(this.getLivrosVinculados(locacao.getId()));
			
			locacoes.add(locacao);
		}
		this.disconnect();
		return locacoes;  		
  }
  
  public boolean gravar (Locacao locacao) throws Exception {
  	String SQL;
  	if (locacao.getBibliotecario() != null) {
  		SQL = "INSERT INTO locacoes (status, idBibliotecario) VALUES (?, ?)";
  	} else {
  		SQL = "INSERT INTO locacoes (dataLocacao, dataDevolucao, dataColeta, idUsuario, horarioColeta) VALUES (?, ?, ?, ?, ?)";
  	}
  	try {
  		this.connect();
  		PreparedStatement pstm = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
  		pstm.setDate(1, locacao.getDataLocacao());
  		pstm.setDate(2, locacao.getDataDevolucao());
  		pstm.setDate(3, locacao.getDataColeta());
  		pstm.setInt(4, locacao.getAluno().getId());
  		pstm.setString(5, locacao.getHorarioColeta());
  		
  		if (locacao.getBibliotecario() != null) {
  			pstm.setString(1, locacao.getStatus());
  			pstm.setInt(2, locacao.getBibliotecario().getId());
  		}
  		pstm.executeUpdate();
  		ResultSet rs = pstm.getGeneratedKeys();
  		if (rs.next()) {
  			for (Livro livro : locacao.getLivros()) {
  				this.vincularLocacao(rs.getInt(1), livro.getId());  				
  			}
  		}
  		this.disconnect();
  		
  		return true;
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  		return false;
  	}
  }
  
  public void vincularLocacao (int idLocacao, int idLivro) throws Exception {
    String SQL = "INSERT INTO locacoes_livros (idLocacoes, idLivros) VALUES (?, ?)";

    this.connect();
    PreparedStatement pstm = conn.prepareStatement(SQL);
    pstm.setInt(1, idLocacao);
    pstm.setInt(2, idLivro);
    pstm.execute();
    this.disconnect();
  }
  
  private ArrayList<Livro> getLivrosVinculados (int idLocacoes) throws Exception {
  	String SQL = "SELECT * FROM locacoes_livros WHERE idLocacoes = ?";
  	
		this.connect();
		PreparedStatement pstm = conn.prepareStatement(SQL);
		pstm.setInt(1, idLocacoes);
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Livro> livros = new ArrayList<Livro>();
		LivroDAO livroDAO = new LivroDAO();
		while (rs.next()) {
			Livro livro = livroDAO.getById(rs.getInt("idLivros"));
      livros.add(livro);
		}
		
		this.disconnect();
		return livros;
  }

  public boolean alterarStatus (String status, int idBibliotecario, int id) throws Exception {
    String SQL = "UPDATE locacoes SET status=?, idBibliotecario=? WHERE id=?";
    try {
      this.connect();
      PreparedStatement pstm = conn.prepareStatement(SQL);
      pstm.setString(1, status);
      pstm.setInt(2, idBibliotecario);
      pstm.setInt(3, id);
      pstm.execute();
      this.disconnect();
      return true;
      
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }


  public boolean deletar (int idLocacao, int idLivro, boolean deletarLocacao) throws Exception {
    try {
      this.connect();
      if (idLivro > 0) {
        String SQL =  "DELETE FROM locacoes_livros WHERE idLocacoes=? AND idLivros = ?;";
        PreparedStatement pstm = conn.prepareStatement(SQL);
        pstm.setInt(1, idLocacao);
        pstm.setInt(2, idLivro);
        pstm.execute();
      }

      if (deletarLocacao) {
        String SQL2 =  "DELETE FROM locacoes WHERE id=?;";
        PreparedStatement pstm = conn.prepareStatement(SQL2);
        pstm.setInt(1, idLocacao);
        pstm.execute();
        pstm.execute();
      }
      
      this.disconnect();

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
