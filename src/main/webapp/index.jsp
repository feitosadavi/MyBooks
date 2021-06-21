<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.*" %>
<%@ page import="java.lang.reflect.Array" %>


<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@include file="src/componentes/head.jsp"%>
  <body>
    <%@include file="/src/componentes/navbar.jsp"%>
    <div class="container">
      <%@include file="/src/componentes/mensagem.jsp"%>

<%--      <%--%>
<%--        System.out.println("olá mundo");--%>
<%--        Usuario usuario = (Usuario) request.getSession().getAttribute("ulogado");--%>
<%--        UsuarioDAO usuarioDAO = null;--%>
<%--        try {--%>
<%--          usuarioDAO = new UsuarioDAO();--%>
<%--          Map<String, Object> livrosLocados = null;--%>
<%--          livrosLocados = usuarioDAO.getLivrosLocados(usuario.getId());--%>
<%--          ArrayList<Livro> livros = (ArrayList<Livro>) livrosLocados.get("livros");--%>
<%--          --%>
<%--//          for (Livro livroLocado : livros) {--%>
<%--//            if (livroLocado.getId() === livro.getId()) {--%>
<%--//              --%>
<%--//            }--%>
<%--          }--%>
<%--          --%>
<%--          System.out.println(livrosLocados);--%>
<%--        } catch (Exception e) {--%>
<%--          e.printStackTrace();--%>
<%--        }--%>
<%--//          assert livrosLocados != null;--%>
<%--//          System.out.println(livrosLocados.get("livros"));--%>
<%--//          pageContext.setAttribute("livrosLocados", livrosLocados);--%>

      %>
      <h2>
      
      
      
      </h2>
    </div>
  </body>
</html>
