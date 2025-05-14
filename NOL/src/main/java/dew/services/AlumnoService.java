// src/main/java/dew/service/CentroClient.java
package dew.services;

import java.io.*;
import java.net.*;
import jakarta.servlet.ServletContext;

public class AlumnoService {
    private final String baseUrl;
    private final String apiKey;

    public AlumnoService(ServletContext ctx) {
        this.baseUrl = ctx.getInitParameter("centro.baseUrl");
        this.apiKey = (String)ctx.getAttribute("centro.apiKey");
    }

    public String getAlumnos() throws IOException {
        String endpoint = String.format("%s/alumnos?key=%s", baseUrl, apiKey);
        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        try (InputStream in = conn.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
    // puedes añadir más métodos: getAsignaturas(), postLogin(), etc.
}