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
 * Intercepta tras FORM-LOGIN de Tomcat y hace:
 * 1) data-auth con CentroClient → apiKey + sessionCookie  
 * 2) almacena apiKey, sessionCookie y dni en HttpSession  
 */
public class DataAuthFilter implements Filter {
  private Properties creds = new Properties();

  @Override
  public void init(FilterConfig cfg) throws ServletException {
    ServletContext ctx = cfg.getServletContext();
    try (InputStream in = ctx.getResourceAsStream("/WEB-INF/credentials.properties")) {
      creds.load(in);
    } catch (IOException e) {
      throw new ServletException("No pude cargar credentials.properties", e);
    }
  }

  @Override
  public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest  req = (HttpServletRequest) rq;
    HttpServletResponse res = (HttpServletResponse) rs;
    HttpSession session = req.getSession(true);

    if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
      String user = req.getRemoteUser();
      String pass = creds.getProperty(user);
      
      //Valido la entrada que sea valida y no contenga ataques
      if (!InputValidator.isValidRequired(pass) || !InputValidator.isValidRequired(user) ) {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Sin credencial REST para " + user);
        return;
      }
      
      AuthResult auth = CentroClient.instance().login(user, pass);
      session.setAttribute("apiKey",        auth.getApiKey());
      session.setAttribute("sessionCookie", auth.getSessionCookie());
      session.setAttribute("dni",           user);
    }
    chain.doFilter(rq, rs);
  }

  @Override
  public void destroy() {}
}
