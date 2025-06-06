package dew.main;

import com.google.gson.JsonObject;
import dew.service.CentroClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase ActualizarNotaServlet:
 * ----------------------------
 * Este servlet procesa solicitudes HTTP PUT que contienen un JSON con los campos { "dni": "...", "acronimo": "...", "nota": "7.0" }.
 * A continuación:
 *   1) Verifica que haya una sesión válida y que existan apiKey y sessionCookie en la HttpSession.
 *   2) Extrae del cuerpo de la petición el objeto JSON con dni, acrónimo de la asignatura y la nota.
 *   3) Construye una petición HTTP PUT al servicio CentroEducativo en la ruta /alumnos/{dni}/asignaturas/{acronimo}?key={apiKey}, incluyendo la cookie de sesión.
 *   4) Envía la nota (como cuerpo de la petición) al servicio remoto.
 *   5) Gestiona la respuesta del servicio CentroEducativo y devuelve al cliente frontend el resultado apropiado:
 *      – “OK” si la actualización fue satisfactoria,
 *      – errores HTTP 403, 404 o 500 según sea el caso.
 */
@WebServlet("/actualizarNotaPorAsignatura")
public class ActualizarNotaServlet extends HttpServlet {

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Verificar autenticación: reemplazar la comprobación de login de Tomcat con la sesión de CentroEducativo
    HttpSession session = req.getSession(false);
    if (session == null ||
        session.getAttribute("apiKey") == null ||
        session.getAttribute("sessionCookie") == null) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado con CentroEducativo");
      return;
    }

    String apiKey        = (String) session.getAttribute("apiKey");
    String sessionCookie = (String) session.getAttribute("sessionCookie");

    // 2) Leer el JSON del cuerpo (body) de la petición
    //    Se espera un JSON con los campos: dni, acronimo y nota (numérica).
    JsonObject jsonRequest = CentroClient.parseJsonBody(req);
    if (jsonRequest == null
        || !jsonRequest.has("dni")
        || !jsonRequest.has("acronimo")
        || !jsonRequest.has("nota")) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos en JSON");
      return;
    }

    String dni       = jsonRequest.get("dni").getAsString();
    String acronimo  = jsonRequest.get("acronimo").getAsString();
    int nota = jsonRequest.get("nota").getAsNumber().intValue();

    // 3) Construir la URL para la petición PUT a CentroEducativo:
    //    PUT /alumnos/{dni}/asignaturas/{acronimo}?key={apiKey}
    String path = "alumnos/" + dni + "/asignaturas/" + acronimo;
    String base = CentroClient.getBaseUrl();
    URL url = new URL(base + path + "?key=" + apiKey);


    // 4) Abrir conexión HTTP y configurar cabeceras

    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("PUT");
    con.setRequestProperty("Content-Type", "application/json");
    // Incluimos la cookie de sesión que guardamos en HttpSession
    con.setRequestProperty("Cookie", sessionCookie);
    con.setDoOutput(true);

    // Enviar la nota en el cuerpo de la petición como texto (p.ej. "7")
    try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(con.getOutputStream(), "UTF-8"))) {
        writer.write(Integer.toString(nota));
    }

    // 5) Obtener el código de respuesta de CentroEducativo y mapearlo al cliente
    int statusCode = con.getResponseCode();
    if (statusCode == HttpURLConnection.HTTP_OK) {
      // Todo OK: devolvemos texto “OK” para que el front-end detecte resp.ok == true
      resp.setContentType("text/plain;charset=UTF-8");
      resp.getWriter().write("OK");
    } else if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
      // 403 Forbidden: el profesor no imparte esa asignatura o no tiene permiso
      resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                     "El profesor no imparte esa asignatura o acceso prohibido");
    } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
      // 404 Not Found: alumno o asignatura no encontrados
      resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                     "Alumno o asignatura no encontrados en CentroEducativo");
    } else {
      // Cualquier otro error: devolvemos 500 para que el front-end maneje el fallo genérico
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                     "Error al actualizar nota en CentroEducativo");
    }
  }
}
