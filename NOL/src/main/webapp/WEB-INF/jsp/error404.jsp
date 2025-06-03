<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error404.css">
<jsp:include page="/includes/header.jsp" />

<div class="container">

  <main class="text-center pb-5">
    <div class="error-card border">
      <div class="card-body">
        <div class="error-icon">
          <i class="fas fa-search text-danger"></i>
        </div>
        <h1 class="fw-bold mb-3">Error 404</h1>
        <h2 class="fs-4 mb-4">Página No Encontrada</h2>

        <div class="error-message p-4 bg-light rounded mb-4 "
             style="border-left: 5px solid red;">
          <p class="mb-0 ">Lo sentimos, no podemos encontrar la página que estás buscando. Este error ocurre cuando intentas acceder a una URL que no existe o que ha sido movida a otra ubicación.</p>
        </div>

        <div class="possible-causes p-4 bg-light rounded mb-4"
        	 style="border-left: 5px solid red;">
          <h3 class="fs-5 mb-3"><i class="fas fa-exclamation-triangle me-2"></i>Posibles causas:</h3>
          <ul class="text-start">
            <li>La URL fue escrita incorrectamente</li>
            <li>La página ha sido eliminada o movida</li>
            <li>El enlace que seguiste está desactualizado</li>
            <li>Hay un error en la dirección que intentas acceder</li>
          </ul>
        </div>

        <div class="next-steps p-4 bg-light rounded mb-4"
        	 sstyle="border-left: 5px solid red;">
          <h3 class="fs-5 mb-3"><i class="fas fa-forward me-2"></i>¿Qué puedo hacer?</h3>
          <ul class="text-start">
            <li>Verifica que la URL esté escrita correctamente</li>
            <li>Regresa a la página anterior e intenta otro enlace</li>
            <li>Utiliza la barra de navegación para ir a una sección existente</li>
            <li>Ve a la página de inicio y comienza de nuevo</li>
          </ul>
        </div>

        <div class="mt-4 d-flex justify-content-center">
          <button onclick="history.back()" class="btn btn-secondary me-3">
            <i class="fas fa-arrow-left me-2"></i>Volver Atrás
          </button>
          <a href="./" class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Ir al Inicio
          </a>
        </div>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.jsp" />