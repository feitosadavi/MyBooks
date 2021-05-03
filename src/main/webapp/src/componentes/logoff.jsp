<%
  session.invalidate();
  response.sendRedirect(request.getContextPath() + "/src/login/form-login.jsp");
%>
