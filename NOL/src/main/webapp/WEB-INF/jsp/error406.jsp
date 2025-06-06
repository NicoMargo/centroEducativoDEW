<jsp:include page="/includes/header.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error401.css">

<div class="container">

  <main class="text-center pb-5">
    <div class="error-card border">
      <div class="card-body">
        <div class="error-icon">
          <i class="fas fa-lock "></i>
        </div>
        <h1 class="fw-bold mb-3">Error 406</h1>

        <div class="error-message p-4 bg-light rounded mb-4"
        	 style="border-left: 5px solid blue;">
        </div>

        <div class="possible-causes p-4 bg-light rounded mb-4"
			 style="border-left: 5px solid blue;">
          <h3 class="fs-5 mb-3"><i class="fas fa-exclamation-triangle me-2"></i>Posibles causas:</h3>
          
        </div>

        <div class="next-steps p-4 bg-light rounded mb-4"
             style="border-left: 5px solid blue;">
          <h3 class="fs-5 mb-3"><i class="fas fa-forward me-2"></i>¿Qué puedo hacer?</h3>
          
        </div>

        <div class="mt-4 d-flex justify-content-center">
          <a href="${pageContext.request.contextPath}" class="btn btn-outline-secondary">
            <i class="fas fa-home me-2"></i>Volver al Inicio
          </a>
        </div>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.jsp" />