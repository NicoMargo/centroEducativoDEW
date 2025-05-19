<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.models.Alumno, dew.models.Asignatura" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ficha del Alumno</title>
  <jsp:include page="/includes/header.html"/>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/perfilAlumno.css"/>
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
</head>
<body>
  <div class="container">
    <h2 class="fw-bold text-center mx-auto">FICHA DEL ALUMNO</h2>
    <main class="pb-5">
      <div class="perfil-card border">
        <div class="card-body">
          <section class="datos-foto-container">
            <div class="datos-personales">
              <% 
                Alumno alumno = (Alumno) request.getAttribute("alumno");
              %>
              <p><i class="fas fa-user me-2"></i>
                 <strong>Nombre:</strong> ${alumno.nombre}</p>
              <p><i class="fas fa-user-tag me-2"></i>
                 <strong>Apellidos:</strong> ${alumno.apellidos}</p>
              <p><i class="fas fa-id-card me-2"></i>
                 <strong>DNI:</strong> ${alumno.dni}</p>
            </div>
          </section>

          <section class="mt-4">
            <h3 class="fw-semibold mb-3">
              <i class="fas fa-book me-2"></i>Asignaturas Matriculadas
            </h3>
            <div class="table-responsive">
              <table class="table table-hover align-middle">
                <thead>
                  <tr>
                    <th>Asignatura</th>
                    <th>Nota</th>
                    <th class="text-center">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <%
                    List<Asignatura> lista = alumno.getAsignaturas();
                    if (lista != null && !lista.isEmpty()) {
                      for (Asignatura asig : lista) {
                  %>
                  <tr>
                    <td><%= asig.getAcronimo() %></td>
                    <td>
                      <%
                        String nota = asig.getNota();
                        out.print((nota == null || nota.isEmpty()) ? "—" : nota);
                      %>
                    </td>
                    <td class="text-center">
                      <a class="btn btn-sm btn-primary"
                         href="<%= request.getContextPath() %>/asignatura?acronimo=<%= asig.getAcronimo() %>">
                        Ver
                      </a>
                    </td>
                  </tr>
                  <%
                      }
                    } else {
                  %>
                  <tr>
                    <td colspan="3" class="text-center">No hay asignaturas</td>
                  </tr>
                  <% } %>
                </tbody>
              </table>
            </div>
          </section>
        </div>
      </div>
    </main>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <jsp:include page="/includes/footer.html"/>
  <script src="${pageContext.request.contextPath}/js/perfilAlumno.js"></script>
</body>
</html>
