<%--
  Created by IntelliJ IDEA.
  User: julesdehon
  Date: 07/01/2021
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hello, I am a Java web app!</title>
    <link rel="stylesheet" href="css/main.css" />
</head>
<body>
<h1>CleanerNotes</h1>
<form method="post" action="upload" enctype="multipart/form-data">
    <input type="file" id="fileSelection" name="file" multiple="multiple" />
    <input type="checkbox" id="whiteBG" name="whiteBG" value="white" checked>
    <label for="whiteBG">Force background colour to white</label><br>
    <input type="checkbox" id="saturate" name="saturate" value="saturate" checked>
    <label for="saturate">Saturate Colours for clarity</label><br>
    <label for="numColors">Number of output colours: </label>
    <input type="number" id="numColors" name="numColors" min="2" max="15" value="8">
    <input type="submit" value="Upload" />
</form>
</body>
</html>