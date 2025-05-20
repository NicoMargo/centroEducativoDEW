<%@ page contentType="text/html" pageEncoding="utf-8" %>
<jsp:include page="/includes/header.html" />
<link rel="stylesheet" href="./css/login.css">

<h2>Autenticación</h2>

<!-- 
    tirar error si falla login

  <c:if test="${param.error == 'true'}">
    <p style="color:red;">Credenciales inválidas, inténtalo de nuevo.</p>
  </c:if>

-->

<div class="container">
  <main class="text-center pb-5">
    <div class="login-card border">
      <div class="card-body">
        <!-- Tag visual para indicar el tipo de usuario -->
        <div class="mb-4">
          <span class="badge rounded-pill bg-secondary fs-5 px-4 py-2">
            <i class="fas fa-user me-2"></i>ALUMNO / PROFESOR
          </span>
        </div>

        <!-- Formulario de login -->
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

<script src="./js/login.js"></script>
<jsp:include page="/includes/footer.html" />
