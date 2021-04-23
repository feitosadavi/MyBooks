package controller;

import model.Menu;
import model.MenuDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GerenciarMenu", value = "/gerenciar_menu.do")
public class GerenciarMenu extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idBruta = request.getParameter("id");
    int id = Integer.parseInt(idBruta.trim());
    String acao = request.getParameter("acao");

    try {
      MenuDAO menuDAO = new MenuDAO();

      String mensagem;
      if (acao.equals("deletar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
          mensagem = menuDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";
          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/menus/listar-menu.jsp");
        }

      } else if (acao.equals("alterar")) {
        if (GerenciarLogin.verificarAcesso(request, response)) {
          Menu menu = menuDAO.getById(id);
          request.getSession().setAttribute("menu", menu);
          response.sendRedirect(request.getContextPath() + "/src/menus/atualizar-menu.jsp");
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
    String link = request.getParameter("link");
    String exibir = request.getParameter("exibir");

    String mensagem;

    String[] fields = {nome, link, exibir};
    String[] fieldNames = {"nome", "link", "exibir"};
    Menu menu = new Menu();
    try {
      MenuDAO menuDAO = new MenuDAO();
      Validacao validacao = new Validacao();

      ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/menus/cadastrar-menu.jsp");

      } else {
        if (!id.isEmpty()) {
          menu.setId(Integer.parseInt(id));
        }
        menu.setNome(nome);
        menu.setLink(link);
        menu.setExibir(Integer.parseInt(exibir));

        mensagem = menuDAO.gravar(menu) ? "Gravado com sucesso" : "Erro ao gravar no banco de dados";
      }

    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/menus/listar-menu.jsp");
    
  }
}

