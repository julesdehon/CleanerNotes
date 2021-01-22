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
    <title>Get your cleaned pdf</title>
    <link rel="stylesheet" href="css/main.css" />
    <link rel="stylesheet" href="css/retrieve.css" />
    <link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="./favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="./favicon-16x16.png">
    <link rel="manifest" href="./site.webmanifest">
    <link rel="mask-icon" href="./safari-pinned-tab.svg" color="#5bbad5">
    <meta name="msapplication-TileColor" content="#da532c">
    <meta name="theme-color" content="#ffffff">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>
<body>
    <div id="header">
        <a href="." class="home-link"><h1>CleanerNotes</h1></a>
    </div>
    <h1 class="title">Generated your cleaned pdf!</h1>
    <a class="downloadBtn" href="download?cleanedDir=${cleanedDir}">Download!</a>
    <c:if test="${not empty problems}">
        <div class="issues">
            <h2>Problems encountered!</h2>
            <h3>The cleaned pdf could still be produced. See details of problems below:</h3>
            <c:forEach items="${problems}" var="problem">
                <p>${problem}</p>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>
