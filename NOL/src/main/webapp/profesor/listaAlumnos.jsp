<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/header.jsp"/>

<div class="container py-4">
  <h2 class="text-center fw-bold mb-4">Navegar Alumnos por Asignatura</h2>

  <!-- Cabecera: muestra el acrónimo -->
  <div class="d-flex justify-content-center mb-3">
    <span class="fs-4">
      Asignatura: <strong><c:out value="${param.acronimo}"/></strong>
    </span>
  </div>

  <!-- Card para un solo alumno -->
  <div class="card mx-auto" style="max-width: 500px;">
    <div class="card-body text-center">
    
    <!-- Nota promedio -->
      <div class="bg-light p-2 rounded mb-4">
        <span class="fw-semibold">Nota promedio de la asignatura:</span>
        <span id="notaPromedio" class="ms-2 fs-5 fw-bold">-</span>
      </div>

      <!-- DNI del alumno en grande -->
	    <h3 id="dniAlumno" class="card-title mb-3">–</h3>
	    <h4 id="nombreAlumno" class="mb-1">–</h4>
		<h5 id="apellidosAlumno" class="mb-3">–</h5>
		<img id="fotoAlumno" src="" alt="Foto del alumno" class="img-fluid rounded" style="max-width: 150px;"/>

      <!-- Nota, editable -->
      <div class="mb-3">
        <label for="notaInput" class="form-label fw-semibold">Calificación:</label>
        <input type="number"
               id="notaInput"
               class="form-control form-control-lg text-center"
               min="0"
               max="10"
               step="1"
               placeholder="—"
        />
        <div class="form-text">Al cambiar la nota y presionar enter o salir del input se guardará automáticamente. <br/> se puede cambiar de alumno con las flechas izquierda y derecha</div>
      </div>
      
      <div id="mensajeExito" class="alert alert-success mx-3 mb-3 d-none">
                    <i class="fas fa-check-circle me-2"></i> La nota ha sido modificada correctamente.
      </div>

      <!-- Flechas de navegación -->
      <div class="d-flex justify-content-between">
        <button id="prevBtn" class="btn btn-outline-primary" title="Alumno anterior" disabled>
          &larr;
        </button>
        <button id="nextBtn" class="btn btn-outline-primary" title="Próximo alumno" disabled>
          &rarr;
        </button>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/listaAlumnos.js"></script>
<jsp:include page="/includes/footer.jsp"/>
