package controller;

import model.Perfil;
import model.PerfilDAO;
import model.Usuario;
import model.UsuarioDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GerenciarUsuario", value = "/gerenciar_usuario.do")
public class GerenciarUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idBruto = request.getParameter("id");
    int id = Integer.parseInt(idBruto.trim());
    String deletar = request.getParameter("deletar");

    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      if (deletar != null) {
        String mensagem;

        mensagem = usuarioDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";

        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuario/listar-usuario.jsp");
        
      } else {
        Usuario usuario = usuarioDAO.getById(id);

        request.getSession().setAttribute("usuario", usuario);
        response.sendRedirect(request.getContextPath() + "/src/usuario/atualizar-usuario.jsp");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("id");
    String nome = request.getParameter("nome");
    String username = request.getParameter("username");
    String status = request.getParameter("status");
    String senha = request.getParameter("senha");
    String idPerfil = request.getParameter("idPerfil");

    String mensagem;
    
    String[] fields = {nome, username, senha, status, idPerfil};
    String[] fieldNames = {"nome", "username", "senha", "status", "idPerfil"};
    
    Usuario usuario = new Usuario();
    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      Validacao validacao = new Validacao();
      ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuario/cadastrar-usuario.jsp");
        
      } else {
        if (!id.isEmpty()) {
          usuario.setId(Integer.parseInt(id));
        }
        usuario.setNome(nome);
        usuario.setUsername(username);
        usuario.setSenha(senha);
        usuario.setStatus(Integer.parseInt(status));
        
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
    response.sendRedirect(request.getContextPath() + "/src/usuario/listar-usuario.jsp");

  }
}

