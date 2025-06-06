document.addEventListener('DOMContentLoaded', function () {
  var alumno = {
    nombre: document.getElementById('nombre') ? document.getElementById('nombre').textContent.trim() : '',
    apellidos: document.getElementById('apellidos') ? document.getElementById('apellidos').textContent.trim() : '',
    dni: document.getElementById('dni') ? document.getElementById('dni').textContent.trim() : '',
    fotoSrc: document.getElementById('fotoAlumno') ? document.getElementById('fotoAlumno').src : ''
  };

  var asignaturas = [];
  var filas = document.querySelectorAll('table tbody tr');

  for (var i = 0; i < filas.length; i++) {
    var celdas = filas[i].getElementsByTagName('td');

    if (celdas.length >= 2 && filas[i].textContent.indexOf('No hay asignaturas') === -1) {
      var textoAsignatura = celdas[0].textContent.trim(); // ej: "DEW - Desarrollo Web"
      var nota = celdas[1].textContent.trim();

      // Separar acrónimo y nombre si están separados por " - "
      var acronimo = '';
      var nombreAsig = '';

      if (textoAsignatura.indexOf(' - ') !== -1) {
        var partes = textoAsignatura.split(' - ');
        acronimo = partes[0].trim();
        nombreAsig = partes[1].trim();
      } else {
        acronimo = textoAsignatura;
        nombreAsig = textoAsignatura;
      }

      // Evitar nulos por seguridad
      asignaturas.push({
        acronimo: acronimo || '',
        nombre: nombreAsig || acronimo,
        nota: nota || '—'
      });
    }
  }

  var btn = document.getElementById('btnCertificado');
  if (btn) {
    btn.addEventListener('click', function () {
      var fecha = new Date().toLocaleDateString('es-ES');
      var html = generarHTMLCertificado(alumno, fecha, asignaturas);
      var nuevaVentana = window.open('', '_blank');
      nuevaVentana.document.write(html);
      nuevaVentana.document.close();
    });
  }
});

function generarHTMLCertificado(alumno, fecha, asignaturas) {
  var folder = location.pathname.split('/')[1];
  var pathBase = folder ? '/' + folder : '';

  var tabla = '';
  for (var i = 0; i < asignaturas.length; i++) {
    tabla += '<tr>' +
      '<td>' + asignaturas[i].acronimo + '</td>' +
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
              '<th style="padding:8px;">Nota</th>' +
            '</tr>' +
          '</thead>' +
          '<tbody>' + tabla + '</tbody>' +
        '</table>' +
        '<div class="firma">' +
          'Valencia, ' + fecha + '<br><br>' +
          '___________________________<br>' +
          'Dirección del Centro Educativo' +
        '</div>' +
      '</div>' +
      '<script>window.onload = function() { window.print(); }<\/script>' +
    '</body>' +
    '</html>';
}
