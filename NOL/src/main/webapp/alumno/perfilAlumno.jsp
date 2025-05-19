<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />  
<link rel="stylesheet" href="../css/perfilAlumno.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />


  <div class="container">
    <h2 class="fw-bold text-center mx-auto">FICHA DEL ALUMNO</h2>
    <main class="pb-5">
      <div class="perfil-card border">
        <div class="card-body">
          <section class="datos-foto-container">
            <div class="datos-personales" id="datosPersonales">
              <p><i class="fas fa-user me-2"></i><strong>Nombre:</strong> <span id="nombre">Nombre</span></p>
              <p><i class="fas fa-user-tag me-2"></i><strong>Apellidos:</strong> <span id="apellidos">Apellidos</span></p>
              <p><i class="fas fa-id-card me-2"></i><strong>D.N.I.:</strong> <span id="dni">DNI</span></p>
              <p><i class="fas fa-info-circle me-2"></i><strong>Descripción:</strong> <span id="descripcion">Información adicional del alumno</span></p> 

            </div>
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
                <tbody id="asignaturasBody"></tbody>
              </table>
            </div>
          </section>
        </div>
      </div>
    </main>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <jsp:include page="/includes/footer.html" />
  <script src="../js/perfilAlumno.js"></script>
</html>

