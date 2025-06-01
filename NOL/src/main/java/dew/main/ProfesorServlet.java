package dew.main;

import dew.models.Profesor;
import dew.service.ProfesorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/profesor")
public class ProfesorServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession s = req.getSession(false);
    if (s == null || s.getAttribute("dni") == null) {
      resp.sendRedirect(req.getContextPath() + "/login.jsp?error=true");
      return;
    }

    String dni = (String) s.getAttribute("dni");

    Profesor profesor = ProfesorService.fetchOne(getServletContext(), s, dni);
    req.setAttribute("profesor", profesor);
    req.getRequestDispatcher("/profesor/profesor.jsp").forward(req, resp);
  }
}

