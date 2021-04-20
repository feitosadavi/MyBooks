package controller;

import model.Menu;
import model.Usuario;
import model.UsuarioDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GerenciarLogin", value = "/gerenciar_login.do" )
public class GerenciarLogin extends HttpServlet {
  private static HttpServletResponse response;
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String logout = request.getParameter("logout");

    if (logout != null) {
      request.getSession().invalidate();
      response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    GerenciarLogin.response = response;
    
    String username = request.getParameter("username");
    String senha = request.getParameter("senha");
    
    String mensagem;

    String[] camposObrigatorios = {"username", "senha"};
    String[] camposDoUsuario = {username, senha};
    try {
      Validacao validacao = new Validacao();
      ArrayList<String> camposNencontrados = validacao.camposRequeridos(camposObrigatorios, camposDoUsuario);

      if (!camposNencontrados.isEmpty()) {
        mensagem = "Os seguintes campos não foram encontrados: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
        
      } else {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.getRecuperarUsuario(username);

        if (usuario != null && usuario.getSenha().equals(senha)) {
          mensagem = "Logado com sucesso";
          request.getSession().setAttribute("ulogado", usuario);
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/");

        } else {
          mensagem = "Username ou senha incorretos!";
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
        }
      }
      
    } catch(Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
  }
  
  public static Usuario autenticar(HttpServletRequest request, HttpServletResponse response) {
    Usuario usuario = null;
    GerenciarLogin.response = response;
    try {
      HttpSession sessao = request.getSession();
      if (sessao.getAttribute("ulogado") == null) {
        response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");

      } else {
        usuario = (Usuario) request.getSession().getAttribute("ulogado");
        
        if (usuario == null) {
          sessao.setAttribute("mensagem", "Você não está autenticado");
          response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
          
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return usuario;
  }

  public static boolean verificarAcesso (HttpServletRequest request, HttpServletResponse response) {
    GerenciarLogin.response = response;
    boolean possuiAcesso = false;
    try {
      String uri = request.getRequestURI();
      String queryString = request.getQueryString();
      if (queryString != null) {
        uri += "?" + queryString;
      }
      Usuario usuario = (Usuario) request.getSession().getAttribute("ulogado");
      System.out.println(queryString);
      System.out.println(uri);

      for (Menu menu: usuario.getPerfil().getMenus()) {
        if (uri.contains(menu.getLink())) {
          System.out.println("possui acesso");
          possuiAcesso = true;
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return possuiAcesso;
  }
}
