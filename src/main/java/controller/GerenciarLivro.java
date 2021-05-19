package controller;

import model.Livro;
import model.LivroDAO;
import utils.GerenciadorDeArquivos;
import utils.Hasher;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "GerenciarLivro", value = "/gerenciar_livro.do")
@MultipartConfig
public class GerenciarLivro extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idBruto = request.getParameter("id");
    int id = Integer.parseInt(idBruto.trim());
    String acao = request.getParameter("acao"); 

    try {
      LivroDAO livroDAO = new LivroDAO();

      String mensagem;
      if (acao.equals("deletar")) {

        if (GerenciarLogin.verificarAcesso(request, response)) {
          mensagem = livroDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";

          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/livros/listar-livro.jsp");
        }

      } else if (acao.equals("alterar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
          Livro livro = livroDAO.getById(id);

          request.getSession().setAttribute("livro", livro);
          response.sendRedirect(request.getContextPath() + "/src/livros/atualizar-livro.jsp");
        }

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("id");
    String nome = request.getParameter("nome");
    String lancamento = request.getParameter("lancamento");
    String genero = request.getParameter("genero");
    String paginas = request.getParameter("paginas");
    String estoque = request.getParameter("estoque");
    String capaAtual = request.getParameter("capaAtual");

    Part capaPart = request.getPart("capa");
    String capaNova = capaPart.getSubmittedFileName();
    
    InputStream conteudoDoArquivoCapa = capaPart.getInputStream();
    Date data = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String novoNomeDoArquivoCapa = conteudoDoArquivoCapa != null 
      ? "MyBooksImage" + dateFormat.format(data) + "-" + data.getTime()
      : "sem-capa-livro.svg";
    
    String mensagem;
    
    String[] fields = {nome, lancamento, genero, paginas, estoque};
    String[] fieldNames = {"nome", "lancamento", "genero", "paginas", "estoque"};
    
    Livro livro = new Livro();
    try {
      LivroDAO livroDAO = new LivroDAO();
      Validacao validacao = new Validacao();
      ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/livros/cadastrar-livro.jsp");
        return;
        
      } else {
      	if (capaAtual != null && !capaAtual.isEmpty() && capaNova.isEmpty()) { // se tiver capa atual e ela não for atualizada: 
      	  livro.setCapa(capaAtual); // não mudo a capa se vier uma imagem já existente
      		
      	} else if (!capaNova.isEmpty()) { // se o usuário escolheu uma nova foto de perfil
      	  livro.setCapa(novoNomeDoArquivoCapa);
      		
          if (id != null && !id.isEmpty()) { // se for editar
            //GerenciadorDeArquivos.procurarArquivo(nomeDoArquivoCapa, request);
          } 
          GerenciadorDeArquivos.uploadImagem(novoNomeDoArquivoCapa, conteudoDoArquivoCapa, "fotosLivro");
      	}
    	
        if (id != null && !id.isEmpty()) { // se for editar
          String status = request.getParameter("status");
          livro.setId(Integer.parseInt(id));
        }
        
        livro.setNome(nome);
        livro.setLancamento(Integer.parseInt(lancamento));
        livro.setGenero(genero);
        livro.setPaginas(Integer.parseInt(paginas));
        livro.setEstoque(Integer.parseInt(estoque));        
     
//        PerfilDAO perfilDAO = new PerfilDAO();
//        Perfil perfil = perfilDAO.getById(Integer.parseInt(idPerfil));
//        livro.setPerfil(perfil);
        
        mensagem = livroDAO.gravar(livro) ? "Gravado com sucesso!" : "Erro ao gravar no banco de dados";
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/livros/listar-livro.jsp");

  }
}

