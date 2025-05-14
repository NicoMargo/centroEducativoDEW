package dew.loginCentro;

import jakarta.servlet.*;
import java.io.*;
import java.net.*;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String baseUrl = ctx.getInitParameter("centro.baseUrl");
        String dni     = ctx.getInitParameter("centro.loginDni");
        String pwd     = ctx.getInitParameter("centro.loginPassword");
        
        CookieHandler.setDefault(new CookieManager());

        try {
            // Construir el JSON de login
	            String payload = String.format(
                "{\"dni\":\"%s\",\"password\":\"%s\"}", 
                dni, pwd);

            URL url = new URL(baseUrl + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");

            // Enviar credenciales
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes("UTF-8"));
            }

            // Leer respuesta
            if (conn.getResponseCode() == 200) {
                try (BufferedReader rd = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String apiKey = rd.readLine().trim();
                    ctx.setAttribute("centro.apiKey", apiKey);
                    ctx.log("StartupListener: API-Key obtenida -> " + apiKey);
                }
            } else {
                throw new RuntimeException(
                  "Login fallido al arrancar: HTTP " + conn.getResponseCode());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error en StartupListener", e);
        }
    }

}
