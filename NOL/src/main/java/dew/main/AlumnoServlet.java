package dew.main;

import dew.models.Alumno;
import dew.service.AlumnoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/alumno","/alumnos"})
public class AlumnoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (!checkAuthentication(req, resp)) {
      return;
    }

    String path = req.getServletPath();
    switch (path) {
      case "/alumno":
        showPerfil(req, resp);
        break;
      case "/alumnos":
        showLista(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  /** Comprueba que exista sesión válida; si no, envía 401 */
  private boolean checkAuthentication(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    HttpSession session = req.getSession(false);
    
    if (session == null ||
        session.getAttribute("apiKey") == null ||
        session.getAttribute("sessionCookie") == null) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                     "No autenticado con CentroEducativo");
      return false;
    }
    return true;
  }

  /** Carga un único alumno y lo muestra en perfilAlumno.jsp */
  private void showPerfil(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String dni = (String) session.getAttribute("dni");

    Alumno alumno = AlumnoService.fetchOne(getServletContext(), session, dni);
    req.setAttribute("alumno", alumno);
    req.getRequestDispatcher("/alumno/perfilAlumno.jsp")
       .forward(req, resp);
  }

  /** Carga todos los alumnos y los muestra en listaAlumnos.jsp */
  private void showLista(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String dni = (String) session.getAttribute("dni");

    Alumno  alumno = AlumnoService.fetchOne(getServletContext(), session, dni);
    req.setAttribute("alumnos", alumno);
    req.getRequestDispatcher("/alumno/listaAlumnos.jsp")
       .forward(req, resp);
  }
}
