package controller;

import model.*;
import utils.Hasher;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    
    String email = request.getParameter("email");
    String senha = request.getParameter("senha");

    String mensagem;

    String[] camposObrigatorios = {"email", "senha"};
    String[] camposDoUsuario = {email, senha};
    try {
      Validacao validacao = new Validacao();
      ArrayList<String> camposNencontrados = validacao.camposRequeridos(camposObrigatorios, camposDoUsuario);

      if (!camposNencontrados.isEmpty()) {
        mensagem = "Os seguintes campos não foram encontrados: " + camposNencontrados;

      } else {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.getRecuperarUsuario(email);

        String senhaHasheada = Hasher.criarHash(senha,  new byte[128 / 8], 1000, 256);
        
        if (usuario.getId() == 0) {
          mensagem = "Email não encontrado";
          
        } else if (!usuario.getSenha().equals(senhaHasheada)) {
          mensagem = "Senha incorreta!";
            
        } else {
          mensagem = "Logado com sucesso";
          request.getSession().setAttribute("ulogado", usuario);
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/");
          return;
        }
      }

    } catch(Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
  }
  
  public static Usuario autenticar(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

  public static boolean verificarAcesso (HttpServletRequest request, HttpServletResponse response) throws IOException {
    GerenciarLogin.response = response;
    boolean possuiAcesso = false;
    try {
      String uri = request.getRequestURI();
      String queryString = request.getQueryString();
      if (queryString != null) {
        uri += "?" + queryString;
      }
      Usuario usuario = (Usuario) request.getSession().getAttribute("ulogado");

      for (Menu menu: usuario.getPerfil().getMenus()) {
        if (uri.contains(menu.getLink())) {
          possuiAcesso = true;
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    if (!possuiAcesso) {
      request.getSession().setAttribute("mensagem", "Acesso negado");
      String referer = request.getHeader("Referer");
      response.sendRedirect(referer);
      return false;
      
    } else {
      return true;
    }
  }
  
  public static void gerenciarPendencias(Usuario usuario, HttpServletRequest request) {
    String mensagem = null;
    try {
      if (usuario != null && usuario.getId() > 0) {
        Map<String, String> verificacao = verificarPendencias(usuario.getId());
        if (verificacao != null) {
          if (verificacao.get("pendencia").equals("true")) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.alterarStatus(0);
            
            mensagem = verificacao.get("mensagem");
          }
        } else {
          mensagem = "Erro ao verificar";
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro interno do servidor";
    }
    if (mensagem != null) {
      request.getSession().setAttribute("mensagem", mensagem);
    }
  }
  
  private static Map<String, String> verificarPendencias(int id) {
    try {
      String mensagem = "";
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      LocacaoDAO locacaoDAO = new LocacaoDAO();
      Map<String, Object> livrosLocados =  usuarioDAO.getLivrosLocados(id);
      ArrayList<Locacao> locacoes = (ArrayList<Locacao>) livrosLocados.get("locacoes");
      ArrayList<Livro> livros = (ArrayList<Livro>) livrosLocados.get("livros");
      
      int dataHoje = construirData(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
      
      ArrayList<Boolean> pendencias = new ArrayList<>();
      for (Locacao locacao : locacoes) {
        int dataDevolucao = construirData(locacao.getDataDevolucao().toString());
        if (dataHoje > dataDevolucao) {
          if (locacao.getStatus().equals("confirmado")) {
            pendencias.add(true);
            mensagem = "Devolva os livro alugados para continuar utilizando a biblioteca!";
            
          } else if (locacao.getStatus().equals("pendente")) { 
            // se tiver pendente e já passou a data de agendamento, deleta a locação
            for (Livro livro : livros) {
              locacaoDAO.deletar(locacao.getId(), livro.getId(), false);
            }
            
            locacaoDAO.deletar(locacao.getId(), 0, true);
            pendencias.add(false);
            mensagem = "Você realizou um agendamento, porém não compareceu. Portanto a locação foi cancelada";
            
          } else {
            pendencias.add(false);
          }
        }
      }
      
      Map<String, String> retorno  = new HashMap<>();
      retorno.put("mensagem", mensagem);
      retorno.put("pendencia", pendencias.contains(true) + "");
      
      return retorno;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  private static int construirData (String data) {
    StringBuilder dataDevolucao = new StringBuilder();
    for (String fragmento : data.split("-")) {
      dataDevolucao.append(fragmento);
    }
    return Integer.parseInt(String.valueOf(dataDevolucao));
  }
}
