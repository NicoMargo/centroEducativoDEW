<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.models.Alumno, dew.models.Asignatura" %>
<jsp:include page="/includes/header.jsp"/>

  <title>Ficha del Alumno</title>
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
            <div class="photo-container">
                 <img src="./img/${alumno.dni}.webp" alt="Foto del alumno" id="fotoAlumno" class="foto-alumno" />
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

  <jsp:include page="/includes/footer.jsp"/>