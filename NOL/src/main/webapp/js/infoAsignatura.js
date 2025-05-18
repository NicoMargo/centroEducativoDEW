
document.addEventListener('DOMContentLoaded', function() {
  let isProfesor = false;
  const userSession = JSON.parse(localStorage.getItem('userSession') || '{}');
  isProfesor = userSession.tipo === 'profesor';

  const datosAsignatura = {
    nombre: "Desarrollo Web",
    codigo: "DEW",
    profesor: "Carlos Rodríguez García",
    curso: 3,
    cuatrimestre: "Primero", 
    creditos: 6,
    examenes: [
      { id: 1, nombre: "Primer parcial", fecha: "15/10/2023", estado: "Completado", nota: 8.5 },
      { id: 2, nombre: "Segundo parcial", fecha: "10/12/2023", estado: "Completado", nota: 7.2 },
      { id: 3, nombre: "Trabajo final", fecha: "15/02/2024", estado: "Pendiente", nota: null }
    ]
  };

  const modalModificarNota = new bootstrap.Modal(document.getElementById('modalModificarNota'));
  const examIdInput = document.getElementById('examId');
  const examNameInput = document.getElementById('examName');
  const notaActualInput = document.getElementById('notaActual');
  const nuevaNotaInput = document.getElementById('nuevaNota');
  const btnGuardarNota = document.getElementById('btnGuardarNota');

  // Inicializar datos
  document.getElementById('nombreAsignatura').textContent = datosAsignatura.nombre;
  document.getElementById('codigoAsignatura').textContent = datosAsignatura.codigo;
  document.getElementById('nombreProfesor').textContent = datosAsignatura.profesor;
  
  // Añadir información adicional de la asignatura
  const infoAsignatura = document.createElement('div');
  infoAsignatura.className = 'asignatura-info mt-2';
  
  if (datosAsignatura.curso) {
    const badgeCurso = document.createElement('span');
    badgeCurso.className = 'badge';
    badgeCurso.innerHTML = `<i class="fas fa-graduation-cap"></i> Curso: ${datosAsignatura.curso}`;
    infoAsignatura.appendChild(badgeCurso);
  }
  
  if (datosAsignatura.cuatrimestre) {
    const badgeCuatrimestre = document.createElement('span');
    badgeCuatrimestre.className = 'badge';
    badgeCuatrimestre.innerHTML = `<i class="fas fa-calendar-alt"></i> Cuatrimestre: ${datosAsignatura.cuatrimestre}`;
    infoAsignatura.appendChild(badgeCuatrimestre);
  }
  
  if (datosAsignatura.creditos) {
    const badgeCreditos = document.createElement('span');
    badgeCreditos.className = 'badge';
    badgeCreditos.innerHTML = `<i class="fas fa-award"></i> Créditos: ${datosAsignatura.creditos}`;
    infoAsignatura.appendChild(badgeCreditos);
  }
  
  const nombreAsignaturaContainer = document.getElementById('nombreAsignatura').parentNode;
  nombreAsignaturaContainer.appendChild(infoAsignatura);

  // Manejar evento del botón volver
  document.getElementById('btnVolver').addEventListener('click', function() {
    window.history.back();
  });

  // Disable edit buttons if not a professor
  const botonesEditar = document.querySelectorAll('.btn-editar');
  botonesEditar.forEach(boton => {
    if (!isProfesor) {
      boton.classList.add('disabled', 'btn-secondary');
      boton.classList.remove('consultar-btn');
      boton.setAttribute('disabled', 'disabled');
      boton.setAttribute('title', 'Solo profesores pueden editar notas');
    }

    boton.addEventListener('click', function() {
      if (!isProfesor) return;
      
      const fila = this.closest('tr');
      const examId = fila.getAttribute('data-exam-id');
      const examen = datosAsignatura.examenes.find(ex => ex.id == examId);
      
      if (examen) {
        abrirModalModificacion(examen);
      }
    });
  });

  

  
}); 