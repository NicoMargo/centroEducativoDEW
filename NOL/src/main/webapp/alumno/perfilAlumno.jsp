<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />
<link rel="stylesheet" href="/css/perfilAlumno.css">

<div class="container">
	<header class="text-center mb-3">
		<h1 class="fw-bold">NOTAS ONLINE</h1>
		<h2 class="fw-bold">PERFIL DE ALUMNO</h2>
	</header>

	<div class="separator"></div>

	<main class="text-center pb-5">
		<h2 class="fw-bold mb-4">DATOS PERSONALES</h2>

		<div class="perfil-card border">
			<div class="card-body">
				<div class="row g-4">
					<div class="col-md-4 text-center mb-3 mb-md-0">
						<div class="avatar-container mx-auto">
							<i class="fas fa-user-graduate avatar-icon"></i>
						</div>
					</div>
					<div class="col-md-8">
						<div class="mb-3 datos-personales">
							<h3 class="fs-4 mb-3">Información del Alumno</h3>
							<div class="info-item">
								<span class="info-label">Nombre:</span> <span id="nombre"
									class="info-value">...</span>
							</div>
							<div class="info-item">
								<span class="info-label">Apellidos:</span> <span id="apellidos"
									class="info-value">...</span>
							</div>
							<div class="info-item">
								<span class="info-label">DNI:</span> <span id="dni"
									class="info-value">12345678A</span>
							</div>
						</div>
					</div>
				</div>

				<div class="asignaturas-container mt-4">
					<h3 class="fs-4 mb-3">Asignaturas matriculadas</h3>
					<div class="table-responsive">
						<table class="table table-hover align-middle">
							<thead>
								<tr>
									<th>Asignatura</th>
									<th>Profesor</th>
									<th class="text-center">Acción</th>
								</tr>
							</thead>
							<tbody id="asignaturasBody">
								<tr>
									<td colspan="3" class="text-center">Cargando
										asignaturas...</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</main>
</div>

<jsp:include page="../includes/footer.html" />
<script src="${pageContext.request.contextPath}/js/perfilAlumno.js"></script>
