<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!DOCTYPE html>
  <html lang="es">
  <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>NOL DEW</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfilAlumno.css"/>
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/infoAsignatura.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profesor.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
  	  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listaAlumnos.css">
  </head>
  <body>
      <h1 class="fw-bold">NOTAS ONLINE</h1>
      <h2 class="fw-bold">GESTION DE ASIGNATURAS</h2>
      
      <div class="top-buttons d-flex gap-2">
    <a href="index.jsp" class="btn btn-dark shadow-sm">
        <i class="fas fa-home me-2"></i>Inicio
    </a>

    <c:if test="${not empty sessionScope.dni}">
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-dark shadow-sm">
            <i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión
        </a>
    </c:if>
</div>
      
      <div class="separator"></div>