<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.models.Profesor, dew.models.Asignatura" %>
<jsp:include page="/includes/header.jsp"/>

<title>Ficha del Profesor</title>
<body>
  <div class="container">
    <h2 class="fw-bold text-center mx-auto">FICHA DEL PROFESOR</h2>
    <main class="pb-5">
      <div class="perfil-card border">
        <div class="card-body">
          <section class="datos-foto-container">
            <div class="datos-personales">
              <% 
                Profesor profesor = (Profesor) request.getAttribute("profesor");
              %>
              <p><i class="fas fa-user me-2"></i>
                 <strong>Nombre:</strong> <%= profesor.getNombre() %></p>
              <p><i class="fas fa-user-tag me-2"></i>
                 <strong>Apellidos:</strong> <%= profesor.getApellidos() %></p>
              <p><i class="fas fa-id-card me-2"></i>
                 <strong>DNI:</strong> <%= profesor.getDni() %></p>
            </div>
            <div class="photo-container">
              <img src="./img/<%= profesor.getDni() %>.webp" alt="Foto del profesor" id="fotoProfesor" class="foto-alumno" />
            </div>
          </section>

          <section class="mt-4">
            <h3 class="fw-semibold mb-3">
              <i class="fas fa-book me-2"></i>Asignaturas Impartidas
            </h3>
            <div class="table-responsive">
              <table class="table table-hover align-middle">
                <thead>
                  <tr>
                    <th>Asignatura</th>
                    <th class="text-center">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <%
                    List<Asignatura> lista = profesor.getAsignaturas();
                    if (lista != null && !lista.isEmpty()) {
                      for (Asignatura asig : lista) {
                  %>
                  <tr>
                    <td><%= asig.getAcronimo() %></td>
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
                    <td colspan="2" class="text-center">No hay asignaturas asignadas</td>
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