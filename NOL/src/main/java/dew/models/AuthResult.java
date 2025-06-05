package dew.models;

/**
 * Resultado del login: apiKey + cookie de sesión (JSESSIONID=...).
 * Contiene la clave de acceso proporcionada por el servicio y la
 * cookie de sesión asociada.
 */
public class AuthResult {
    private final String apiKey;
    private final String sessionCookie;

    /**
     * Constructor que inicializa el resultado del login.
     * @param apiKey clave de acceso proporcionada por el servidor
     * @param sessionCookie cookie de sesión (JSESSIONID) para peticiones posteriores
     */
    public AuthResult(String apiKey, String sessionCookie) {
        this.apiKey        = apiKey;
        this.sessionCookie = sessionCookie;
    }

    /**
     * Obtiene la clave de acceso (API key) resultante del login.
     * @return apiKey como String
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Obtiene la cookie de sesión (JSESSIONID) devuelta tras el login.
     * @return sessionCookie como String
     */
    public String getSessionCookie() {
        return sessionCookie;
    }
}
