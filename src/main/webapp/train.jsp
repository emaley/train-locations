<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
</head>
<body onload="disconnect()">
  <div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        <a href="/userUI">User Details</a>

    </c:if>
  </div>
  <h1>Train Locations</h1>
  <div>
      <div>
          <button id="connect" onclick="connect();">Realtime ON</button>
          <button id="disconnect" disabled="disabled" onclick="disconnect();">Realtime OFF</button>
      </div>
  </div>
  <table id="example" class="display" width="100%">
      <tr>
          <th>Name</th>
          <th>GPS Coordinates</th>
          <th>Destination</th>
          <th>Speed</th>
      </tr>
  </table>

  <script src="${contextPath}/resources/js/stomp.js"></script>
  <script src="${contextPath}/resources/js/app.js"></script>
  <script src="${contextPath}/resources/js/data.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
