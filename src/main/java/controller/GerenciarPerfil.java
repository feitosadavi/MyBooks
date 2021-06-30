package controller;

import model.Menu;
import model.Perfil;
import model.PerfilDAO;
import utils.Validacao;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(name = "GerenciarPerfil", value = "/gerenciar_perfil.do")
public class GerenciarPerfil extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idBruto = request.getParameter("id");
    int id = Integer.parseInt(idBruto.trim());
    String acao = request.getParameter("acao");

    try {
      PerfilDAO perfilDAO = new PerfilDAO();

      if (acao.equals("deletar")) {
        String mensagem;
        
        if (GerenciarLogin.verificarAcesso(request, response)) {
          ArrayList<Menu> menus = perfilDAO.getMenusVinculadosPorPerfil(id);
          if (!menus.isEmpty()) {
            menus.forEach(menu -> {
              try {
                perfilDAO.desvincular(menu.getId(), id);

              } catch (Exception e) {
                e.printStackTrace();
              }
            });
          }
          mensagem = perfilDAO.deletar(id) ? "Deletado com sucesso!" : "Erro ao deletar";

          request.getSession().setAttribute("mensagem", mensagem);
          response.sendRedirect(request.getContextPath() + "/src/perfis/listar-perfil.jsp");
        }

      } else if (acao.equals("alterar")){
        
        if (GerenciarLogin.verificarAcesso(request, response)) {
          Perfil perfil = perfilDAO.getById(id);
          ArrayList<Menu> menus = perfilDAO.getMenusVinculadosPorPerfil(perfil.getId());

          // o idsMenus servirá para verificar se o checkbox deve estar checado ou não 
          ArrayList<Integer> idsMenus = new ArrayList<>();
          menus.forEach(menu -> {
            idsMenus.add(menu.getId());
          });

          request.getSession().setAttribute("perfil", perfil);
          request.getSession().setAttribute("idsMenus", idsMenus);
          response.sendRedirect(request.getContextPath() + "/src/perfis/atualizar-perfil.jsp");
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
    
    Enumeration<String> parameterNames = request.getParameterNames();

    String mensagem = "";
    
    String[] fields = {nome};
    String[] fieldNames = {"nome"};
    Perfil perfil = new Perfil();
    try {
      PerfilDAO perfilDAO = new PerfilDAO();
      Validacao validacao = new Validacao();

      ArrayList<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      // Caso existam campos dentro de camposNencontrados, eu retorno erro ao usuário
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/perfis/cadastrar-perfil.jsp");
        
      } else {
        if (!id.isEmpty()) {
          int idParsed = Integer.parseInt(id);
          perfil.setId(idParsed);

          if (parameterNames.hasMoreElements()) {
            mensagem = "Perfil alterado com sucesso";
          } else {
            mensagem = "Gravado sucesso";
          }
          
          // Crio um array com os menus do sistema
          while (parameterNames.hasMoreElements()) {
            try {
              int idMenu;
              String nomeMenu = parameterNames.nextElement();
              String valorMenu = request.getParameter(nomeMenu);

              if (valorMenu.contains("#desvincular")) {
                String[] arr = valorMenu.split("#");
                idMenu = Integer.parseInt(arr[0]);
                perfilDAO.desvincular(idMenu, idParsed);
              } else {
                idMenu = Integer.parseInt(valorMenu);
                perfilDAO.vincular(idMenu, idParsed);
              }
              

            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
      
      perfil.setNome(nome);
      mensagem = perfilDAO.gravar(perfil) ? mensagem : "Erro interno do servidor";

    } catch (Exception e) {
      e.printStackTrace();
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/perfis/listar-perfil.jsp");

  }
}

