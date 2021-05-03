package controller;

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
    int id = Integer.parseInt(idBruto.trim());
    String acao = request.getParameter("acao");

    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      String mensagem;
      if (acao.equals("deletar")) {

        if (GerenciarLogin.verificarAcesso(request, response)) {
          mensagem = usuarioDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";

          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/listar-usuario.jsp");
        }

      } else if (acao.equals("alterar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
          GerenciarLogin.verificarAcesso(request, response);
          Usuario usuario = usuarioDAO.getById(id);

          request.getSession().setAttribute("usuario", usuario);
          response.sendRedirect(request.getContextPath() + "/src/usuarios/atualizar-usuario.jsp");
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
    String email = request.getParameter("email");
    String senha = request.getParameter("senha");
    String idPerfil = request.getParameter("idPerfil");
    String matricula = request.getParameter("matricula");
    InputStream conteudoDoArquivoCapa = request.getPart("capa").getInputStream();
    
    Date data = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String nomeDoArquivoCapa = conteudoDoArquivoCapa != null 
      ? "MyBooksImage" + dateFormat.format(data) + "-" + data.getTime()
      : "sem-foto-perfil.webp";
    
    String mensagem;
    
    String[] fields = {nome, email, senha, matricula, idPerfil};
    String[] fieldNames = {"nome", "email", "senha", "matricula", "idPerfil"};
    
    Usuario usuario = new Usuario();
    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      Validacao validacao = new Validacao();
      ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuarios/cadastrar-usuario.jsp");
        
      } else {
        if (!id.isEmpty()) { // se for editar
          String status = request.getParameter("status");
          usuario.setStatus(Integer.parseInt(status));
          usuario.setId(Integer.parseInt(id));
          usuario.setSenha(senha);
          
        } else { // se for gravar um novo registro
          String senhaHasheada = Hasher.criarHash(senha,  new byte[128 / 8], 1000, 256);
          usuario.setSenha(senhaHasheada);
        }
        
        GerenciadorDeArquivos.uploadImagem(nomeDoArquivoCapa, conteudoDoArquivoCapa);
        
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setMatricula(Integer.parseInt(matricula));
        usuario.setCapa(nomeDoArquivoCapa);
        
        
        PerfilDAO perfilDAO = new PerfilDAO();
        Perfil perfil = perfilDAO.getById(Integer.parseInt(idPerfil));
        usuario.setPerfil(perfil);
        
        mensagem = usuarioDAO.gravar(usuario) ? "Gravado com sucesso!" : "Erro ao gravar no banco de dados";
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuarios/listar-usuario.jsp");

  }
}

