// src/main/java/dew/filters/LogsFilter.java
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

        // Si la ruta no es absoluta, la resolvemos contra el contexto web
        if (!Paths.get(filePath).isAbsolute()) {
            String real = filterConfig
                .getServletContext()
                .getRealPath(filePath);
            filePath = real;
        }

        try {
            logFile = Paths.get(filePath);
            Path parent = logFile.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            }
        } catch (IOException e) {
            throw new ServletException("No se pudo inicializar el fichero de logs", e);
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

            // Construimos la URL completa con query string
            String uri       = req.getRequestURI();
            String qs        = req.getQueryString();
            if (qs != null && !qs.isEmpty()) {
                uri += "?" + qs;
            }

            String method    = req.getMethod();

            String entry = String.format("%s %s %s %s %s%n",
                    timestamp, user, ip, uri, method);

            Files.write(logFile,
                        entry.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}
