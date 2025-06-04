// src/main/java/dew/main/AsignaturaServlet.java
package dew.main;

import com.google.gson.Gson;
import dew.models.Asignatura;
import dew.models.AsignaturaAlumno;
import dew.service.AsignaturaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Maneja dos operaciones:
 *  1) GET /asignatura?acronimo=XYZ       → mostrar /asignatura/infoAsignatura.jsp
 *  2) GET /asignatura/listaAlumnos?acronimo=XYZ  → devuelve JSON con lista de alumnos/nota
 */
@WebServlet({ "/asignatura", "/listaAlumnosPorAsignatura"})
public class AsignaturaServlet extends HttpServlet {
  private static final Gson GSON = new Gson();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  

    // 1) Comprobar sesión y credenciales REST
    HttpSession s = req.getSession(false);
    if (s == null
        || s.getAttribute("apiKey") == null
        || s.getAttribute("sessionCookie") == null) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
      System.out.println("entre 1");
      return;
    }

    // 2) Determinar cuál de los dos paths es:
    String servletPath = req.getServletPath(); // "/asignatura" o "/asignatura/listaAlumnos"
    String acr = req.getParameter("acronimo");
    if (acr == null || acr.isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro acrónimo");
      return;
    }
    System.out.println(servletPath);

    if ("/asignatura".equals(servletPath)) {
      // → Mostrar la página JSP con detalles de la asignatura (y profesores)
    	System.out.println("entre 1");
      Asignatura info = AsignaturaService.fetchOne(
        getServletContext(), s, acr
      );
      req.setAttribute("asignatura", info);
      req.getRequestDispatcher("/asignatura/infoAsignatura.jsp")
         .forward(req, resp);

    } else if ("/listaAlumnosPorAsignatura".equals(servletPath)) {
    	System.out.println("entre 1");
      // → Responder con JSON: lista de alumnos+nota
      List<AsignaturaAlumno> alumnos =
          AsignaturaService.fetchAlumnosAsignatura(
            getServletContext(), s, acr
          );
      // Devolver JSON puro
      resp.setContentType("application/json;charset=UTF-8");
      String jsonResponse = GSON.toJson(alumnos);
      resp.getWriter().write(jsonResponse);
    }
  }
}
