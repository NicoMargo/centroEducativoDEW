document.addEventListener('DOMContentLoaded', function() {
  const btnAlumno = document.getElementById('btnAlumno');
  const btnProfesor = document.getElementById('btnProfesor');
  const heading = document.querySelector('main h2');
  const alumnoText = "CONSULTA TUS EXÁMENES";
  const profesorText = "CONSULTA/MODIFICA LOS EXÁMENES";

  btnAlumno.addEventListener('click', () => {
    btnAlumno.classList.add('active');
    btnProfesor.classList.remove('active');
    document.title = "Login - Alumno";
    heading.textContent = alumnoText;
  });

  btnProfesor.addEventListener('click', () => {
    btnProfesor.classList.add('active');
    btnAlumno.classList.remove('active');
    document.title = "Login - Profesor";
    heading.textContent = profesorText;
  });

  const togglePassword = document.getElementById('togglePassword');
  const passwordInput = document.getElementById('clave');
  const eyeIcon = document.getElementById('eyeIcon');
  
  togglePassword.addEventListener('click', () => {
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      eyeIcon.classList.remove('fa-eye');
      eyeIcon.classList.add('fa-eye-slash');
    } else {
      passwordInput.type = 'password';
      eyeIcon.classList.remove('fa-eye-slash');
      eyeIcon.classList.add('fa-eye');
    }
  });

  document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const isAlumno = btnAlumno.classList.contains('active');
    const dni = document.getElementById('usuario').value;
    const password = document.getElementById('clave').value;
    
    if (!dni || !password) {
      alert('Por favor, completa todos los campos');
      return;
    }
    
    const userSession = {
      dni: dni,
      tipo: isAlumno ? 'alumno' : 'profesor',
      nombre: 'Usuario',
      apellidos: 'De Ejemplo'
    };
    
    localStorage.setItem('userSession', JSON.stringify(userSession));
    
    if (isAlumno) {
      window.location.href = `perfil.html?dni=${dni}`;
    } else {
      window.location.href = `profesor-dashboard.html?dni=${dni}`;
    }
  });
});