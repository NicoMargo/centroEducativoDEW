<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />

<%-- Detectar el rol desde la URL --%>
<%
  String rol = request.getParameter("rol");
  if (rol == null) {
    rol = "alumno"; // Por defecto
  }
  String rolMayus = rol.toUpperCase();
  String icono = rol.equals("profesor") ? "fa-chalkboard-teacher" : "fa-user-graduate";
%>

<link rel="stylesheet" href="./css/login.css">

<h2 class="text-center fw-bold mt-4">AUTENTICACIÓN</h2>

<div class="container">
  <main class="text-center pb-5">

    <div class="login-card border mt-4">
      <div class="card-body">

        <div class="d-flex justify-content-center mb-4 role-toggle">
          <button class="btn btn-outline-dark active me-2" disabled>
            <i class="fas <%= icono %> me-2"></i>
            <%= rolMayus %>
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

<script src="./js/login.js"></script>
<jsp:include page="/includes/footer.html" />
