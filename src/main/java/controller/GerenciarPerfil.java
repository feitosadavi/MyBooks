package controller;

import model.Menu;
import model.Perfil;
import model.PerfilDAO;
import utils.Validacao;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

@WebServlet(name = "GerenciarPerfil", value = "/gerenciar_perfil.do")
public class GerenciarPerfil extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    
    String id = request.getParameter("id");
    String deletar = request.getParameter("deletar");
    
    
    Perfil perfil = new Perfil();
    int idPerfil = Integer.parseInt(id.trim());
    try {
      PerfilDAO perfilDAO = new PerfilDAO();

      if (deletar != null) {
        String mensagem;
        ArrayList<Menu> menus = perfilDAO.getMenusVinculadosPorPerfil(idPerfil);
        if (!menus.isEmpty()) {
          menus.forEach(menu -> {
            try {
              perfilDAO.desvincular(menu.getId(), idPerfil);

            } catch (Exception e) {
              out.print(e);
            }
          });
        }
        
        mensagem = perfilDAO.deletar(idPerfil) ? "Deletado com sucesso!" : "Erro ao deletar";
        
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/perfil/listar-perfil.jsp");
      }

      Perfil resultado = perfilDAO.getById(idPerfil);

      
      perfil.setId(resultado.getId());
      perfil.setNome(resultado.getNome());

      ArrayList<Menu> menus = perfilDAO.getMenusVinculadosPorPerfil(resultado.getId());

      // o idsMenus servirá para verificar se o checkbox deve estar checado ou não 
      ArrayList<Integer> idsMenus = new ArrayList<>();
      menus.forEach(menu -> {
        idsMenus.add(menu.getId());
      });
      
      request.getSession().setAttribute("perfil", perfil);
      request.getSession().setAttribute("idsMenus", idsMenus);
      response.sendRedirect(request.getContextPath() + "/src/perfil/atualizar-perfil.jsp");
      
    } catch (Exception e) {
      out.println(e);
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    String nome = request.getParameter("nome");
    
    String home = request.getParameter("home");
    String perfis = request.getParameter("perfis");
    String menus = request.getParameter("menus");
    String usuario = request.getParameter("usuario");

    String mensagem;
    
    // Campos que o usuário inseriu
    String[] fields = {nome};
    // Campos que devem ser preenchidos
    String[] fieldNames = {"nome"};
    Perfil perfil = new Perfil();
    try {
      PerfilDAO perfilDAO = new PerfilDAO();
      Validacao validacao = new Validacao();
      Stack<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      
      // Caso existam campos dentro de camposNencontrados, eu retorno erro ao usuário
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/perfil/cadastrar-perfil.jsp");
        return;
      }

      if (!id.isEmpty()) {
        int idParsed = Integer.parseInt(id);
        perfil.setId(idParsed);

        // Crio uma pilha com os menus do sistema
        Stack<String> menusVinculados = new Stack<String>();
        menusVinculados.push(home);
        menusVinculados.push(perfis);
        menusVinculados.push(menus);
        menusVinculados.push(usuario);
        System.out.println(menusVinculados);
        menusVinculados.forEach(menuVinculado->{
          try {
            int idMenu;
            if (menuVinculado.contains("#desvincular")) {
              String[] arr = menuVinculado.split("#");
              idMenu = Integer.parseInt(arr[0]);
              perfilDAO.desvincular(idMenu, idParsed);
            } else {
              idMenu = Integer.parseInt(menuVinculado);
              perfilDAO.vincular(idMenu, idParsed);
            }
          } catch (Exception e) {
            System.out.println(e);
          }
        });
      }
      perfil.setNome(nome);
      mensagem = perfilDAO.gravar(perfil) ? "Gravado com sucesso" : "Erro ao gravar no banco de dados";
      
    } catch (Exception e) {
      out.print(e);
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/perfil/listar-perfil.jsp");

  }
}

