package controller;

import model.Perfil;
import model.PerfilDAO;
import utils.Validacao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

@WebServlet(name = "GerenciarPerfil", value = "/gerenciar_perfil.do")
public class GerenciarPerfil extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    
    String id = request.getParameter("id");
    String deletar = request.getParameter("deletar");
    Perfil perfil = new Perfil();
    
    String idLimpa = id.trim();
    int idParsed = Integer.parseInt(idLimpa);
    try {
      PerfilDAO perfilDAO = new PerfilDAO();

      if (deletar == null) {
        Perfil resultado = perfilDAO.getById(idParsed);

        perfil.setId(resultado.getId());
        perfil.setNome(resultado.getNome());

        request.getSession().setAttribute("id", perfil.getId());
        request.getSession().setAttribute("nome", perfil.getNome());
        response.sendRedirect(request.getContextPath() + "/src/perfil/atualizar-perfil.jsp");
        
      } else {
        String mensagem;
        
        if (perfilDAO.deletar(idParsed)) {
          mensagem = "Deletado com sucesso!";
        } else {
          mensagem = "Erro ao deletar";
        }
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/perfil/listar-perfil.jsp");
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
    
    String mensagem;
    
    String[] fields = {nome};
    String[] fieldNames = {"nome"};
    Perfil perfil = new Perfil();
    try {
      PerfilDAO perfilDAO = new PerfilDAO();
      Validacao validacao = new Validacao();
      Stack<String> camposNencontrados = validacao.camposRequeridos(fieldNames, fields);
      if (!camposNencontrados.isEmpty()) {
        mensagem = "Campos não inseridos: " + camposNencontrados;
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/src/perfil/cadastrar-perfil.jsp");
        return;
      }
      
      if (!id.isEmpty()) {
        perfil.setId(Integer.parseInt(id));
      }
      perfil.setNome(nome);
      
      if (perfilDAO.gravar(perfil)) {
        mensagem = "Gravado com sucesso";
      } else {
        mensagem = "Erro ao gravar no banco de dados";
      }
    } catch (Exception e) {
      out.print(e);
      mensagem = "Erro ao executar";
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/perfil/listar-perfil.jsp");

  }
}

