<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<footer class="footer mt-auto py-3 bg-light">
		  <div class="container text-center">
		    <p>&copy; Copyright DEW</p>
		    <p>Ruta de logs: ${applicationScope.logFilePathResolved}</p>
		  </div>
		</footer>
		
		<!-- Scripts de la aplicación -->
		<script 
		  src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" 
		  integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" 
		  crossorigin="anonymous">
		</script>
		<script src="${pageContext.request.contextPath}/js/listaAlumnos.js"></script>
		<script src="${pageContext.request.contextPath}/js/perfilAlumno.js"></script>
		<script src="${pageContext.request.contextPath}/js/login.js"></script>
		<script src="${pageContext.request.contextPath}/js/perfilProfesor.js"></script>
	</body>
</html>
