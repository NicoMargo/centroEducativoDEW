<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />
<link rel="stylesheet" href="./css/error401.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container">
  <div class="separator"></div>

  <main class="text-center pb-5">
    <div class="error-card border">
      <div class="card-body">
        <div class="error-icon">
          <i class="fas fa-lock"></i>
        </div>
        <h1 class="fw-bold mb-3">Error 401</h1>
        <h2 class="fs-4 mb-4">Acceso No Autorizado</h2>

        <div class="error-message p-4 bg-light rounded mb-4">
          <p class="mb-0">No tienes los permisos necesarios para acceder a esta página. Este error ocurre cuando intentas acceder a un recurso que requiere autenticación o credenciales que no has proporcionado o que son inválidas.</p>
        </div>

        <div class="possible-causes p-4 bg-light rounded mb-4">
          <h3 class="fs-5 mb-3"><i class="fas fa-exclamation-triangle me-2"></i>Posibles causas:</h3>
          <ul class="text-start">
            <li>No has iniciado sesión</li>
            <li>Tu sesión ha expirado</li>
            <li>No tienes los privilegios necesarios para acceder al recurso</li>
            <li>Las credenciales proporcionadas son incorrectas</li>
          </ul>
        </div>

        <div class="next-steps p-4 bg-light rounded mb-4">
          <h3 class="fs-5 mb-3"><i class="fas fa-forward me-2"></i>¿Qué puedo hacer?</h3>
          <ul class="text-start">
            <li>Inicia sesión si aún no lo has hecho</li>
            <li>Contacta con el administrador si crees que deberías tener acceso</li>
            <li>Verifica que tus credenciales sean correctas</li>
            <li>Intenta volver a iniciar sesión si tu sesión ha expirado</li>
          </ul>
        </div>

        <div class="mt-4 d-flex justify-content-center">
          <a href="/login.html" class="btn btn-secondary me-3">
            <i class="fas fa-sign-in-alt me-2"></i>Iniciar Sesión
          </a>
          <a href="/" class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Volver al Inicio
          </a>
        </div>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.html" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script> 