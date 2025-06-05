package dew.main;

import com.google.gson.Gson;
import dew.models.Alumno;
import dew.service.AlumnoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Clase AlumnoJson:
 * -----------------
 * Este servlet atiende peticiones GET a la ruta "/alumnoJson". Su comportamiento es:
 *   1) Verificar que exista una sesión válida con apiKey y sessionCookie. Si no, devuelve 401.
 *   2) Obtener el parámetro "dni" de la petición y cargar el objeto Alumno correspondiente (sin foto).
 *   3) Construir la ruta relativa dentro del contexto "/img/{dni}.webp" para buscar la imagen del alumno.
 *      - Con getRealPath(...) se convierte esa ruta en la ruta absoluta del sistema de archivos.
 *      - Si existe el archivo, se lee, se codifica en Base64 y se asigna a alumno.foto; si no, foto queda nulo.
 *   4) Establecer asignaturas en null para no enviarlas por JSON.
 *   5) Serializar el objeto Alumno (incluyendo la foto en Base64 si la hubo) a JSON y devolverlo como aplicación/json.
 */
@WebServlet("/alumnoJson")
public class AlumnoJson extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Comprobar autenticación: apiKey y sessionCookie en sesión
    if (!checkAuthentication(req, resp)) {
      return;
    }

    HttpSession session = req.getSession(false);
    // Obtener el parámetro "dni" de la URL
    String dniParam = req.getParameter("dni");

    // 2) Recuperar el objeto Alumno (sin foto) desde el servicio
    Alumno alumno = AlumnoService.fetchOne(getServletContext(), session, dniParam);

    // No enviar las asignaturas en la respuesta JSON
    alumno.setAsignaturas(null);

    // 3) Cargar la imagen física desde la ruta de contexto "/img/{dni}.webp"
    String relativePath = "/img/" + dniParam + ".webp";
    try (InputStream is = req.getServletContext().getResourceAsStream(relativePath)) {
        if (is != null) {
            // Leemos todos los bytes del InputStream
            byte[] bytes = is.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            alumno.setFoto(base64);
        } else {
            // Si no existe el recurso en /img/{dni}.webp
            alumno.setFoto(null);
        }
    } catch (IOException e) {
        alumno.setFoto(null);
    }

    // 4) Serializar el objeto Alumno a JSON y devolverlo
    resp.setContentType("application/json;charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");
    Gson gson = new Gson();
    String jsonOut = gson.toJson(alumno);
    resp.getWriter().write(jsonOut);
  }

  /**
   * Envía 401 si no hay sesión o faltan apiKey / sessionCookie en HttpSession
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
}
