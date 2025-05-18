document.addEventListener('DOMContentLoaded', function() {

    let isProfesor = false;
  
    // Datos estáticos de ejemplo
    const datosAsignatura = {
      nombre: "Desarrollo Web",
      codigo: "DEW",
      profesor: "Carlos Rodríguez García",
      curso: 3,
      cuatrimestre: "Primero", 
      creditos: 6,
      examenes: [
        { id: 1, nombre: "Primer parcial", fecha: "15/10/2023", estado: "Completado", nota: 8.5 },
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
  
    document.getElementById('btnVolver').addEventListener('click', function() {
      window.history.back();
    });
  
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
  
    const btnModificarNota = document.getElementById('btnModificarNota');
    if (btnModificarNota) {
      if (!isProfesor) {
        btnModificarNota.classList.add('disabled', 'btn-secondary');
        btnModificarNota.setAttribute('disabled', 'disabled');
        btnModificarNota.setAttribute('title', 'Solo profesores pueden editar notas');
      }
  
      btnModificarNota.addEventListener('click', function() {
        if (!isProfesor) return;
        alert('Seleccione un examen específico para modificar su nota usando los botones de edición en la tabla.');
      });
    }
  
    btnGuardarNota.addEventListener('click', function() {
      const examId = examIdInput.value;
      const nuevaNota = parseFloat(nuevaNotaInput.value);
      
      if (isNaN(nuevaNota) || nuevaNota < 0 || nuevaNota > 10) {
        alert('Por favor, ingrese una nota válida entre 0 y 10.');
        return;
      }
      
      const examen = datosAsignatura.examenes.find(ex => ex.id == examId);
      if (examen) {
        examen.nota = nuevaNota;
        examen.estado = "Completado";
        
        const fila = document.querySelector(`tr[data-exam-id="${examId}"]`);
        const celdaNota = fila.querySelector('.nota-value');
        const celdaEstado = fila.querySelector('.badge');
        
        celdaNota.textContent = nuevaNota.toFixed(1);
        celdaEstado.textContent = "Completado";
        celdaEstado.className = "badge bg-success";
        
        
        modalModificarNota.hide();
        
        
        mostrarMensajeExito(`Nota actualizada correctamente a ${nuevaNota.toFixed(1)}`);
      }
    });
  
    function abrirModalModificacion(examen) {
      examIdInput.value = examen.id;
      examNameInput.value = examen.nombre;
      notaActualInput.value = examen.nota !== null ? examen.nota : 'No evaluado';
      nuevaNotaInput.value = examen.nota !== null ? examen.nota : '';
      
      modalModificarNota.show();
    }
    
    function mostrarMensajeExito(mensaje) {
      const mensajeDiv = document.createElement('div');
      mensajeDiv.className = 'alert alert-success alert-dismissible fade show mt-3';
      mensajeDiv.setAttribute('role', 'alert');
      mensajeDiv.innerHTML = `
        ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      `;
      
      const examSection = document.querySelector('.examenes-section');
      examSection.prepend(mensajeDiv);
    }
  }); 