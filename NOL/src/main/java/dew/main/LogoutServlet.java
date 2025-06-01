package dew.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dew.service.CentroClient;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
      

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
      // 1) Recuperar y anular la sesión
      HttpSession session = req.getSession(false);
      if (session != null) {
        session.invalidate();
        CentroClient.clearCookies();
      }
      Cookie cookie = new Cookie("JSESSIONID", "");
      cookie.setMaxAge(0);
      cookie.setPath(req.getContextPath());
      resp.addCookie(cookie);

      // 3) Redirigir al login o página pública
      resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
