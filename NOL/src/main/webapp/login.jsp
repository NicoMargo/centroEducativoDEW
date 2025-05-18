<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login - Notas Online</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="/css/login.css">
</head>
<body>
  <div class="container">
    <header class="text-center mb-3">
      <h1 class="fw-bold">BIENVENID@ A NOTAS ONLINE</h1>
        <h2 class="fw-bold">*AQÍ VA UNA DESCRIPCIÓN*</h2>
    </header>

    <div class="separator"></div>

    <main class="text-center pb-5">
      <h2 class="fw-bold mb-4">CONSULTA TUS EXÁMENES</h2>

      <div class="login-card border">
        <div class="card-body">
          <div class="d-flex justify-content-center mb-4 role-toggle">
            <button id="btnAlumno" class="btn btn-outline-dark active me-2">
              <i class="fas fa-user-graduate me-2"></i>ALUMNO
            </button>
            <button id="btnProfesor" class="btn btn-outline-dark">
              <i class="fas fa-chalkboard-teacher me-2"></i>PROFESOR
            </button>
          </div>

          <form id="loginForm">
            <div class="mb-3 text-start">
              <label for="usuario" class="form-label fw-semibold">
                <i class="fas fa-user me-2"></i>USUARIO:
              </label>
              <input type="text" id="usuario" class="form-control" placeholder="Ingresa tu usuario" required>
            </div>
            <div class="mb-4 text-start">
              <label for="clave" class="form-label fw-semibold">
                <i class="fas fa-lock me-2"></i>CLAVE:
              </label>
              <div class="input-group">
                <input type="password" id="clave" class="form-control" placeholder="Ingresa tu clave" required>
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

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="login.js"></script>
</body>
</html>