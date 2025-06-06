package dew.filter;

import dew.helper.InputValidator;
import dew.models.AuthResult;
import dew.service.CentroClient;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase DataAuthFilter:
 * ---------------------
 * Este filtro intercepta las solicitudes HTTP después del FORM-LOGIN de Tomcat y realiza dos acciones principales:
 *   1) Autenticación remota mediante CentroClient utilizando apiKey y sessionCookie.
 *   2) Almacena apiKey, sessionCookie y dni (usuario) en la HttpSession para su uso en posteriores peticiones.
 */
public class DataAuthFilter implements Filter {
  // Propiedades para cargar credenciales de usuarios desde WEB-INF/credentials.properties
  private Properties creds = new Properties();

  @Override
  public void init(FilterConfig cfg) throws ServletException {
    ServletContext ctx = cfg.getServletContext();
    try (InputStream in = ctx.getResourceAsStream("/WEB-INF/credentials.properties")) {
      creds.load(in);
    } catch (IOException e) {
      // Si no se puede cargar el archivo de credenciales, lanzamos una excepción para detener la inicialización
      throw new ServletException("No pude cargar credentials.properties", e);
    }
  }

  @Override
  public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest  req     = (HttpServletRequest) rq;
    HttpServletResponse res     = (HttpServletResponse) rs;
    HttpSession         session = req.getSession(true);

    // Verificamos si aún no tenemos apiKey en sesión pero el usuario ya está autenticado en Tomcat (req.getRemoteUser() != null)
    if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
      String user = req.getRemoteUser();
      String pass = creds.getProperty(user);
      
      // Validamos que el nombre de usuario y la contraseña estén presentes y no contengan ataques (inyección, etc.)
      if (!InputValidator.isValidRequired(pass) || !InputValidator.isValidRequired(user)) {
        // Si la validación falla, devolvemos un error 401 (Unauthorized)
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Sin credencial REST para " + user);
        return;
      }
      
      // Realizamos la autenticación remota contra el servicio CentroClient
      AuthResult auth = CentroClient.instance().login(user, pass);
      
      // Almacenamos en sesión los datos obtenidos: apiKey, sessionCookie y dni (usuario)
      session.setAttribute("apiKey",        auth.getApiKey());
      session.setAttribute("sessionCookie", auth.getSessionCookie());
      session.setAttribute("dni",           user);
    }

    // Continuamos con la cadena de filtros o la servlet objetivo
    chain.doFilter(rq, rs);
  }

  @Override
  public void destroy() {
  }
}
