package dew.service;

import com.google.gson.Gson;
import dew.models.Asignatura;
import dew.models.Profesor;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfesorService {
  private static final Gson GSON = new Gson();

  public static Profesor fetchOne(ServletContext ctx, HttpSession ses, String dni)
      throws IOException {

    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");

    // 1) JSON del profesor base
    String profesorJson = CentroClient.instance()
        .getResource("/profesores/" + dni, apiKey, sessionCookie);
    Profesor profesor = GSON.fromJson(profesorJson, Profesor.class);

    // 2) JSON de asignaturas impartidas
    String asignaturasJson = CentroClient.instance()
        .getResource("/profesores/" + dni + "/asignaturas", apiKey, sessionCookie);
    List<Asignatura> asignaturas = GSON.fromJson(
        asignaturasJson,
        new com.google.gson.reflect.TypeToken<List<Asignatura>>() {}.getType()
    );

    profesor.setAsignaturas(asignaturas);
    return profesor;
  }
}

