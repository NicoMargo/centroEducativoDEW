// src/main/java/dew/main/AlumnoJson.java
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

@WebServlet("/alumnoJson")
public class AlumnoJson extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Comprobar autenticación
    if (!checkAuthentication(req, resp)) {
      return;
    }

    HttpSession session = req.getSession(false);
    String dniParam = req.getParameter("dni");
    // 2) Obtener el objeto Alumno ya rellenado (sin foto aún)
    Alumno alumno = AlumnoService.fetchOne(getServletContext(), session, dniParam);
    
    //No se debe enviar las asignaturas por json
    alumno.setAsignaturas(null);

    // 3) Cargar la imagen física desde /webapp/img/<dni>.webp
    String relativePath = "/img/" + dniParam + ".webp";
    String absolutePath = req.getServletContext().getRealPath(relativePath);

    File imgFile = new File(absolutePath);
    if (imgFile.exists()) {
      try {
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        // Codificar a Base64
        String base64 = Base64.getEncoder().encodeToString(bytes);
        alumno.setFoto(base64);
      } catch (IOException e) {
        // Si no podemos leerla, dejamos foto = null
        alumno.setFoto(null);
      }
    } else {
      // Si no existe el archivo, dejamos foto nula (o una cadena vacía)
      alumno.setFoto(null);
    }

    // 4) Serializar a JSON y devolver
    resp.setContentType("application/json;charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");
    Gson gson = new Gson();
    String jsonOut = gson.toJson(alumno);
    resp.getWriter().write(jsonOut);
  }

  /** Envía 401 si no hay sesión o apiKey / sessionCookie faltante */
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
