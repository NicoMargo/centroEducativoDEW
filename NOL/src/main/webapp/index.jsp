<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.jsp" />

<div class="container">
    <div class="text-center mb-3" role="banner">
        <h2 class="fw-bold">BIENVENID@ A NOTAS ONLINE</h2>
        <h2 class="fw-bold">SISTEMA DE GESTION ACADEMICA</h2>
      </div>
  <main class="text-center pb-5">
    <div class="welcome-card border">
      <div class="card-body">
        <p class="lead mb-4">Esta plataforma permite a estudiantes y profesores gestionar de forma eficiente el seguimiento academico de las asignaturas.</p>

        <div class="mt-4">
          <h4 class="fs-5 mb-3 fw-bold"><i class="fas fa-star me-2"></i>Caracteristicas principales:</h4>
          <ul class="list-group list-group-flush text-start mb-4">
            <li class="list-group-item"><i class="fas fa-graduation-cap me-2 text-dark"></i> Consulta de calificaciones en tiempo real</li>
            <li class="list-group-item"><i class="fas fa-book me-2 text-dark"></i> Gestion de asignaturas y cursos</li>
            <li class="list-group-item"><i class="fas fa-chart-line me-2 text-dark"></i> Seguimiento del rendimiento academico</li>
            <li class="list-group-item"><i class="fas fa-comments me-2 text-dark"></i> Comunicacion directa con profesores</li>
          </ul>
        </div>

        <div class="mt-5">
          <a href="http://localhost:8080/NOL/alumno" class="btn btn-secondary btn-lg">
            <i class="fas fa-sign-in-alt me-2"></i> Ir a mi perfil Alumno
          </a>
        </div>
        <div class="mt-5">
          <a href="http://localhost:8080/NOL/profesor" class="btn btn-secondary btn-lg">
            <i class="fas fa-sign-in-alt me-2"></i> Ir a mi perfil Profesor 
          </a>
        </div>
      </div>
    </div>
      </main>
</div>
 
  
<jsp:include page="/includes/footer.html" />
