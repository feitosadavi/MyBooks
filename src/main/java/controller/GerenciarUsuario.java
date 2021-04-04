package controller;

import model.Usuario;
import model.UsuarioDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

@WebServlet(name = "GerenciarUsuario", value = "/gerenciar_usuario.do")
public class GerenciarUsuario extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    String id = request.getParameter("id");
    String deletar = request.getParameter("deletar");
    Usuario usuario = new Usuario();

    String idLimpa = id.trim();
    int idParsed = Integer.parseInt(idLimpa);
    try {
      UsuarioDAO usuarioDAO = new UsuarioDAO();

      if (deletar == null) {
        Usuario resultado = usuarioDAO.getById(idParsed);

        usuario.setId(resultado.getId());
        usuario.setNome(resultado.getNome());
        usuario.setUsername(resultado.getUsername());
        usuario.setStatus(resultado.getStatus());
        usuario.setSenha(resultado.getSenha());
        usuario.setIdPerfil(resultado.getIdPerfil());

        request.getSession().setAttribute("usuario", usuario);
        response.sendRedirect(request.getContextPath() + "/src/usuario/atualizar-usuario.jsp");

      } else {
        String mensagem;

        if (usuarioDAO.deletar(idParsed)) {
          mensagem = "Deletado com sucesso!";
        } else {
          mensagem = "Erro ao deletar";
        }
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuario/listar-usuario.jsp");
      }

    } catch (Exception e) {
      out.println(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
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
      Stack<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/usuario/cadastrar-usuario.jsp");
        return;
      }

      if (!id.isEmpty()) {
        usuario.setId(Integer.parseInt(id));
      }
      usuario.setNome(nome);
      usuario.setUsername(username);
      usuario.setSenha(senha);
      usuario.setStatus(Integer.parseInt(status));
      usuario.setIdPerfil(Integer.parseInt(idPerfil));

      if (usuarioDAO.gravar(usuario)) {
        mensagem = "Gravado com sucesso";
      } else {
        mensagem = "Erro ao gravar no banco de dados";
      }
    } catch (Exception e) {
      out.print(e);
      System.out.println(e);
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/usuario/listar-usuario.jsp");

  }
}

