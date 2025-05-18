package dew.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

public class DebugFilter implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    System.out.println(">> DEBUG: " + r.getMethod() + " " + r.getRequestURI());
    chain.doFilter(req, res);
  }
}