<%@page contentType="text/html" pageEncoding="utf-8"%>
<jsp:include page="/includes/header.html" />
 <h2>Autenticación</h2>

  <c:if test="${param.error == 'true'}">
    <p style="color:red;">Credenciales inválidas, inténtalo de nuevo.</p>
  </c:if>

  <!-- Form alumno -->
  <h3>Acceso Alumnos</h3>
  <form action="j_security_check" method="POST">
    <label>DNI:</label>
    <input type="text" name="j_username"/><br/>
    <label>Pass:</label>
    <input type="password" name="j_password"/><br/>
    <button type="submit">Entrar como Alumno</button>
  </form>

  <!-- Form profesor -->
  <h3>Acceso Profesores</h3>
  <form action="${pageContext.request.contextPath}/j_security_check" method="POST">
    <label>DNI:</label>
    <input type="text" name="j_username"/><br/>
    <label>Pass:</label>
    <input type="password" name="j_password"/><br/>
    <button type="submit">Entrar como Profesor</button>
  </form><jsp:include page="/includes/footer.html" />