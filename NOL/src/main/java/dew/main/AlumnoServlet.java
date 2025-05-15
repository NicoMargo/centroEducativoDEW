package dew.main;

import dew.service.CentroClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlumnoServlet extends HttpServlet {
    private CentroClient client;

    @Override
    public void init() {
        String baseUrl = getServletContext().getInitParameter("centro.baseUrl");
        client = new CentroClient(baseUrl);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String apiKey = (String) req.getSession().getAttribute("apiKey");
        if (apiKey == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                           "No autenticado con CentroEducativo");
            return;
        }

        String json = client.getAlumnos(apiKey);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(json);
    }
}