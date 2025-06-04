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
 * Servlet que recibe por POST un JSON con { "dni": "...", "acronimo": "...", "nota": "7.0" }
 * y se lo envía a CentroEducativo (PUT /alumnos/{dni}/asignaturas/{acronimo}?key=...).
 */
@WebServlet("/actualizarNotaPorAsignatura")
public class ActualizarNotaServlet extends HttpServlet {

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1) Verificar autenticación: que exista sesión y apiKey + sessionCookie (cookie)
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
    //    Se espera algo como:
    //    {
    //      "dni": "12345678W",
    //      "acronimo": "DEW",
    //      "nota": "7.0"
    //    }
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
    
    // 3) Montar la URL PUT /alumnos/{dni}/asignaturas/{acronimo}?key={apiKey}
    String path = "alumnos/" + dni + "/asignaturas/" + acronimo;
    String base = CentroClient.getBaseUrl();
    URL url = new URL(base + path + "?key=" + apiKey);

    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("PUT");
    con.setRequestProperty("Content-Type", "application/json");
    // Incluir la cookie de sesión para que CentroEducativo reconozca la sesión
    con.setRequestProperty("Cookie", sessionCookie);
    con.setDoOutput(true);



    try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(con.getOutputStream(), "UTF-8"))) {

        writer.write(Integer.toString(nota));
    }

    // 5) Leer el código de respuesta
    int statusCode = con.getResponseCode();
    if (statusCode == HttpURLConnection.HTTP_OK) {
      // Todo OK: devolvemos texto “OK” para que el fetch en JS lo reciba con resp.ok == true
      resp.setContentType("text/plain;charset=UTF-8");
      resp.getWriter().write("OK");
    } else if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                     "El profesor no imparte esa asignatura o acceso prohibido");
    } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                     "Alumno o asignatura no encontrados en CentroEducativo");
    } else {
      // En cualquier otro caso devolvemos 500 para que JS muestre su mensaje de error
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                     "Error al actualizar nota en CentroEducativo");
    }
  }
}