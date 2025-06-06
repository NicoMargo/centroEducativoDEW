document.addEventListener('DOMContentLoaded', function () {
  const userSession = JSON.parse(localStorage.getItem('userSession') || 'null');

  const urlParams = new URLSearchParams(window.location.search);
  const dni = urlParams.get('dni') || (userSession ? userSession.dni : '12345678A');

  const alumno = {
    nombre: "Ana María",
    apellidos: "García López",
    dni: dni,
    fotoSrc: document.getElementById('fotoAlumno') ? document.getElementById('fotoAlumno').src : ''
  };

  if (document.getElementById('nombre')) document.getElementById('nombre').textContent = alumno.nombre;
  if (document.getElementById('apellidos')) document.getElementById('apellidos').textContent = alumno.apellidos;
  if (document.getElementById('dni')) document.getElementById('dni').textContent = alumno.dni;

  const asignaturas = [
    { nombre: "Desarrollo Web", acronimo: "DEW", nota: "8.5" },
    { nombre: "Interfaces Web", acronimo: "DIW", nota: "9.2" },
    { nombre: "Despliegue de Aplicaciones", acronimo: "DAW", nota: "7.8" }
  ];

  mostrarAsignaturas(asignaturas);

  const btn = document.getElementById('btnCertificado');
  if (btn) {
    btn.addEventListener('click', function () {
      const fecha = new Date().toLocaleDateString('es-ES');
      const html = generarHTMLCertificado(alumno, fecha, asignaturas);
      const nuevaVentana = window.open('', '_blank');
      nuevaVentana.document.write(html);
      nuevaVentana.document.close();
    });
  }
});

function mostrarAsignaturas(asignaturas) {
  const asignaturasBody = document.getElementById('asignaturasBody');
  if (!asignaturasBody) return;

  asignaturasBody.innerHTML = '';

  if (asignaturas.length === 0) {
    asignaturasBody.innerHTML = '<tr><td colspan="3" class="text-center">No hay asignaturas matriculadas</td></tr>';
    return;
  }

  asignaturas.forEach(function (a) {
    const row = document.createElement('tr');
    row.innerHTML =
      '<td>' + a.nombre + ' (' + a.acronimo + ')</td>' +
      '<td>' + a.nota + '</td>' +
      '<td class="text-center">' +
        '<button class="btn btn-secondary consultar-btn py-0 px-2" style="font-size: 0.8rem;" data-acronimo="' + a.acronimo + '">' +
          '<i class="fas fa-search me-1"></i>Consultar' +
        '</button>' +
      '</td>';
    asignaturasBody.appendChild(row);
  });
}

function generarHTMLCertificado(alumno, fecha, asignaturas) {
  var folder = location.pathname.split('/')[1];
  var pathBase = folder ? '/' + folder : '';

  var tabla = '';
  for (var i = 0; i < asignaturas.length; i++) {
    tabla += '<tr>' +
      '<td>' + asignaturas[i].acronimo + '</td>' +
      '<td>' + asignaturas[i].nombre + '</td>' +
      '<td>' + asignaturas[i].nota + '</td>' +
    '</tr>';
  }

  return '' +
    '<!DOCTYPE html>' +
    '<html lang="es">' +
    '<head>' +
      '<meta charset="UTF-8">' +
      '<title>Certificado de Estudios</title>' +
      '<link rel="stylesheet" href="' + pathBase + '/css/certificado.css">' +
    '</head>' +
    '<body>' +
      '<div class="cert-container">' +
        '<h1>Certificado de Estudios</h1>' +
        '<img src="' + alumno.fotoSrc + '" alt="Foto del Alumno" class="foto" />' +
        '<div class="cert-info">' +
          'Se certifica que el/la alumno/a <span class="bold">' + alumno.nombre + ' ' + alumno.apellidos + '</span>, ' +
          'con documento nacional de identidad <span class="bold">' + alumno.dni + '</span>, ha cursado ' +
          'satisfactoriamente sus estudios en esta institución educativa.' +
        '</div>' +
        '<h2 style="margin-top:2cm;">Asignaturas Cursadas</h2>' +
        '<table style="width:100%; border-collapse:collapse; margin-top:1cm;" border="1">' +
          '<thead>' +
            '<tr>' +
              '<th style="padding:8px;">Acrónimo</th>' +
              '<th style="padding:8px;">Nombre</th>' +
              '<th style="padding:8px;">Nota</th>' +
            '</tr>' +
          '</thead>' +
          '<tbody>' +
            tabla +
          '</tbody>' +
        '</table>' +
        '<div class="firma">' +
          'Valencia, ' + fecha + '<br><br>' +
          '___________________________<br>' +
          'Dirección del Centro Educativo' +
        '</div>' +
      '</div>' +
      '<script>' +
        'window.onload = function() { window.print(); };' +
      '<\/script>' +
    '</body>' +
    '</html>';
}
