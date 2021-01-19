<%--@elvariable id="cleanedDir" type="String"--%>
<%--@elvariable id="problems" type="java.util.List<String>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: julesdehon
  Date: 13/01/2021
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Get your cleaned file</title>
    <link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="./favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="./favicon-16x16.png">
    <link rel="manifest" href="./site.webmanifest">
    <link rel="mask-icon" href="./safari-pinned-tab.svg" color="#5bbad5">
    <meta name="msapplication-TileColor" content="#da532c">
    <meta name="theme-color" content="#ffffff">
</head>
<body>
    <h1>Cool JSP forward dude :)</h1>
    <p>Click <a href="download?cleanedDir=${cleanedDir}">here</a> to download your cleaned up pdf!</p>
    <c:if test="${not empty problems}">
        <h3>Some problems were encountered, but the cleaned images were still produced. These were the issues:</h3>
        <c:forEach items="${problems}" var="problem">
            <p>${problem}</p>
        </c:forEach>
    </c:if>
</body>
</html>
