package controller;

import model.*;
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
          LocacaoDAO locacaoDAO = new LocacaoDAO();
          ArrayList<Locacao> locacoes =  locacaoDAO.getHistoricoLocacoesPorUsuario(id);
          
          // desvinculo as locações
          for (Locacao locacao : locacoes) {
            for (Livro livro : locacao.getLivros()) {
              locacaoDAO.deletar(locacao.getId(), livro.getId(), false);
            }
            locacaoDAO.deletar(locacao.getId(), 0, true);
          }
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
    Usuario ulogado = (Usuario) request.getSession().getAttribute("ulogado");

    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      if (acao != null) {
        if (acao.equals("alterar")) {
          String email = request.getParameter("email");
          if (email != null) {
            ulogado.setEmail(email);
            if (usuarioDAO.gravar(ulogado)) {
              mensagem = "Email alterado com sucesso";
            } else {
              mensagem = "Erro ao alterar o email. Tente novamente!";
            }
          } else {
            mensagem = "Email inválido";
          }
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
          return;
          
        } else if (acao.equals("alterar_status")) {
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
          
        } else if (acao.equals("alterar_senha")) {
          String senhaAtual = request.getParameter("senha_atual");
          String senhaNova = request.getParameter("senha_nova");
          
          String senhaAtualHasheada = Hasher.criarHash(senhaAtual, new byte[128 / 8], 1000, 256);
          if (senhaAtualHasheada != null && senhaAtualHasheada.equals(ulogado.getSenha())) {
            String senhaNovaHasheada = Hasher.criarHash(senhaNova, new byte[128 / 8], 1000, 256);
            ulogado.setSenha(senhaNovaHasheada);
            if (usuarioDAO.gravar(ulogado)) {
              mensagem = "Senha alterada com sucesso";
            } else {
              mensagem = "Erro ao alterar senha. Tente novamente!";
            }
          } else {
            mensagem = "Senha atual incorreta";
          }
          
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/minha-conta.jsp");
          return;
        }
        
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
}

