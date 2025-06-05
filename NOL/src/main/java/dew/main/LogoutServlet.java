package dew.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dew.service.CentroClient;
import jakarta.servlet.http.*;

/**
 * Clase LogoutServlet:
 * --------------------
 * Este servlet gestiona la acción de cierre de sesión (logout) del usuario.
 * Al ejecutar una petición GET a "/logout":
 *   1) Recupera la sesión HTTP actual y la invalida, eliminando todos sus atributos.
 *   2) Limpia las cookies de sesión guardadas en CentroClient.
 *   3) Crea una cookie "JSESSIONID" vacía con valor de duración cero para sobrescribir la anterior.
 *   4) Redirige al usuario a la página pública "index.jsp" dentro del mismo contexto de la aplicación.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // 1) Recuperar la sesión actual sin crear una nueva (getSession(false))
    HttpSession session = req.getSession(false);
    if (session != null) {
      // Si existe sesión, invalidarla para cerrar sesión y eliminar atributos
      session.invalidate();
      // Limpiar cualquier cookie almacenada por CentroClient
      CentroClient.clearCookies();
    }

    // 2) Crear una cookie "JSESSIONID" vacía y de duración cero:
    //    Esto fuerza al navegador a eliminar la cookie de sesión.
    Cookie cookie = new Cookie("JSESSIONID", "");
    cookie.setMaxAge(0);
    // Establecer la ruta de la cookie al contexto de la aplicación para que tenga el mismo ámbito
    cookie.setPath(req.getContextPath());
    resp.addCookie(cookie);

    // 3) Redirigir al usuario a la página pública "index.jsp"
    //    Utilizamos req.getContextPath() para obtener la ruta base de la aplicación
    resp.sendRedirect(req.getContextPath() + "/index.jsp");
  }
}
