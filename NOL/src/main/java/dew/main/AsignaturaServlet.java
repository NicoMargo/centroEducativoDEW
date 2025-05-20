// src/main/java/dew/main/AsignaturaServlet.java
package dew.main;

import dew.models.Asignatura;
import dew.service.AsignaturaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/asignatura")
public class AsignaturaServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Comprobar sesión y credenciales
    HttpSession s = req.getSession(false);
    if (s == null || s.getAttribute("apiKey") == null || s.getAttribute("sessionCookie") == null) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
      return;
    }

    // 2) Leer parámetro "acronimo"
    String acr = req.getParameter("acronimo");
    if (acr == null || acr.isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro acronimo");
      return;
    }

    // 3) Obtener info via service
    Asignatura info = AsignaturaService.fetchOne(
      getServletContext(), s, acr
    );

    // 4) Pasar al JSP
    req.setAttribute("asignatura", info);
    req.getRequestDispatcher("/asignatura/infoAsignatura.jsp")
       .forward(req, resp);
  }
}
