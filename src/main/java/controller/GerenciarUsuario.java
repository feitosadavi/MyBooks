package controller;

import model.Livro;
import model.LivroDAO;
import model.Locacao;
import model.LocacaoDAO;
import model.Perfil;
import model.PerfilDAO;
import model.Usuario;
import model.UsuarioDAO;
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

@WebServlet(name = "GerenciarUsuario", value = "/gerenciar_usuario.do")
@MultipartConfig
public class GerenciarUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idBruto = request.getParameter("id");
    int id = 0;
    if (idBruto != null) id = Integer.parseInt(idBruto.trim()); // evitando NullPointerException
    String acao = request.getParameter("acao");
    String mensagem = null;
    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      if (acao.equals("deletar")) {

        if (GerenciarLogin.verificarAcesso(request, response)) {
          mensagem = usuarioDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";

          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/listar-usuario.jsp");
        }

      } else if (acao.equals("alterar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
          Usuario usuario = usuarioDAO.getById(id);

          request.getSession().setAttribute("usuario", usuario);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/atualizar-usuario.jsp");
        }

      } else if (acao.equals("carrinho")) {
      	String livroId = request.getParameter("livroId");
      	ArrayList<String> carrinho  = (ArrayList<String>) request.getSession().getAttribute("carrinho");
      	
      	if (carrinho != null) {
      		carrinho.add(livroId);
      	} else {
      		carrinho = new ArrayList<String>() {{
      			add(livroId);
      		}};
        	request.getSession().setAttribute("carrinho", carrinho);
      	}
      	response.sendRedirect(request.getContextPath() + "/src/livros/listar-livro.jsp");
      	
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String acao = request.getParameter("acao");
    String mensagem = null;
    
    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      if (acao != null && acao.equals("alugar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
        	Usuario aluno = (Usuario) request.getSession().getAttribute("ulogado");
        	if (aluno.getStatus() == 1) { // se o usuário estiver ativado		
        		String dataColeta = request.getParameter("dataColeta");
        		
//          	ArrayList<String> carrinho  = (ArrayList<String>) request.getSession().getAttribute("carrinho");
//        		ArrayList<Integer> livrosId = new ArrayList<Integer>();
//        		for (String livroIdString : carrinho) {
//        			livrosId.add(Integer.parseInt(livroIdString));
//        		}
//            this.alugar(livrosId, aluno);
//            
//            request.getSession().removeAttribute("carrinho");
            response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
        	}
        }    
      } else if (acao != null && acao.equals("alterar_status")) {
        String status = request.getParameter("status");
        String id = request.getParameter("id");

        if (usuarioDAO.alterarStatus(Integer.parseInt(status))) {
          mensagem = "Status do usuário alterado com sucesso";
        } else {
          mensagem = "Erro ao alterar status";
        }
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuarios/conta.jsp?id=" + id);
        return;
        
      } else {
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String idPerfil = request.getParameter("idPerfil");
        String matricula = request.getParameter("matricula");
        String capaAtual = request.getParameter("capaAtual");

        Part capaPart = request.getPart("capa");
        String capaNova = capaPart.getSubmittedFileName();

        InputStream conteudoDoArquivoCapa = capaPart.getInputStream();
        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        final String novoNomeDoArquivoCapa = conteudoDoArquivoCapa != null
          ? "MyBooksImage" + dateFormat.format(data) + "-" + data.getTime()
          : "sem-foto-perfil.svg";

        String[] fields = {nome, email, senha, matricula, idPerfil};
        String[] fieldNames = {"nome", "email", "senha", "matricula", "idPerfil"};
        
        Validacao validacao = new Validacao();
        ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
        if (!camposNencontrados.isEmpty()) {
          mensagem = "Campos não inseridos: " + camposNencontrados;
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/cadastrar-usuario.jsp");
          return;
          
        } else {
          Usuario usuario = new Usuario();
          if (capaAtual != null && !capaAtual.isEmpty() && capaNova.isEmpty()) { // se tiver capa atual e ela não for atualizada: 
            usuario.setCapa(capaAtual); // não mudo a capa se vier uma imagem já existente
            
          } else if (!capaNova.isEmpty()) { // se o usuário escolheu uma nova foto de perfil
            usuario.setCapa(novoNomeDoArquivoCapa);
            
            if (id != null && !id.isEmpty()) { // se for editar
              //GerenciadorDeArquivos.procurarArquivo(nomeDoArquivoCapa, request);
            } 
            GerenciadorDeArquivos.uploadImagem(novoNomeDoArquivoCapa, conteudoDoArquivoCapa, "fotosUsuario");
          }
        
          if (id != null && !id.isEmpty()) { // se for editar
            String status = request.getParameter("status");
            usuario.setStatus(Integer.parseInt(status));
            usuario.setId(Integer.parseInt(id));
            usuario.setSenha(senha);
            
          } else { // se for gravar um novo registro
            String senhaHasheada = Hasher.criarHash(senha,  new byte[128 / 8], 1000, 256);
            usuario.setSenha(senhaHasheada);
          }
          
          usuario.setNome(nome);
          usuario.setEmail(email);
          usuario.setMatricula(Integer.parseInt(matricula));
          
       
          PerfilDAO perfilDAO = new PerfilDAO();
          Perfil perfil = perfilDAO.getById(Integer.parseInt(idPerfil));
          usuario.setPerfil(perfil);
          
          mensagem = usuarioDAO.gravar(usuario) ? "Gravado com sucesso!" : "Erro ao gravar no banco de dados";
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuarios/listar-usuario.jsp");

  }
  
  private boolean alugar (ArrayList<Integer> idLivros, Usuario aluno) throws Exception {
  	try {
  		java.util.Date dataHoje = new java.util.Date();
//  		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yyyy");
//  		String dataHojeString = spdf.format(dataHoje);
//  		Date date1 = spdf.parse(dataHojeString);
  		
  		Locacao locacao = new Locacao();
  		java.sql.Date dataHojeSQL = new java.sql.Date(dataHoje.getTime());
  		locacao.setDataLocacao(dataHojeSQL);
  		
  		java.util.Date dataDevolucao = new java.util.Date();
  		dataDevolucao.setDate(dataHoje.getDate() + 10);
  		java.sql.Date dataDevolucaoSQL = new java.sql.Date(dataDevolucao.getTime());
  		
  		locacao.setDataDevolucao(dataDevolucaoSQL);
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

