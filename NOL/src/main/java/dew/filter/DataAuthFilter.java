package dew.filter;

import dew.service.AuthResult;
import dew.service.CentroClient;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Filter que:
 *  1) Espera a que Tomcat autentique web (getRemoteUser())
 *  2) Usa credentials.properties para password
 *  3) Llama a CentroClient.login() → apiKey + sessionCookie
 *  4) Guarda en HttpSession solo apiKey y sessionCookie
 */
public class DataAuthFilter implements Filter {
    private String baseUrl;
    private Properties creds;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        baseUrl = cfg.getServletContext().getInitParameter("centro.baseUrl");
        creds   = new Properties();
        try (InputStream in = cfg.getServletContext()
               .getResourceAsStream("/WEB-INF/credentials.properties")) {
            if (in == null) throw new ServletException("Missing credentials.properties");
            creds.load(in);
        } catch (IOException e) {
            throw new ServletException("Error loading credentials", e);
        }
    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest)  rq;
        HttpServletResponse res  = (HttpServletResponse) rs;
        HttpSession session = req.getSession(true);

        // Si no hay apiKey/cacheCookie en sesión y el usuario web está logeado:
        if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
            String user = req.getRemoteUser();
            String pass = creds.getProperty(user);
            if (pass == null) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "No REST credential for user " + user);
                return;
            }

            // 1) Login REST
            CentroClient client = CentroClient.getInstance(baseUrl);
            AuthResult auth;
            try {
                auth = client.login(user, pass);
            } catch (IOException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Data auth failed: " + e.getMessage());
                return;
            }

            // 2) Guardar apiKey y sessionCookie en HttpSession
            session.setAttribute("apiKey",       auth.getApiKey());
            session.setAttribute("sessionCookie", auth.getSessionCookie());
            session.setAttribute("dni", user);
        }

        chain.doFilter(rq, rs);
    }

    @Override
    public void destroy() {}
}
