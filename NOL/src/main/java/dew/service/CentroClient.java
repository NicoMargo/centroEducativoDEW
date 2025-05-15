package dew.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * Singleton thread‐safe para llamadas a CentroEducativo, sin CookieStore interno.
 */
public class CentroClient {
    private static volatile CentroClient INSTANCE;
    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    private CentroClient(String baseUrl) {
        this.baseUrl    = baseUrl;
        this.httpClient = HttpClients.createDefault();
    }

    /** Obtiene la instancia única */
    public static CentroClient getInstance(String baseUrl) {
        if (INSTANCE == null) {
            synchronized (CentroClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CentroClient(baseUrl);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Hace POST /login → devuelve apiKey y cookie JSESSIONID
     */
    public AuthResult login(String dni, String password) throws IOException {
        String url = baseUrl + "/login";
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(
            String.format("{\"dni\":\"%s\",\"password\":\"%s\"}", dni, password),
            StandardCharsets.UTF_8));
        post.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse resp = httpClient.execute(post)) {
            int code = resp.getCode();
            if (code != 200) {
                throw new IOException("Login failed: HTTP " + code);
            }
            // cuerpo = apiKey
            String apiKey;
            try {
                apiKey = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8).trim();
            } catch (Exception e) {
                throw new IOException("Error parsing login response body", e);
            }

            // extraer primera cookie JSESSIONID
            Header[] cookies = resp.getHeaders("Set-Cookie");
            String sessionCookie = null;
            for (Header h : cookies) {
                String v = h.getValue();
                if (v.startsWith("JSESSIONID=")) {
                    sessionCookie = v.split(";", 2)[0];
                    break;
                }
            }
            if (sessionCookie == null) {
                throw new IOException("No JSESSIONID cookie in login response");
            }
            return new AuthResult(apiKey, sessionCookie);
        }
    }

    /**
     * Hace GET /alumnos?key=… añadiendo la cookie de sesión manualmente.
     */
    public String getAlumnos(String apiKey, String sessionCookie) throws IOException {
        String url = String.format("%s/alumnos?key=%s", baseUrl, apiKey);
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");
        get.setHeader("Cookie", sessionCookie);

        try (CloseableHttpResponse resp = httpClient.execute(get)) {
            int code = resp.getCode();
            if (code != 200) {
                throw new IOException("getAlumnos failed: HTTP " + code);
            }
            try {
                return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new IOException("Error parsing getAlumnos response body", e);
            }
        }
    }
    
    public String getAlumnoPorDNI(String apiKey, String sessionCookie, String dni) throws IOException {
        String url = String.format("%s/alumnos/%s?key=%s", baseUrl,dni, apiKey);
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");
        get.setHeader("Cookie", sessionCookie);

        try (CloseableHttpResponse resp = httpClient.execute(get)) {
            int code = resp.getCode();
            if (code != 200) {
                throw new IOException("getAlumno failed: HTTP " + code);
            }
            try {
                return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new IOException("Error parsing getAlumnos response body", e);
            }
        }
    }
}
