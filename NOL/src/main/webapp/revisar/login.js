document.addEventListener('DOMContentLoaded', () => {
  const btnAlumno = document.getElementById('btnAlumno');
  const btnProfesor = document.getElementById('btnProfesor');
  const heading = document.getElementById('mainHeading');
  const alumnoText = "CONSULTA TUS EXÁMENES";
  const profesorText = "CONSULTA/MODIFICA LOS EXÁMENES";

  const formAlumno = document.getElementById('formAlumno');
  const formProfesor = document.getElementById('formProfesor');

  btnAlumno.addEventListener('click', () => {
    btnAlumno.classList.add('active');
    btnProfesor.classList.remove('active');
    heading.textContent = alumnoText;
    document.title = "Login - Alumno";
    formAlumno.style.display = "block";
    formProfesor.style.display = "none";
  });

  btnProfesor.addEventListener('click', () => {
    btnProfesor.classList.add('active');
    btnAlumno.classList.remove('active');
    heading.textContent = profesorText;
    document.title = "Login - Profesor";
    formProfesor.style.display = "block";
    formAlumno.style.display = "none";
  });

  // Toggle password alumno
  const togglePasswordAlumno = document.getElementById('togglePasswordAlumno');
  const passwordInputAlumno = document.getElementById('clave');
  const eyeIconAlumno = document.getElementById('eyeIconAlumno');

  togglePasswordAlumno.addEventListener('click', () => {
    if (passwordInputAlumno.type === 'password') {
      passwordInputAlumno.type = 'text';
      eyeIconAlumno.classList.remove('fa-eye');
      eyeIconAlumno.classList.add('fa-eye-slash');
    } else {
      passwordInputAlumno.type = 'password';
      eyeIconAlumno.classList.remove('fa-eye-slash');
      eyeIconAlumno.classList.add('fa-eye');
    }
  });

  // Toggle password profesor
  const togglePasswordProfesor = document.getElementById('togglePasswordProfesor');
  const passwordInputProfesor = document.getElementById('claveProfe');
  const eyeIconProfesor = document.getElementById('eyeIconProfesor');

  togglePasswordProfesor.addEventListener('click', () => {
    if (passwordInputProfesor.type === 'password') {
      passwordInputProfesor.type = 'text';
      eyeIconProfesor.classList.remove('fa-eye');
      eyeIconProfesor.classList.add('fa-eye-slash');
    } else {
      passwordInputProfesor.type = 'password';
      eyeIconProfesor.classList.remove('fa-eye-slash');
      eyeIconProfesor.classList.add('fa-eye');
    }
  });
});
