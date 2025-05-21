<jsp:include page="/includes/header.jsp" />
<link rel="stylesheet" href="./css/error401.css">

<div class="container">

  <main class="text-center pb-5">
    <div class="error-card border">
      <div class="card-body">
        <div class="error-icon">
          <i class="fas fa-lock "></i>
        </div>
        <h1 class="fw-bold mb-3">Error 401</h1>
        <h2 class="fs-4 mb-4">Acceso No Autorizado</h2>

        <div class="error-message p-4 bg-light rounded mb-4"
        	 style="border-left: 5px solid blue;">
          <p class="mb-0">No tienes los permisos necesarios para acceder a esta p�gina. Este error ocurre cuando intentas acceder a un recurso que requiere autenticaci�n o credenciales que no has proporcionado o que son inv�lidas.</p>
        </div>

        <div class="possible-causes p-4 bg-light rounded mb-4"
			 style="border-left: 5px solid blue;">
          <h3 class="fs-5 mb-3"><i class="fas fa-exclamation-triangle me-2"></i>Posibles causas:</h3>
          <ul class="text-start">
            <li>No has iniciado sesi�n</li>
            <li>Tu sesi�n ha expirado</li>
            <li>No tienes los privilegios necesarios para acceder al recurso</li>
            <li>Las credenciales proporcionadas son incorrectas</li>
          </ul>
        </div>

        <div class="next-steps p-4 bg-light rounded mb-4"
             style="border-left: 5px solid blue;">
          <h3 class="fs-5 mb-3"><i class="fas fa-forward me-2"></i>�Qu� puedo hacer?</h3>
          <ul class="text-start">
            <li>Inicia sesi�n si a�n no lo has hecho</li>
            <li>Contacta con el administrador si crees que deber�as tener acceso</li>
            <li>Verifica que tus credenciales sean correctas</li>
            <li>Intenta volver a iniciar sesi�n si tu sesi�n ha expirado</li>
          </ul>
        </div>

        <div class="mt-4 d-flex justify-content-center">
          <a href="./" class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Volver al Inicio
          </a>
        </div>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.html" />