<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Información de Asignatura</title>
  <jsp:include page="/includes/header.html" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/infoAsignatura.css">
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
  <div class="container">
    <main class="text-center pb-5">
      <h2 class="fw-bold mb-4">INFORMACIÓN DE ASIGNATURA</h2>

      <div class="asignatura-card border">
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 id="nombreAsignatura" class="fs-4 mb-0">
              ${asignatura.nombre}
            </h3>
            <span id="codigoAsignatura" class="badge bg-secondary">
              ${asignatura.acronimo}
            </span>
          </div>

          <div class="mb-4">
            <!-- Si tuvieses un campo 'profesor' en tu modelo lo mostrarías con EL aquí,
                 por ahora lo dejamos estático o eliminamos este bloque -->
            <div class="profesor-info p-3 bg-light rounded mb-3">
              <h4 class="fs-5">
                <i class="fas fa-chalkboard-teacher me-2"></i>Profesor:
              </h4>
              <p id="nombreProfesor" class="mb-0">
                Nombre del Profesor
              </p>
            </div>

            <div class="info-general p-3 bg-light rounded">
              <div class="row">
                <div class="col-12 text-start">
                  <h4 class="fs-5 mb-2">
                    <i class="fas fa-info-circle me-2"></i>Detalles:
                  </h4>
                  <ul class="list-unstyled">
                    <li><strong>Curso:</strong> ${asignatura.curso}</li>
                    <li><strong>Cuatrimestre:</strong> ${asignatura.cuatrimestre}</li>
                    <li><strong>Créditos:</strong> ${asignatura.creditos}</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

          <div class="mt-4 d-flex justify-content-between">
            <button id="btnVolver" class="btn btn-outline-secondary"
                    onclick="history.back()">
              <i class="fas fa-arrow-left me-2"></i>Volver
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>

  <jsp:include page="/includes/footer.html"/>
  <script src="${pageContext.request.contextPath}/js/infoAsignatura.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
