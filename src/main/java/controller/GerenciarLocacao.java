package controller;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@WebServlet(name = "GerenciarLocacao", value = "/gerenciar_locacao.do")
@MultipartConfig
public class GerenciarLocacao extends HttpServlet {
  private HttpServletRequest request;
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.request = request;
    
    int idLocacao = Integer.parseInt(request.getParameter("idLocacao"));
    int idLivro = Integer.parseInt(request.getParameter("idLivro"));
    Usuario ulogado = (Usuario) request.getSession().getAttribute("ulogado");
    System.out.println(ulogado);
    int idConta = ulogado.getId();
    
    String mensagem = null;
    String acao = request.getParameter("acao");
    String status = request.getParameter("statusLocacao");

    try {
      if (acao != null && acao.equals("alterar_status")) {
        if (this.alterarStatus(status, idLocacao)) {
          mensagem = "Status da locacao alterado com sucesso";
        } else {
          mensagem = "Erro ao alterar status";
        }
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuarios/conta.jsp?id=" + idConta);
      } else if (acao != null && acao.equals("cancelar")) {
        LocacaoDAO locacaoDAO = new LocacaoDAO();
        LivroDAO livroDAO = new LivroDAO();
        
        Livro livro = livroDAO.getById(idLivro);
        ArrayList<Livro> livros = locacaoDAO.getLivrosDaLocacaoPorId(idLocacao);
        
        if (livros.size() > 1) { // se a locação tiver mais de um livro
          System.out.println("Deletar um livro");
          locacaoDAO.deletar(idLocacao, idLivro, false); // remove apenas um livro, não a locação
          mensagem = "Livro removido da locação com sucesso";
        } else {
          System.out.println("Deletar locação");
          locacaoDAO.deletar(idLocacao, idLivro, true); // remove apenas um livro, não a locação
          mensagem = "Locação cancelada com sucesso";
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro interno do servidor!";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String locacao = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    String acao = request.getParameter("acao");
    String mensagem = "";
    try {
      JSONObject locacaoObj = new JSONObject(locacao);
      String dataColetaString = locacaoObj.getString("dataColeta");
      String horarioColeta = locacaoObj.getString("horarioColeta");
      JSONArray livrosIdJSON = locacaoObj.getJSONArray("livrosId");
      
      java.sql.Date dataColetaDate = java.sql.Date.valueOf(dataColetaString);
      
      String[] fields = {dataColetaString};
      String[] fieldNames = {"dataColeta"};

      Usuario aluno = (Usuario) request.getSession().getAttribute("ulogado");

      ArrayList<Integer> livrosId = new ArrayList<Integer>();
      for (int i = 0; i < livrosIdJSON.length(); i++) {
        Integer diaObj = (Integer) livrosIdJSON.get(i);
        livrosId.add(diaObj);
      }

      mensagem = this.alugar(livrosId, aluno, dataColetaDate, horarioColeta);
      request.getSession().removeAttribute("carrinho");
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
  }
  
  private boolean alterarStatus(String status, int idLocacao) {
    try {
      Usuario usuario = (Usuario) this.request.getSession().getAttribute("ulogado");
      System.out.println(usuario.getPerfil().getNome());
      boolean retorno = false;
      if (usuario.getPerfil().getNome().equals("Bibliotecario")) {
        LocacaoDAO locacaoDAO = new LocacaoDAO();
        retorno = locacaoDAO.alterarStatus(status, usuario.getId(), idLocacao);
      }
      return retorno;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  } 
  
  private String alugar (ArrayList<Integer> idLivros, Usuario aluno, java.sql.Date dataColeta, String horarioColeta) throws Exception {
  	try {
  		java.util.Date dataHoje = new java.util.Date();

  		Locacao locacao = new Locacao();
  		java.sql.Date dataHojeSQL = new java.sql.Date(dataHoje.getTime());
  		locacao.setDataLocacao(dataHojeSQL);
  		
  		java.util.Date dataDevolucao = new java.util.Date();
  		dataDevolucao.setDate(dataColeta.getDate() + 10);
  		java.sql.Date dataDevolucaoSQL = new java.sql.Date(dataDevolucao.getTime());
  		
  		locacao.setDataDevolucao(dataDevolucaoSQL);
  		locacao.setDataColeta(dataColeta);
  		locacao.setHorarioColeta(horarioColeta);
  		locacao.setAluno(aluno);
  		
  		ArrayList<Livro> livros = new ArrayList<Livro>();
  		for (int idLivro : idLivros) {
  			LivroDAO livroDAO = new LivroDAO();
    		Livro livro = livroDAO.getById(idLivro);
    		
    		if (livro.getEstoque() < 1) return "Livro fora de estoque. Por favor, aguarde!";
    		if (livroDAO.atualizarEstoque(livro.getEstoque() - 1, livro.getId())) {
    		  livros.add(livro);
        } else {
    		  return "Erro ao atualizar o estouqe";
        }
    		
  		}
  		locacao.setLivros(livros);
  		
  		LocacaoDAO locacaoDAO = new LocacaoDAO();
    	locacaoDAO.gravar(locacao);
    	
  		return "Livro(s) alugado(s) com sucesso";
  	} catch (Exception e) {
  		e.printStackTrace();
  		return "Erro interno do servidor ao alugar";
  	}
  }
}

