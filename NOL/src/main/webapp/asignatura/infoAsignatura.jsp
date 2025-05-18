<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />
<link rel="stylesheet" href="/css/infoAsignatura.css">

<div class="container">
	<header class="text-center mb-3">
		<h1 class="fw-bold">NOTAS ONLINE</h1>
		<h2 class="fw-bold">GESTIÓN DE ASIGNATURAS</h2>
	</header>

	<div class="separator"></div>

	<main class="text-center pb-5">
		<h2 class="fw-bold mb-4">INFORMACIÓN DE ASIGNATURA</h2>

		<div class="asignatura-card border">
			<div class="card-body">
				<div class="d-flex justify-content-between align-items-center mb-4">
					<h3 id="nombreAsignatura" class="fs-4 mb-0">Desarrollo Web</h3>
					<span id="codigoAsignatura" class="badge bg-secondary">DEW</span>
				</div>

				<div class="mb-4">
					<div class="profesor-info p-3 bg-light rounded mb-3">
						<h4 class="fs-5">
							<i class="fas fa-chalkboard-teacher me-2"></i>Profesor:
						</h4>
						<p id="nombreProfesor" class="mb-0">Nombre del Profesor</p>
					</div>

					<div class="info-general p-3 bg-light rounded">
						<div class="row">
							<div class="col-12 text-start">
								<h4 class="fs-5 mb-2">
									<i class="fas fa-info-circle me-2"></i>Descripción:
								</h4>
								<p class="mb-0 descripcion-asignatura">El desarrollo de
									aplicaciones para la web es un ámbito de trabajo muy importante
									para los titulados en informática. El dimensionamiento de la
									asignatura es claramente deficitario, cobrando gran importancia
									la ayuda que otras asignatura puedan aportar en esta temática.
									Dado que se trata de una asignatura de la rama de Tecnologías
									de la Información, hay una relación natural con las asignaturas
									hermanas aunque se cursen en el siguiente cuatrimestre,
									destacando 11613-Desarrollo centrado en el usuario,
									11606-Seguridad en redes y sistemas informáticos, y
									11608-Sistemas y servicios en red. Necesitan una mención
									especial las asignaturas previas que ejercitan habilidades de
									programación (11543-Programación, 11555-Ingeniería del
									software, 11563-Tecnologías de sistemas de información en la
									red) o que proporcionan conocimientos relacionados con las
									redes de comunicación (12990-Redes de computadores). En un
									último grupo se pueden considerar algunas asignaturas ofertas
									en el bloque optativo general de cuarto, que abordan aspectos
									relacionados como 11648-Diseño de sitios web, y 14099-Seguridad
									web</p>
							</div>
						</div>
					</div>
				</div>

				<div class="examenes-section">
					<h4 class="fs-5 mb-3">
						<i class="fas fa-file-alt me-2"></i>Exámenes y evaluaciones
					</h4>
					<div class="table-responsive">
						<table class="table table-hover align-middle rounded-table">
							<thead>
								<tr>
									<th>Evaluación</th>
									<th>Fecha</th>
									<th>Estado</th>
									<th>Nota</th>
									<th class="text-center">Acción</th>
								</tr>
							</thead>
							<tbody id="tablaExamenes">
								<tr data-exam-id="1">
									<td>Primer parcial</td>
									<td>15/10/2023</td>
									<td><span class="badge bg-success">Completado</span></td>
									<td class="nota-value">8.5</td>
									<td class="text-center">
										<button class="btn btn-sm btn-editar consultar-btn py-0 px-2"
											style="font-size: 0.8rem;" title="Editar nota">
											<i class="fas fa-pencil-alt"></i>
										</button>
									</td>
								</tr>
								<tr data-exam-id="2">
									<td>Segundo parcial</td>
									<td>10/12/2023</td>
									<td><span class="badge bg-success">Completado</span></td>
									<td class="nota-value">7.2</td>
									<td class="text-center">
										<button class="btn btn-sm btn-editar consultar-btn py-0 px-2"
											style="font-size: 0.8rem;" title="Editar nota">
											<i class="fas fa-pencil-alt"></i>
										</button>
									</td>
								</tr>
								<tr data-exam-id="3">
									<td>Trabajo final</td>
									<td>15/02/2024</td>
									<td><span class="badge bg-warning">Pendiente</span></td>
									<td class="nota-value">-</td>
									<td class="text-center">
										<button class="btn btn-sm btn-editar consultar-btn py-0 px-2"
											style="font-size: 0.8rem;" title="Editar nota">
											<i class="fas fa-pencil-alt"></i>
										</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="mt-4 d-flex justify-content-between">
					<button id="btnVolver" class="btn btn-outline-secondary">
						<i class="fas fa-arrow-left me-2"></i>Volver
					</button>
				</div>

				<!-- Modal para modificar nota -->
				<div class="modal fade" id="modalModificarNota" tabindex="-1"
					aria-labelledby="modalModificarNotaLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="modalModificarNotaLabel">Modificar
									Nota</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form id="formModificarNota">
									<input type="hidden" id="examId">
									<div class="mb-3">
										<label for="examName" class="form-label">Evaluación:</label> <input
											type="text" class="form-control" id="examName" readonly>
									</div>
									<div class="mb-3">
										<label for="notaActual" class="form-label">Nota
											Actual:</label> <input type="text" class="form-control"
											id="notaActual" readonly>
									</div>
									<div class="mb-3">
										<label for="nuevaNota" class="form-label">Nueva Nota:</label>
										<input type="number" class="form-control" id="nuevaNota"
											min="0" max="10" step="0.1" required>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-secondary"
									data-bs-dismiss="modal">Cancelar</button>
								<button type="button" class="btn btn-secondary"
									id="btnGuardarNota">Guardar Cambios</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
</div>

<jsp:include page="../includes/footer.html" />
<script src="/js/infoAsignatura.js"></script>
