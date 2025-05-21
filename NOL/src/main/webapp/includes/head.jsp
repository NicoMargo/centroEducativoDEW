<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
