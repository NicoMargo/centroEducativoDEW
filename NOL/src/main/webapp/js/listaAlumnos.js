document.addEventListener('DOMContentLoaded', function() {
  // 1) Calcular basePath dinámicamente (para no hardcodear "/NOL")
  const context = window.location.pathname.split('/')[1] || '';
  const basePath = window.location.origin + '/' + context;

  // 2) Obtener parámetro "acronimo" de la URL actual
  const params = new URLSearchParams(window.location.search);
  const acronimo = params.get('acronimo');
  if (!acronimo) {
    console.warn('No se encontró parámetro "acronimo" en la URL');
    return;
  }

  // 3) Referencias a elementos DOM
  const dniAlumnoElem    = document.getElementById('dniAlumno');
  const nombreAlumnoElem = document.getElementById('nombreAlumno');
  const apellidosElem    = document.getElementById('apellidosAlumno');
  const fotoElem         = document.getElementById('fotoAlumno');
  const notaInput        = document.getElementById('notaInput');
  const prevBtn          = document.getElementById('prevBtn');
  const nextBtn          = document.getElementById('nextBtn');
const notaPromedioElem = document.getElementById('notaPromedio');

  let alumnosList   = [];   // Array de objetos { alumno: "DNI", nota: "...", nombre?, apellidos?, foto? }
  let currentIndex  = 0;    // Índice del alumno mostrado actualmente

  // 4) Petición AJAX inicial para obtener la lista de alumnos con sus notas
  fetch(`${basePath}/listaAlumnosPorAsignatura?acronimo=${encodeURIComponent(acronimo)}`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      // data === [ { alumno: "12345678W", nota: "8.5" }, { … }, … ]
      alumnosList = data;
      if (alumnosList.length === 0) {
        dniAlumnoElem.textContent    = 'No hay alumnos';
        nombreAlumnoElem.textContent = '';
        apellidosElem.textContent    = '';
        fotoElem.src                 = '';
        notaInput.value              = '';
        prevBtn.disabled             = true;
        nextBtn.disabled             = true;
        return;
      }
      // Mostrar el primer alumno
      currentIndex = 0;
      mostrarAlumno(currentIndex);
      calcularYMostrarPromedio();
    })
    .catch(err => {
      console.error('Error al cargar lista de alumnos:', err);
      dniAlumnoElem.textContent = 'Error al cargar alumnos';
    });

  // 5) Función para renderizar un alumno (si ya tiene datos completos los pinta; si no, hace AJAX)
  function mostrarAlumno(idx) {
    const item = alumnosList[idx];
    // Mostrar DNI y nota inmediata
    dniAlumnoElem.textContent = item.alumno;
    notaInput.value           = item.nota || '';

    // Habilitar/deshabilitar botones de flecha
    prevBtn.disabled = (idx === 0);
    nextBtn.disabled = (idx === alumnosList.length - 1);

    // Si YA tenemos nombre/apellidos/foto almacenados, pintamos directamente
    if (item.nombre && item.apellidos && item.foto) {
      nombreAlumnoElem.textContent = item.nombre;
      apellidosElem.textContent    = item.apellidos;
      fotoElem.src                 = item.foto.startsWith('data:')
                                      ? item.foto
                                      : `data:image/webp;base64,${item.foto}`;
      return;
    }

    // Sino, hacemos un fetch a /alumnoJson para traer datos completos
    fetch(`${basePath}/alumnoJson?dni=${encodeURIComponent(item.alumno)}`)
      .then(resp => {
        if (!resp.ok) {
          throw new Error(`HTTP ${resp.status} al obtener detalles de ${item.alumno}`);
        }
        return resp.json();
      })
      .then(data => {
        // data === { dni:"12345678W", nombre:"Juan", apellidos:"Pérez", asignaturas:[…], foto:"<base64…>" }
        item.nombre    = data.nombre;
        item.apellidos = data.apellidos;
        item.foto      = data.foto || null;
        // Pintar en el DOM
        nombreAlumnoElem.textContent = item.nombre;
        apellidosElem.textContent    = item.apellidos;
        if (item.foto) {
          fotoElem.src = item.foto.startsWith('data:')
                          ? item.foto
                          : `data:image/webp;base64,${item.foto}`;
        } else {
          fotoElem.src = '';
        }
      })
      .catch(err => {
        console.error('Error al cargar detalles de alumno:', err);
        nombreAlumnoElem.textContent = '[Sin nombre]';
        apellidosElem.textContent    = '';
        fotoElem.src                 = '';
      });
  }

  // 6) Eventos "clic" de flechas
  prevBtn.addEventListener('click', () => {
    if (currentIndex > 0) {
      currentIndex--;
      mostrarAlumno(currentIndex);
    }
  });
  nextBtn.addEventListener('click', () => {
    if (currentIndex < alumnosList.length - 1) {
      currentIndex++;
      mostrarAlumno(currentIndex);
    }
  });

  // 6.1) Navegar también con las teclas izquierda y derecha
  document.addEventListener('keydown', (e) => {
    // Si el foco está sobre el input de nota, no navegamos
    if (document.activeElement === notaInput) return;

    if (e.key === 'ArrowLeft') {
      if (currentIndex > 0) {
        currentIndex--;
        mostrarAlumno(currentIndex);
      }
      e.preventDefault();
    }
    if (e.key === 'ArrowRight') {
      if (currentIndex < alumnosList.length - 1) {
        currentIndex++;
        mostrarAlumno(currentIndex);
      }
      e.preventDefault();
    }
  });
  
  // Función para actualizar la nota
  function actualizarNota(mostrarMensaje = false) {
    const nuevaNota = parseFloat(notaInput.value);
    if (isNaN(nuevaNota) || nuevaNota < 0 || nuevaNota > 10) {
      alert('Ingrese una calificación válida entre 0 y 10.');
      mostrarAlumno(currentIndex);
      return;
    }
    alumnosList[currentIndex].nota = nuevaNota.toFixed(1);

    const dni      = alumnosList[currentIndex].alumno;
    const payload  = {
      acronimo: acronimo,
      dni: dni,
      nota: nuevaNota.toFixed(1)
    };

    fetch(`${basePath}/actualizarNotaPorAsignatura`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    .then(resp => {
      if (!resp.ok) {
        throw new Error(`HTTP ${resp.status} al guardar nota`);
      }
      return resp.text();
    })
    .then(texto => {
      console.log('Nota actualizada:', texto);
      
      // Mostrar mensaje solo si se especificó
      if (mostrarMensaje) {
        const mensajeExito = document.getElementById('mensajeExito');
        mensajeExito.classList.remove('d-none');
        setTimeout(() => {
          mensajeExito.classList.add('d-none');
        }, 2000);
      }
      
      // Recalcular y mostrar el promedio actualizado
      calcularYMostrarPromedio();
    })
    .catch(err => {
      console.error('Error al actualizar nota:', err);
      alert('No se pudo guardar la nota en el servidor.');
      mostrarAlumno(currentIndex);
    });
  }
  
  // 7) Cuando cambia la nota (al perder el foco), guardamos sin mensaje
  notaInput.addEventListener('change', () => {
    actualizarNota(false); // No mostrar mensaje
  });
  
  // Cuando presiona Enter, guardamos con mensaje
  notaInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      actualizarNota(true); // Mostrar mensaje
    }
  });

  // Función para calcular y mostrar la nota promedio
  function calcularYMostrarPromedio() {
    if (alumnosList.length === 0) {
      notaPromedioElem.textContent = '-';
      return;
    }
    
    let totalNotas = 0;
    let alumnosConNota = 0;
    
    // Calcular la suma total de notas
    alumnosList.forEach(alumno => {
      if (alumno.nota && !isNaN(parseFloat(alumno.nota))) {
        totalNotas += parseFloat(alumno.nota);
        alumnosConNota++;
      }
    });
    
    if (alumnosConNota === 0) {
      notaPromedioElem.textContent = '-';
    } else {
      // Calcular el promedio usando el total de alumnos
      const promedio = totalNotas / alumnosList.length;
      notaPromedioElem.textContent = promedio.toFixed(1);
    }
  }
});