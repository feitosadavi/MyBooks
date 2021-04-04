package controller;

import model.Menu;
import model.MenuDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

@WebServlet(name = "GerenciarMenu", value = "/gerenciar_menu.do")
public class GerenciarMenu extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    String id = request.getParameter("id");
    String deletar = request.getParameter("deletar");
    Menu menu = new Menu();

    String idLimpa = id.trim();
    int idParsed = Integer.parseInt(idLimpa);
    try {
      MenuDAO menuDAO = new MenuDAO();

      if (deletar == null) {
        Menu resultado = menuDAO.getById(idParsed);

        menu.setId(resultado.getId());
        menu.setNome(resultado.getNome());
        menu.setLink(resultado.getLink());
        menu.setExibir(resultado.getExibir());

        request.getSession().setAttribute("menu", menu);
        response.sendRedirect(request.getContextPath() + "/src/menu/atualizar-menu.jsp");

      } else {
        String mensagem;

        if (menuDAO.deletar(idParsed)) {
          mensagem = "Deletado com sucesso!";
        } else {
          mensagem = "Erro ao deletar";
        }
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/menu/listar-menu.jsp");
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
    String link = request.getParameter("link");
    String exibir = request.getParameter("exibir");

    String mensagem;

    String[] fields = {nome, link, exibir};
    String[] fieldNames = {"nome", "link", "exibir"};
    Menu menu = new Menu();
    try {
      MenuDAO menuDAO = new MenuDAO();
      Validacao validacao = new Validacao();
      Stack<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/menu/listar-menu.jsp");
        return;
      }

      if (!id.isEmpty()) {
        menu.setId(Integer.parseInt(id));
      }
      menu.setNome(nome);
      menu.setLink(link);
      menu.setExibir(Integer.parseInt(exibir));

      if (menuDAO.gravar(menu)) {
        mensagem = "Gravado com sucesso";
      } else {
        mensagem = "Erro ao gravar no banco de dados";
      }
    } catch (Exception e) {
      out.print(e);
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/menu/listar-menu.jsp");

  }
}

