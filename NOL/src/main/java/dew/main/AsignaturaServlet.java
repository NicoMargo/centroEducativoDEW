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
 * Clase AsignaturaServlet:
 * ------------------------
 * Este servlet maneja dos operaciones basadas en la ruta solicitada:
 *   1) GET /asignatura?acronimo=XYZ
 *      → Carga los detalles de la asignatura indicada (con acrónimo XYZ),
 *        añade una descripción de relleno (Lorem ipsum) al objeto Asignatura
 *        y reenvía a la JSP infoAsignatura.jsp para mostrar la información.
 *   2) GET /listaAlumnosPorAsignatura?acronimo=XYZ
 *      → Obtiene la lista de alumnos matriculados en la asignatura XYZ junto con sus notas,
 *        convierte la lista a JSON y la envía como respuesta con Content-Type application/json.
 */
@WebServlet({ "/asignatura", "/listaAlumnosPorAsignatura"})
public class AsignaturaServlet extends HttpServlet {
  // Instancia de Gson para convertir objetos Java a JSON fácilmente
  private static final Gson GSON = new Gson();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Comprobar que haya sesión válida con apiKey y sessionCookie en HttpSession
    HttpSession s = req.getSession(false);
    if (s == null
        || s.getAttribute("apiKey") == null
        || s.getAttribute("sessionCookie") == null) {
      // Si no está autenticado, devolvemos 401 Unauthorized
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
      return;
    }

    // 2) Determinar cuál de los dos paths se está invocando:
    //    - "/asignatura"
    //    - "/listaAlumnosPorAsignatura"
    String servletPath = req.getServletPath();
    // Obtener parámetro "acronimo" de la URL (requerido en ambos casos)
    String acr = req.getParameter("acronimo");
    if (acr == null || acr.isEmpty()) {
      // Si falta el parámetro acrónimo, devolvemos 400 Bad Request
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro acrónimo");
      return;
    }

    if ("/asignatura".equals(servletPath)) {
      // → Operación 1: Mostrar la página JSP con detalles de la asignatura

      // Llamada al servicio para obtener la información de la asignatura por acrónimo
      Asignatura info = AsignaturaService.fetchOne(
        getServletContext(), s, acr
      );

      // Se agrega descripción de relleno (Lorem ipsum) tal como especifica el requisito
      String descripcion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
          + "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad "
          + "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
          + "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit "
          + "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat "
          + "non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
      info.setDescripcion(descripcion);

      // Pasamos el objeto Asignatura a la JSP como atributo "asignatura"
      req.setAttribute("asignatura", info);
      // Forward a la página JSP que mostrará la información de la asignatura
      req.getRequestDispatcher("/asignatura/infoAsignatura.jsp")
         .forward(req, resp);

    } else if ("/listaAlumnosPorAsignatura".equals(servletPath)) {
      // → Operación 2: Devolver JSON con lista de alumnos y nota de la asignatura

      // Obtenemos la lista de AsignaturaAlumno (alumno + nota) para el acrónimo dado
      List<AsignaturaAlumno> alumnos =
          AsignaturaService.fetchAlumnosAsignatura(
            getServletContext(), s, acr
          );

      // Configuramos la respuesta como JSON y codificación UTF-8
      resp.setContentType("application/json;charset=UTF-8");
      // Convertimos la lista de AsignaturaAlumno a JSON y la enviamos en el body
      String jsonResponse = GSON.toJson(alumnos);
      resp.getWriter().write(jsonResponse);
    }
  }
}
