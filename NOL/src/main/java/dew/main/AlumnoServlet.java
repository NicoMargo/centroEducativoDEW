package dew.main;

import dew.models.Alumno;
import dew.service.AlumnoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Clase AlumnoServlet:
 * --------------------
 * Este servlet gestiona las solicitudes GET para mostrar información de alumnos.
 * Según la ruta solicitada:
 *   - "/alumno": carga y muestra el perfil del alumno cuya sesión está activa.
 *   - "/alumnos": carga y muestra la lista de alumnos (en este caso, actualmente carga un único alumno para ejemplo).
 * Además, comprueba que exista una sesión válida con apiKey y sessionCookie antes de permitir el acceso.
 */
@WebServlet({"/alumno","/alumnos"})
public class AlumnoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Verificar que el usuario esté autenticado ante CentroEducativo
    if (!checkAuthentication(req, resp)) {
      return; // Si no está autenticado, se envía 401 y se detiene el procesamiento
    }

    String path = req.getServletPath();
    switch (path) {
      case "/alumno":
        // Mostrar perfil del alumno actual
        showPerfil(req, resp);
        break;
      case "/alumnos":
        // Mostrar lista de alumnos
        showLista(req, resp);
        break;
      default:
        // Ruta no encontrada: enviar 404
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  /**
   * Comprueba que exista una sesión válida con los atributos necesarios (apiKey y sessionCookie).
   * Si falta alguno, envía un error 401 Unauthorized y devuelve false.
   */
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

  /**
   * Carga el perfil del alumno actual (a partir del DNI almacenado en sesión)
   * y lo reenvía a la vista perfilAlumno.jsp para mostrarlo.
   */
  private void showPerfil(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String dni = (String) session.getAttribute("dni");

    // Obtenemos los datos del alumno desde AlumnoService
    Alumno alumno = AlumnoService.fetchOne(getServletContext(), session, dni);
    // Guardamos el objeto Alumno como atributo para la vista
    req.setAttribute("alumno", alumno);
    // Forward a la página JSP que muestra el perfil del alumno
    req.getRequestDispatcher("/alumno/perfilAlumno.jsp")
       .forward(req, resp);
  }

  /**
   * Carga la lista de alumnos. Actualmente se está obteniendo un único alumno
   * para mostrar como ejemplo, y lo reenvía a la vista listaAlumnos.jsp.
   */
  private void showLista(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String dni = (String) session.getAttribute("dni");

    // En este ejemplo, obtenemos el mismo alumno para la lista
    Alumno alumno = AlumnoService.fetchOne(getServletContext(), session, dni);
    // Guardamos la lista de alumnos (aquí solo un alumno) como atributo para la vista
    req.setAttribute("alumnos", alumno);
    // Forward a la página JSP que muestra la lista de alumnos
    req.getRequestDispatcher("/alumno/listaAlumnos.jsp")
       .forward(req, resp);
  }
}
