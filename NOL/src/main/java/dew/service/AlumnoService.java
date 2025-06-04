package dew.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.models.Alumno;
import dew.models.Asignatura;
import dew.models.AsignaturaAlumno;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AlumnoService {
  private static final Gson GSON = new Gson();

  public static Alumno fetchOne(ServletContext ctx, HttpSession ses, String dni)

      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");

    // 1) JSON del alumno base
    String alumnoJson = CentroClient.instance()
        .getResource("/alumnos/" + dni, apiKey, sessionCookie);
    Alumno alumno = GSON.fromJson(alumnoJson, Alumno.class);

    // 2) JSON de sus asignaturas (array de objetos)
    String asigJson = CentroClient.instance()
        .getResource("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

    // 3) Parsear directamente a List<Asignatura>
    List<Asignatura> asignaturas = GSON.fromJson(
      asigJson,
      new TypeToken<List<Asignatura>>(){}.getType()
    );

    // 4) Inyectar la lista en el POJO
    alumno.setAsignaturas(asignaturas);
    return alumno;
  }
  
}
