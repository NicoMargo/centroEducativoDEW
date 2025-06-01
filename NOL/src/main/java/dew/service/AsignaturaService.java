package dew.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.models.Asignatura;
import dew.models.Profesor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;


public class AsignaturaService {
  private static final Gson GSON = new Gson();

  /**
   * Obtiene los detalles completos de una asignatura por su acrónimo,
   * incluyendo la lista de profesores.
   *
   * @param ctx       contexto de servlet (para la base URL)
   * @param ses       sesión HTTP con apiKey y sessionCookie
   * @param acronimo  código de la asignatura
   * @return Asignatura con campos básicos y lista de profesores
   */
  public static Asignatura fetchOne(ServletContext ctx, HttpSession ses, String acronimo)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");

    // 1) JSON detalle asignatura
    String jsonAsign = CentroClient.instance()
        .getResource("/asignaturas/" + acronimo, apiKey, sessionCookie);
    Asignatura asign = GSON.fromJson(jsonAsign, Asignatura.class);

    // 2) JSON lista de profesores
    String jsonProf = CentroClient.instance()
        .getResource("/asignaturas/" + acronimo + "/profesores", apiKey, sessionCookie);
    List<Profesor> listaProf = GSON.fromJson(
      jsonProf,
      new TypeToken<List<Profesor>>(){}.getType()
    );

    // 3) Enlaza la lista de profesores al objeto Asignatura
    asign.setProfesores(listaProf);

    return asign;
  }
  public static List<Asignatura> getAsignaturasPorProfesorDni(ServletContext ctx, HttpSession ses, String dni)
	      throws IOException {

	    String apiKey = (String) ses.getAttribute("apiKey");
	    String sessionCookie = (String) ses.getAttribute("sessionCookie");

	    String json = CentroClient.instance()
	        .getResource("/profesores/" + dni + "/asignaturas", apiKey, sessionCookie);

	    Type listType = new TypeToken<List<Asignatura>>() {}.getType();
	    return GSON.fromJson(json, listType);
	  }
}
