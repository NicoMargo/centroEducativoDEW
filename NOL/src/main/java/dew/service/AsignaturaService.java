package dew.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.models.Asignatura;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servicios estáticos para manejar Asignaturas,
 * reutilizando CentroClient para todas las llamadas HTTP.
 */
public class AsignaturaService {
  private static final Gson GSON = new Gson();

  /**
   * Obtiene la lista de asignaturas (con nota) para el alumno identificado en sesión.
   *
   * @param ctx contexto de servlet (para baseUrl ya en CentroClient)
   * @param ses sesión HTTP con apiKey, sessionCookie y dni
   * @return List<Asignatura> con campos acronimo y nota
   */
  public static List<Asignatura> fetchForAlumno(ServletContext ctx, HttpSession ses)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");
    String dni           = (String) ses.getAttribute("dni");

    String json = CentroClient.instance()
        .getResource("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

    return GSON.fromJson(
      json,
      new TypeToken<List<Asignatura>>(){}.getType()
    );
  }

  /**
   * Obtiene los detalles completos de una asignatura por su acrónimo.
   *
   * @param ctx       contexto de servlet
   * @param ses       sesión HTTP con apiKey y sessionCookie
   * @param acronimo  código de la asignatura
   * @return Asignatura con todos los campos (acronimo, nombre, curso, cuatrimestre, creditos)
   */
  public static Asignatura fetchOne(ServletContext ctx, HttpSession ses, String acronimo)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");

    String json = CentroClient.instance()
        .getResource("/asignaturas/" + acronimo, apiKey, sessionCookie);

    return GSON.fromJson(json, Asignatura.class);
  }
}
