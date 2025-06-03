document.addEventListener('DOMContentLoaded', function() {
    // Inicializar el modal para asegurar que funcione correctamente
    const modalElement = document.getElementById('editarCalificacionModal');
    const modalInstance = new bootstrap.Modal(modalElement);
    
    const guardarBtn = document.getElementById('guardarCalificacion');
    const calificacionInput = document.getElementById('calificacionInput');
    const calificacionSpan = document.querySelector('#cuerpoTabla td:last-child span');
    
    // Manejar el clic en el botón guardar
    guardarBtn.addEventListener('click', function() {
        const nuevaCalificacion = parseFloat(calificacionInput.value);
        
        // Validar que sea un número entre 0 y 10
        if (isNaN(nuevaCalificacion) || nuevaCalificacion < 0 || nuevaCalificacion > 10) {
            alert('Por favor, ingrese una calificación válida entre 0 y 10.');
            return;
        }
        calificacionSpan.textContent = nuevaCalificacion.toFixed(1);
        modalInstance.hide();
    });
}); 