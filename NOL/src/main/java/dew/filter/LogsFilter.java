package dew.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogsFilter implements Filter {
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private Path logFile;
    private boolean loggingEnabled;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String filePath = filterConfig.getInitParameter("logFilePath");
        loggingEnabled = Boolean.parseBoolean(
            filterConfig.getInitParameter("loggingEnabled")
        );

        // 2) Resolución siempre relativa al contexto web
        //    (getRealPath transforma "/WEB-INF/logs/app.log" en, p.ej.,
        //     "/home/tomcat/webapps/miApp/WEB-INF/logs/app.log")
        ServletContext ctx = filterConfig.getServletContext();
        String realPath = ctx.getRealPath(filePath);
        	
        try {
            logFile = Paths.get(realPath);
            Path parent = logFile.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            }
            ctx.setAttribute("logFilePathResolved", realPath);
        } catch (IOException e) {
            throw new ServletException(
              "No se pudo inicializar el fichero de logs en " + realPath, e
            );
        }
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        if (loggingEnabled && request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;

            String timestamp = LocalDateTime.now().format(FORMATTER);
            String user      = req.getRemoteUser() != null ? req.getRemoteUser() : "-";
            String ip        = req.getRemoteAddr();

            // Construir URI completa con query string, si la hay
            String uri = req.getRequestURI();
            String qs  = req.getQueryString();
            if (qs != null && !qs.isEmpty()) {
                uri += "?" + qs;
            }

            String method = req.getMethod();

            String entry = String.format(
                "%s %s %s %s %s%n",
                timestamp, user, ip, uri, method
            );

            // 3) Añadir la línea al final del fichero
            Files.write(
                logFile,
                entry.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND
            );
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
