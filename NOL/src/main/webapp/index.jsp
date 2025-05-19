<%@page contentType="text/html" pageEncoding="utf-8"%>
<link rel="stylesheet" href="../css/index.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<div class="container">
  <header class="text-center mb-3">
    <h2 class="fw-bold">BIENVENID@ A NOTAS ONLINE</h1>
    <h2 class="fw-bold">SISTEMA DE GESTIÓN ACADÉMICA</h2>
  </header>
  <div class="separator"></div>
  <main class="text-center pb-5">
    <div class="welcome-card border">
      <div class="card-body">
        <p class="lead mb-4">Esta plataforma permite a estudiantes y profesores gestionar de forma eficiente el seguimiento académico de las asignaturas.</p>
        
        <div class="mt-4">
          <h4 class="fs-5 mb-3 fw-bold"><i class="fas fa-star me-2"></i>Características principales:</h4>
          <ul class="list-group list-group-flush text-start mb-4">
            <li class="list-group-item"><i class="fas fa-graduation-cap me-2 text-dark"></i> Consulta de calificaciones en tiempo real</li>
            <li class="list-group-item"><i class="fas fa-book me-2 text-dark"></i> Gestión de asignaturas y cursos</li>
            <li class="list-group-item"><i class="fas fa-chart-line me-2 text-dark"></i> Seguimiento del rendimiento académico</li>
            <li class="list-group-item"><i class="fas fa-comments me-2 text-dark"></i> Comunicación directa con profesores</li>
          </ul>
        </div>
        
        <div class="mt-5">
          <a href="#" class="btn btn-secondary btn-lg">
            <i class="fas fa-sign-in-alt me-2"></i> INICIAR SESIÓN
          </a>
        </div>
      </div>
    </div>
  </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/includes/footer.html" />