<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/includes/header.html" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/infoAsignatura.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />

<div class="container">
  <main class="text-center pb-5">
    <h2 class="fw-bold mb-4">INFORMACIÓN DE ASIGNATURA</h2>

    <div class="asignatura-card border">
      <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h3 class="fs-4 mb-0">${asignatura.nombre}</h3>
          <span class="badge bg-secondary">${asignatura.acronimo}</span>
        </div>

        <!-- Detalles de la asignatura -->
        <div class="info-general p-3 bg-light rounded mb-4 text-start">
          <ul class="list-unstyled mb-0">
            <li><strong>Curso:</strong> ${asignatura.curso}</li>
            <li><strong>Cuatrimestre:</strong> ${asignatura.cuatrimestre}</li>
            <li><strong>Créditos:</strong> ${asignatura.creditos}</li>
          </ul>
        </div>

        <!-- Tabla de profesores -->
        <section>
          <h4 class="fs-5 mb-3"><i class="fas fa-chalkboard-teacher me-2"></i>Profesores</h4>
          <div class="table-responsive">
            <table class="table table-hover align-middle">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Apellidos</th>
                  <th>DNI</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="prof" items="${asignatura.profesores}">
                  <tr>
                    <td>${prof.nombre}</td>
                    <td>${prof.apellidos}</td>
                    <td>${prof.dni}</td>
                  </tr>
                </c:forEach>
                <c:if test="${empty asignatura.profesores}">
                  <tr>
                    <td colspan="3" class="text-center fst-italic">No hay profesores asignados</td>
                  </tr>
                </c:if>
              </tbody>
            </table>
          </div>
        </section>

        <div class="mt-4 d-flex justify-content-between">
          <button class="btn btn-outline-secondary" onclick="history.back()">
            <i class="fas fa-arrow-left me-2"></i>Volver
          </button>
        </div>
      </div>
    </div>
  </main>
</div>

<jsp:include page="/includes/footer.html" />
<script src="${pageContext.request.contextPath}/js/infoAsignatura.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
