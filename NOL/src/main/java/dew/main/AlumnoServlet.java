package dew.main;

import dew.service.CentroClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Devuelve JSON de alumnos usando apiKey y sessionCookie de sesión.
 */
public class AlumnoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sin sesión");
            return;
        }

        String apiKey        = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");
        String dni 			 = (String) session.getAttribute("dni");
        if (apiKey == null || sessionCookie == null || dni == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                           "No autenticado con CentroEducativo");
            return;
        }

        String baseUrl = getServletContext().getInitParameter("centro.baseUrl");
        CentroClient client = CentroClient.getInstance(baseUrl);
        String json = client.getAlumnoPorDNI(apiKey, sessionCookie, dni);

        //resp.setContentType("application/json;charset=UTF-8");
        //resp.getWriter().write(json);
        req.getRequestDispatcher("/alumno/perfilAlumno.jsp").forward(req, resp);
    }
}
