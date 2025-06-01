document.addEventListener('DOMContentLoaded', function() {
  const userSession = JSON.parse(localStorage.getItem('userSession') || 'null');


  const urlParams = new URLSearchParams(window.location.search);
  const dni = urlParams.get('dni') || (userSession ? userSession.dni : '12345678A');

  const alumnoEjemplo = {
    nombre: "Ana María",
    apellidos: "García López",
    dni: dni
  };

  document.getElementById('nombre').textContent = alumnoEjemplo.nombre;
  document.getElementById('apellidos').textContent = alumnoEjemplo.apellidos;
  document.getElementById('dni').textContent = alumnoEjemplo.dni;

  const asignaturasEjemplo = [
    { 
      nombre: "Desarrollo Web", 
      acronimo: "DEW",
      profesor: { nombre: "Carlos", apellidos: "Rodríguez Martín" }
    },
    { 
      nombre: "Interfaces Web", 
      acronimo: "DIW",
      profesor: { nombre: "María", apellidos: "Sánchez López" }
    },
    { 
      nombre: "Despliegue de Aplicaciones", 
      acronimo: "DAW",
      profesor: { nombre: "Juan", apellidos: "Pérez García" }
    }
  ];
  mostrarAsignaturas(asignaturasEjemplo);
});

// Función para mostrar asignaturas
function mostrarAsignaturas(asignaturas) {
  const asignaturasBody = document.getElementById('asignaturasBody');
  asignaturasBody.innerHTML = '';

  if (!asignaturas || asignaturas.length === 0) {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td colspan="3" class="text-center">No hay asignaturas matriculadas</td>
    `;
    asignaturasBody.appendChild(row);
    return;
  }

  asignaturas.forEach(asignatura => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${asignatura.nombre} (${asignatura.acronimo})</td>
      <td>${asignatura.profesor ? `${asignatura.profesor.nombre} ${asignatura.profesor.apellidos}` : 'Sin asignar'}</td>
      <td class="text-center">
        <button class="btn btn-secondary consultar-btn py-0 px-2" style="font-size: 0.8rem;" data-acronimo="${asignatura.acronimo}">
          <i class="fas fa-search me-1"></i>Consultar
        </button>
      </td>
    `;
    asignaturasBody.appendChild(row);

  });
}