package dew.filter;

import dew.service.CentroClient;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebFilter("/*")
public class DataAuthFilter implements Filter {
    private String baseUrl;

    @Override
    public void init(FilterConfig cfg) {
        baseUrl = cfg.getServletContext().getInitParameter("centro.baseUrl");
        System.out.println("[DataAuthFilter] init with baseUrl=" + baseUrl);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        System.out.println("[DataAuthFilter] Incoming: " + request.getMethod() + " " + uri);

        // Permitir que FORM-LOGIN procese j_security_check
        if (uri.endsWith("j_security_check")) {
            System.out.println("[DataAuthFilter] skipping j_security_check");
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(true);
        if (session.getAttribute("apiKey") == null) {
            String user = request.getRemoteUser();
            System.out.println("[DataAuthFilter] remoteUser=" + user);
            if (user != null) {
                // obtener password del formulario
                String pwd = request.getParameter("j_password");
                System.out.println("[DataAuthFilter] obtained pwd for " + user);
                try {
                    CentroClient client = new CentroClient(baseUrl);
                    String apiKey = client.login(user, pwd);
                    session.setAttribute("apiKey", apiKey);
                    System.out.println("[DataAuthFilter] stored apiKey=" + apiKey);
                } catch (IOException e) {
                    System.err.println("[DataAuthFilter] data auth failed: " + e.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Data auth failed");
                    return;
                }
            }
        } else {
            System.out.println("[DataAuthFilter] apiKey in session: " + session.getAttribute("apiKey"));
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("[DataAuthFilter] destroy");
    }
}
