package dew.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LogsFilter intercepta cada petición HTTP entrante y escribe una línea en un fichero de logs,
 * incluyendo la marca temporal, usuario, IP, URI solicitada y método HTTP.
 */
public class LogsFilter implements Filter {
    // Formato de fecha/hora ISO con milisegundos, por ejemplo: 2025-06-05T14:23:45.123
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    // Ruta al fichero de logs en disco, resuelta en init()
    private Path logFile;

    // Controla si la escritura de logs está habilitada (configurable en web.xml)
    private boolean loggingEnabled;

    /**
     * Inicializa el filtro leyendo los parámetros de configuración:
     * - logFilePath: ruta relativa al contexto donde se escribirá el fichero de logs.
     * - loggingEnabled: si está activado o no el registro.
     * Crea las carpetas necesarias y el fichero de logs si no existen.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String filePath = filterConfig.getInitParameter("logFilePath");
        loggingEnabled = Boolean.parseBoolean(
            filterConfig.getInitParameter("loggingEnabled")
        );

        // 2) Obtener ServletContext para convertir la ruta relativa en una ruta física real
        ServletContext ctx = filterConfig.getServletContext();

        String realPath = ctx.getRealPath(filePath);

        try {
            // 3) Convertir la cadena realPath en un objeto Path
            logFile = Paths.get(realPath);

            // 4) Asegurarse de que el directorio padre exista; si no, crearlo
            Path parent = logFile.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            // 5) Si el fichero de logs no existe, crearlo vacío
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            }

            // 6) Almacenar la ruta física resuelta en el contexto de la aplicación para uso posterior (por ejemplo, en el footer)
            ctx.setAttribute("logFilePathResolved", realPath);
        } catch (IOException e) {
            // Si ocurre cualquier error de E/S al crear rutas o ficheros, abortamos la inicialización
            throw new ServletException(
              "No se pudo inicializar el fichero de logs en " + realPath, e
            );
        }
    }

    /**
     * Intercepta cada petición entrante. Si el registro está habilitado,
     * construye una línea con la fecha, usuario, IP, URI (incluyendo query string)
     * y método HTTP, y la añade al fichero de logs.
     * Luego continúa con la cadena de filtros y servlets.
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        // Solo registramos si loggingEnabled es true y la petición es HTTP (no p. ej. un recurso estático no-HTTP)
        if (loggingEnabled && request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;

            // 1) Capturar la marca temporal actual con formato definido arriba
            String timestamp = LocalDateTime.now().format(FORMATTER);

            // 2) Obtener el nombre del usuario autenticado (o "-" si no hay ninguno)
            String user = req.getRemoteUser() != null ? req.getRemoteUser() : "-";

            // 3) Obtener la IP de origen de la petición
            String ip = req.getRemoteAddr();

            // 4) Construir la URI completa, incluyendo query string si existe
            String uri = req.getRequestURI();
            String qs  = req.getQueryString();
            if (qs != null && !qs.isEmpty()) {
                uri += "?" + qs;
            }

            // 5) Obtener el método HTTP (GET, POST, etc.)
            String method = req.getMethod();

            // 6) Formatear la línea de log (campos separados por espacios, con salto de línea al final)
            //    Ejemplo: "2025-06-05T14:23:45.123 usuario 192.168.1.10 /NOL/alumno GET"
            String entry = String.format(
                "%s %s %s %s %s%n",
                timestamp, user, ip, uri, method
            );

            // 7) Añadir esta línea al final del fichero de logs (modo APPEND)
            Files.write(
                logFile,
                entry.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND
            );
        }

        // Continuar con la cadena de filtros / servlet target
        chain.doFilter(request, response);
    }

    /**
     * Método llamado al destruir el filtro. Actualmente no realiza ninguna acción.
     */
    @Override
    public void destroy() {
        // No hay recursos especiales que liberar en este filtro
    }
}
