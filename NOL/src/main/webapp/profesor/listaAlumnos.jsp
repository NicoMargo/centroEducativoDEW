<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.models.Alumno, dew.models.Asignatura" %>
<jsp:include page="/includes/header.jsp"/>

<div class="container">
        <h2 class="fw-bold text-center mx-auto mb-4">LISTA DE ALUMNOS</h2>
        <main class="pb-5">
            <div class="perfil-card border">
                <div class="card-body">
                    <section class="datos-header">
                        <h3 class="fw-semibold mb-3 text-center">
                            <i class="fas fa-book me-2"></i><span id="asignatura">Asignatura</span>
                        </h3>
                    </section>

                    <section class="mt-4">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle text-center" id="tablaAlumnos">
                                <thead>
                                    <tr>
                                        <th>Nombre y Apellidos</th>
                                        <th>Email</th>
                                        <th>Calificación</th>
                                    </tr>
                                </thead>
                                <tbody id="cuerpoTabla">
                                    <tr>
                                        <td><i class="fas fa-user me-2"></i>Nombre Apellidos</td>
                                        <td><i class="fas fa-envelope me-2"></i>email@ejemplo.com</td>
                                        <td>
                                            <div class="d-flex align-items-center justify-content-center">
                                                <span>0.0</span>
                                                <button class="btn btn-sm btn-primary ms-2" data-id="1" title="Modificar calificación" data-bs-toggle="modal" data-bs-target="#editarCalificacionModal">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
        </main>
    </div>

    <div class="modal fade" id="editarCalificacionModal" tabindex="-1" aria-labelledby="editarCalificacionModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content perfil-card">
                <div class="modal-header border-0 pb-0 justify-content-center">
                    <h5 class="modal-title fw-bold text-center" id="editarCalificacionModalLabel">Editar Calificación</h5>
                    <button type="button" class="btn-close position-absolute end-0 me-3" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="datos-foto-container mb-4">
                        <div class="datos-personales">
                            <p class="fs-5"><i class="fas fa-user me-2"></i><strong>Nombre:</strong> <span id="modalNombre">Nombre Apellidos</span></p>
                            <p class="fs-5"><i class="fas fa-id-card me-2"></i><strong>DNI:</strong> <span id="modalDNI">12345678W</span></p>
                            <p class="fs-5"><i class="fas fa-envelope me-2"></i><strong>Email:</strong> <span id="modalEmail">email@ejemplo.com</span></p>
                            <p class="fs-5"><i class="fas fa-book me-2"></i><strong>Asignatura:</strong> <span id="modalAsignatura">Asignatura</span></p>
                        </div>
                        <div class="photo-container">
                            <img src="img/12345678W.webp" alt="Foto del alumno" id="fotoAlumno" class="foto-alumno" />
                        </div>
                    </div>

                    <form id="formCalificacion" class="mt-5">
                        <input type="hidden" id="alumnoId" value="1">
                        <div class="mb-4">
                            <label for="calificacionInput" class="form-label fw-semibold fs-5"><i class="fas fa-graduation-cap me-2"></i>Calificación:</label>
                            <input type="number" class="form-control form-control-lg" id="calificacionInput" min="0" max="10" step="0.1" value="0.0" required>
                            <div class="form-text">Ingrese un valor entre 0 y 10. Puede usar decimales.</div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer border-0">
                    <button type="button" class="btn btn-secondary btn-lg px-4" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary btn-lg px-4" id="guardarCalificacion">Guardar cambios</button>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />