// perfil.js

document.addEventListener('DOMContentLoaded', () => {
  // Aquí puedes poner la lógica que maneje clicks en los botones consultar

  document.querySelectorAll('.consultar-btn').forEach(button => {
    button.addEventListener('click', () => {
      const asignatura = button.getAttribute('data-asignatura');
      alert(`Consultar notas de la asignatura: ${asignatura}`);
      // Aquí podrías hacer una petición AJAX para obtener detalles, etc.
    });
  });
});
