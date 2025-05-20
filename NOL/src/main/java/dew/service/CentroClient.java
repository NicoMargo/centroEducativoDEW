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

import dew.models.AuthResult;

import org.apache.hc.core5.http.io.entity.EntityUtils;


public class CentroClient {
  private static final String BASE_URL = "http://localhost:9090/CentroEducativo/";
  private static final CloseableHttpClient HTTP = HttpClients.createDefault();

  private CentroClient() { /* constructor privado */ }

  /** Acceso único al singleton */
  public static CentroClient instance() {
    return Holder.INSTANCE;
  }
  private static class Holder {
    private static final CentroClient INSTANCE = new CentroClient();
  }

  /** POST /login → devuelve apiKey + sessionCookie */
  public AuthResult login(String dni, String pass) throws IOException {
    HttpPost post = new HttpPost(BASE_URL + "/login");
    post.setEntity(new StringEntity(
      String.format("{\"dni\":\"%s\",\"password\":\"%s\"}", dni, pass),
      StandardCharsets.UTF_8));
    post.setHeader("Content-Type", "application/json");

    try (CloseableHttpResponse resp = HTTP.execute(post)) {
      int code = resp.getCode();
      if (code != 200) {
        throw new IOException("Login failed: HTTP " + code);
      }
      String apiKey;
      try {
          apiKey = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8).trim();
    	  
      }catch (Exception e){
    	  apiKey = null;
      }

      String sessionCookie = null;
      for (Header h : resp.getHeaders("Set-Cookie")) {
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
   * GET genérico a cualquier recurso REST.
   * @param resource Ruta a partir de "/…", p.ej. "/alumnos" o "/alumnos/123"
   * @param apiKey   API-Key obtenida en login
   * @param cookie   "JSESSIONID=…" de login
   */
  public String getResource(String resource, String apiKey, String cookie)
      throws IOException {
    String url = String.format("%s%s?key=%s", BASE_URL, resource, apiKey);
    HttpGet get = new HttpGet(url);
    get.setHeader("Accept", "application/json");
    get.setHeader("Cookie", cookie);

    try (CloseableHttpResponse resp = HTTP.execute(get)) {
      int code = resp.getCode();
      if (code != 200) {
        throw new IOException("GET " + resource + " failed: HTTP " + code);
      }
      try {
          return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
    	  
      }catch (Exception e){
    	  return "";
      }
    }
  }
}
