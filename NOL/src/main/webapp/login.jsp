<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.jsp" />

 <h2>Autenticación</h2>
 
  <!-- 
    tirar error si falla login

  <c:if test="${param.error == 'true'}">
    <p style="color:red;">Credenciales inválidas, inténtalo de nuevo.</p>
  </c:if>

-->
    
<div class="container">
  <main class="text-center pb-5">
    <!-- Título eliminado según indicación -->

    <div class="login-card border">
      <div class="card-body">
        <div class="d-flex justify-content-center mb-4 role-toggle">
          <button id="btnAlumno" class="btn btn-outline-dark active me-2">
            <i class="fas fa-user-graduate me-2"></i>Alumno /
            <i class="fas fa-chalkboard-teacher ms-2"></i> Profesor
          </button>
        </div>
        <form id="loginForm" action="j_security_check" method="POST">
          <div class="mb-3 text-start">
            <label for="usuario" class="form-label fw-semibold">
              <i class="fas fa-user me-2"></i>USUARIO:
            </label>
            <input type="text" name="j_username" id="usuario" class="form-control" placeholder="Ingresa tu usuario" required>
          </div>
          <div class="mb-4 text-start">
            <label for="clave" class="form-label fw-semibold">
              <i class="fas fa-lock me-2"></i>CLAVE:
            </label>
            <div class="input-group">
              <input type="password" id="clave" name="j_password" class="form-control" placeholder="Ingresa tu clave" required>
              <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                <i class="fas fa-eye" id="eyeIcon"></i>
              </button>
            </div>
          </div>
          <button type="submit" class="btn btn-secondary w-100">
            <i class="fas fa-sign-in-alt me-2"></i>INICIAR SESIÓN
          </button>
        </form>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.html" />

