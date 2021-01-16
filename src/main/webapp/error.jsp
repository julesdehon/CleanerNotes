<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: julesdehon
  Date: 13/01/2021
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Encountered some issues</title>
</head>
<body>

    <h1>Encountered some issues</h1>
    <h2>Unfortunately, it seems some problems occurred when trying to clean up your notes. See below the reasons why</h2>

    <%--@elvariable id="problems" type="java.util.List<String>"--%>
    <c:forEach items="${problems}" var="problem">
        <p>${problem}</p>
    </c:forEach>

</body>
</html>
