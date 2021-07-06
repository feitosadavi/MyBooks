package controller;

import model.Data;
import model.DataDAO;
import model.Horario;
import model.HorarioDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@WebServlet(name = "GerenciarAgendamento", value = "/gerenciar_agendamento.do")
public class GerenciarAgendamento extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String agendamento = request.getParameter("agendamento");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String agendamento = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    String mensagem;
    try {
      JSONObject jsonObject = new JSONObject(agendamento);
      JSONArray dias = jsonObject.getJSONArray("dias");
      JSONArray horariosTotais = jsonObject.getJSONArray("horariosTotais");
      HorarioDAO horarioDAO = new HorarioDAO();
      
      horarioDAO.deletarTodos();
      DataDAO dataDAO = new DataDAO();
      dataDAO.deletar(0); // deleta tudo antes de gravar os novos
      for (int i = 0; i < horariosTotais.length(); i++) {
        String hora = (String) horariosTotais.get(i);

        Horario horario = new Horario();
        horario.setHorario(hora);
        horario.setQtd(3);
        horario.setStatus(1);

        horarioDAO.gravar(horario);
      }
      

      Date date = new Date();
      for (int i = date.getDate(); i < dias.length(); i++) {
        JSONObject diaObj = dias.getJSONObject(i);

        Data data = new Data();
        data.setData(diaObj.getString("dia"));
        data.setStatus(diaObj.getInt("status"));

        String[] horariosDisponiveis = diaObj.getJSONArray("horariosDisponiveis").toString().replace("[", "").replace("]", "").split(",");
        ArrayList<Horario> horarios = new ArrayList<Horario>();
        
        Arrays.stream(horariosDisponiveis).forEach(horario -> {
          try {
            Horario h = horarioDAO.getPorNome(horario);
            horarios.add(h);
          } catch (Exception e) {
            e.printStackTrace();
          }

        });
        data.setHorarios(horarios);
        System.out.println(data);
        dataDAO.gravar(data);
      }
      
      mensagem = "Agendamento atualizado com sucesso!";

    } catch (Exception e) {
      mensagem = "Erro interno do servidor!";
      e.printStackTrace();
    }
    request.getSession().setAttribute("mensagem", mensagem);
    response.sendRedirect(request.getContextPath() + "/src/livros/listar-livro.jsp");
  }
}
