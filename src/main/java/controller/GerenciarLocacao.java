package controller;

import model.Livro;
import model.LivroDAO;
import model.Locacao;
import model.LocacaoDAO;
import model.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@WebServlet(name = "GerenciarLocacao", value = "/gerenciar_locacao.do")
@MultipartConfig
public class GerenciarLocacao extends HttpServlet {  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String dataColetaString = request.getParameter("dataColeta");
    
    SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date parsed = null;
		try {
			parsed = spdf.parse(dataColetaString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    java.sql.Date dataColetaDate = new java.sql.Date(parsed.getTime());
    
    String mensagem = null;
    
    String[] fields = {dataColetaString};
    String[] fieldNames = {"dataColeta"};
    
    try {
      if (GerenciarLogin.verificarAcesso(request, response)) {
      	Usuario aluno = (Usuario) request.getSession().getAttribute("ulogado");
      	if (aluno.getStatus() == 1) { // se o usuário estiver ativado		      		      		
        	ArrayList<String> carrinho  = (ArrayList<String>) request.getSession().getAttribute("carrinho");
      		ArrayList<Integer> livrosId = new ArrayList<Integer>();
      		
      		for (String livroIdString : carrinho) {
      			livrosId.add(Integer.parseInt(livroIdString));
      		}
          this.alugar(livrosId, aluno, dataColetaDate);
          
          request.getSession().removeAttribute("carrinho");
      	}
      	mensagem = "Livro(s) alugado(s) com sucesso";
      	
      } else {
      	mensagem = "Usuários inativos não podem alugar livros";
      }
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
  }
  
  private boolean alugar (ArrayList<Integer> idLivros, Usuario aluno, java.sql.Date dataColeta) throws Exception {
  	try {
  		java.util.Date dataHoje = new java.util.Date();
//  		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yyyy");
//  		String dataHojeString = spdf.format(dataHoje);
//  		Date date1 = spdf.parse(dataHojeString);
  		
  		Locacao locacao = new Locacao();
  		java.sql.Date dataHojeSQL = new java.sql.Date(dataHoje.getTime());
  		locacao.setDataLocacao(dataHojeSQL);
  		
  		java.util.Date dataDevolucao = new java.util.Date();
  		dataDevolucao.setDate(dataColeta.getDate() + 10);
  		java.sql.Date dataDevolucaoSQL = new java.sql.Date(dataDevolucao.getTime());
  		
  		locacao.setDataDevolucao(dataDevolucaoSQL);
  		locacao.setDataColeta(dataColeta);
  		locacao.setAluno(aluno);
  		
  		ArrayList<Livro> livros = new ArrayList<Livro>();
  		for (int idLivro : idLivros) {
  			LivroDAO livroDAO = new LivroDAO();
    		Livro livro = livroDAO.getById(idLivro);
    		livros.add(livro);
  		}
  		locacao.setLivros(livros);
  		
  		LocacaoDAO locacaoDAO = new LocacaoDAO();
    	locacaoDAO.gravar(locacao);
    	
  		return true;
  	} catch (Exception e) {
  		e.printStackTrace();
  		return false;
  	}
  }
}

