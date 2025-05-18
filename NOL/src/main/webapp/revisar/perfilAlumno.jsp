<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Ficha Alumno - Notas Online</title>

  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
  <!-- Font Awesome -->
  <link href="${pageContext.request.contextPath}/css/fontawesome.min.css" rel="stylesheet" />
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/perfil.css" rel="stylesheet" />
</head>
<body>
  <div class="container">
    <header class="text-center mb-3 d-flex justify-content-center">
      <h1 class="fw-bold text-center mx-auto">FICHA DEL ALUMNO</h1>
    </header>

    <div class="separator"></div>

    <main class="pb-5">
      <div class="perfil-card border">
        <div class="card-body">
          <section class="datos-foto-container">
            <div class="datos-personales" id="datosPersonales">
              <p><i class="fas fa-user me-2"></i><strong>Nombre:</strong> 
                <span id="nombreAlumno">${usuarioAlumno.nombreCompleto}</span>
              </p>
              <p><i class="fas fa-id-card me-2"></i><strong>D.N.I./PAS.:</strong> 
                <span id="dni">${usuarioAlumno.dni}</span>
              </p>
              <p><i class="fas fa-birthday-cake me-2"></i><strong>Nacimiento:</strong> 
                <span id="fechaNacimiento">${usuarioAlumno.fechaNacimiento} (${usuarioAlumno.paisNacimiento})</span>
              </p>
              <p><i class="fas fa-school me-2"></i><strong>Escuela:</strong> 
                <span id="escuela">${usuarioAlumno.escuela}</span>
              </p>
            </div>
            <img src="${usuarioAlumno.foto}" alt="Foto de ${usuarioAlumno.nombreCompleto}" id="fotoAlumno" class="foto-alumno" />
          </section>

          <section class="mt-4">
            <h3 class="fw-semibold mb-3"><i class="fas fa-book me-2"></i>Asignaturas Matriculadas</h3>
            <div class="table-responsive">
              <table class="table table-hover align-middle">
                <thead>
                  <tr>
                    <th>Asignatura</th>
                    <th>Profesor</th>
                    <th class="text-center">Acciones</th>
                  </tr>
                </thead>
                <tbody id="asignaturasBody">
                  <c:forEach var="asignatura" items="${usuarioAlumno.asignaturas}">
                    <tr>
                      <td>${asignatura.nombre}</td>
                      <td>${asignatura.profesor}</td>
                      <td class="text-center">
                        <button class="btn btn-sm btn-secondary consultar-btn" data-asignatura="${asignatura.nombre}">
                          <i class="fas fa-search me-1"></i>Consultar
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </section>
        </div>
      </div>
    </main>
  </div>

  <!-- Bootstrap JS Bundle -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/perfil.js"></script>
</body>
</html>
