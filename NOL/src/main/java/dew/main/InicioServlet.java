package dew.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/inicio")
public class InicioServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (req.isUserInRole("rolalu")) {
      resp.sendRedirect(req.getContextPath() + "/alumno");
    } else if (req.isUserInRole("rolpro")) {
      resp.sendRedirect(req.getContextPath() + "/profesor");
    } else {
      resp.sendRedirect(req.getContextPath() + "/login.jsp?error=true");
    }
  }
}
