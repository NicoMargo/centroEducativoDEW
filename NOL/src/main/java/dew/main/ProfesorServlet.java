package dew.main;

import dew.models.Profesor;
import dew.service.ProfesorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Clase ProfesorServlet:
 * ----------------------
 * Este servlet atiende peticiones GET a la ruta "/profesor". Su comportamiento es:
 *   1) Verificar que exista una sesión válida y que contenga el atributo "dni".
 *      - Si no hay sesión o falta el DNI, redirige al usuario a la página de login con un parámetro de error.
 *   2) Si la sesión es válida, obtiene el DNI del profesor de la HttpSession.
 *   3) Invoca a ProfesorService.fetchOne para recuperar los datos del profesor a partir del DNI.
 *   4) Guarda el objeto Profesor como atributo en la petición y hace forward a la JSP que mostrará el perfil.
 */
@WebServlet("/profesor")
public class ProfesorServlet extends HttpServlet {
  /**
   * Maneja peticiones GET para mostrar la página principal de profesor.
   * - Verifica si la sesión existe y contiene el DNI.
   * - Si no, redirige al login con error.
   * - Si existe, obtiene datos del profesor y los envía al JSP.
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Intentar recuperar la sesión existente (sin crear una nueva)
    HttpSession s = req.getSession(false);
    if (s == null || s.getAttribute("dni") == null) {
      // Si no hay sesión o no se encuentra el atributo "dni", redirigimos a login.jsp con error=true
      resp.sendRedirect(req.getContextPath() + "/login.jsp?error=true");
      return;
    }

    // 2) Obtener el DNI del profesor almacenado en sesión
    String dni = (String) s.getAttribute("dni");

    // 3) Llamar al servicio para obtener los datos del profesor según su DNI
    Profesor profesor = ProfesorService.fetchOne(getServletContext(), s, dni);
    // Guardar el objeto Profesor como atributo para la vista
    req.setAttribute("profesor", profesor);
    // 4) Hacer forward a la JSP que muestra la información del profesor
    req.getRequestDispatcher("/profesor/profesor.jsp").forward(req, resp);
  }
}
