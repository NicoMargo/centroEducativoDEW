/*
 * Package: dew.service
 * Libraries: Apache HttpClient 5.4 (place JARs in WEB-INF/lib)
 */
package dew.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * Cliente HTTP para consumir la API REST de CentroEducativo
 */
public class CentroClient {
    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    public CentroClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Hace login en la API REST y devuelve la API-Key
     */
    public String login(String dni, String password) throws IOException {
        String url = baseUrl + "/login";
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(
            String.format("{\"dni\":\"%s\",\"password\":\"%s\"}", dni, password),
            StandardCharsets.UTF_8));
        post.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse resp = httpClient.execute(post)) {
            int code = resp.getCode();
            if (code == 200) {
                try {
                    // EntityUtils puede lanzar IOException o RuntimeException
                    return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8).trim();
                } catch (Exception e) {
                    throw new IOException("Error parsing login response", e);
                }
            } else {
                throw new IOException("Login failed: HTTP " + code);
            }
        }
    }

    /**
     * Obtiene el listado de alumnos en formato JSON, usando la API-Key
     */
    public String getAlumnos(String apiKey) throws IOException {
        String url = String.format("%s/alumnos?key=%s", baseUrl, apiKey);
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");

        try (CloseableHttpResponse resp = httpClient.execute(get)) {
            int code = resp.getCode();
            if (code == 200) {
                try {
                    return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new IOException("Error parsing alumnos response", e);
                }
            } else {
                throw new IOException("getAlumnos failed: HTTP " + code);
            }
        }
    }
    
    public String getAlumno(String apiKey) throws IOException {
        String url = String.format("%s/alumnos?key=%s", baseUrl, apiKey);
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");

        try (CloseableHttpResponse resp = httpClient.execute(get)) {
            int code = resp.getCode();
            if (code == 200) {
                try {
                    return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new IOException("Error parsing alumnos response", e);
                }
            } else {
                throw new IOException("getAlumnos failed: HTTP " + code);
            }
        }
    }
}