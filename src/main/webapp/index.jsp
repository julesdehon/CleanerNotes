<%--
  Created by IntelliJ IDEA.
  User: julesdehon
  Date: 07/01/2021
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello, I am a Java web app!</title>
</head>
<body>
<h1>Simple Java Web App Demo</h1>
<form method="post" action="UploadServlet" enctype="multipart/form-data">
    <input type="file" name="file" />
    <input type="submit" value="Upload" />
</form>
</body>
</html>