<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Login - Notas Online</title>

  <!-- Bootstrap 5.3.3 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
  <!-- Custom CSS -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<!-- <jsp:include page="/includes/header.html" /> REVISAR --> 
<body>
  <div class="container">
    <header class="text-center mb-3">
      <h1 class="fw-bold">BIENVENID@ A NOTAS ONLINE</h1>
      <h2 class="fw-bold">*AQÍ VA UNA DESCRIPCIÓN*</h2>
    </header>

    <div class="separator"></div>

    <main class="text-center pb-5">
      <h2 class="fw-bold mb-4" id="mainHeading">CONSULTA TUS EXÁMENES</h2>

      <div class="login-card border">
        <div class="card-body">

          <div class="d-flex justify-content-center mb-4 role-toggle">
            <button id="btnAlumno" class="btn btn-outline-dark active me-2" type="button">
              <i class="fas fa-user-graduate me-2"></i>ALUMNO
            </button>
            <button id="btnProfesor" class="btn btn-outline-dark" type="button">
              <i class="fas fa-chalkboard-teacher me-2"></i>PROFESOR
            </button>
          </div>

          <%-- Mensaje error login si existe --%>
          <c:if test="${param.error == 'true'}">
            <div class="alert alert-danger" role="alert">
              Credenciales inválidas, inténtalo de nuevo.
            </div>
          </c:if>

          <!-- Form alumno -->
          <form action="j_security_check" method="POST" id="formAlumno" style="display: block;">
            <div class="mb-3 text-start">
              <label for="usuario" class="form-label fw-semibold">
                <i class="fas fa-user me-2"></i>USUARIO:
              </label>
              <input type="text" id="usuario" name="j_username" class="form-control" placeholder="Ingresa tu usuario" required />
            </div>
            <div class="mb-4 text-start">
              <label for="clave" class="form-label fw-semibold">
                <i class="fas fa-lock me-2"></i>CLAVE:
              </label>
              <div class="input-group">
                <input type="password" id="clave" name="j_password" class="form-control" placeholder="Ingresa tu clave" required />
                <button class="btn btn-outline-secondary" type="button" id="togglePasswordAlumno">
                  <i class="fas fa-eye" id="eyeIconAlumno"></i>
                </button>
              </div>
            </div>
            <button type="submit" class="btn btn-secondary w-100">
              <i class="fas fa-sign-in-alt me-2"></i>INICIAR SESIÓN
            </button>
          </form>

          <!-- Form profesor -->
          <form action="${pageContext.request.contextPath}/j_security_check" method="POST" id="formAlumno" style="display: block;">
            <div class="mb-3 text-start">
              <label for="usuarioProfe" class="form-label fw-semibold">
                <i class="fas fa-user me-2"></i>USUARIO:
              </label>
              <input type="text" id="usuarioProfe" name="j_username" class="form-control" placeholder="Ingresa tu usuario" required />
            </div>
            <div class="mb-4 text-start">
              <label for="claveProfe" class="form-label fw-semibold">
                <i class="fas fa-lock me-2"></i>CLAVE:
              </label>
              <div class="input-group">
                <input type="password" id="claveProfe" name="j_password" class="form-control" placeholder="Ingresa tu clave" required />
                <button class="btn btn-outline-secondary" type="button" id="togglePasswordProfesor">
                  <i class="fas fa-eye" id="eyeIconProfesor"></i>
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

  <!-- Bootstrap JS Bundle -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Custom JS -->
 <script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
<jsp:include page="/includes/footer.html" />
</html>
