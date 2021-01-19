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
        <title>CleanerNotes</title>
        <script src="https://kit.fontawesome.com/22764d8773.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/main.css" />
        <link rel="stylesheet" href="https://use.typekit.net/ixd8bfq.css">
        <link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="./favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="./favicon-16x16.png">
        <link rel="manifest" href="./site.webmanifest">
        <link rel="mask-icon" href="./safari-pinned-tab.svg" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#da532c">
        <meta name="theme-color" content="#ffffff">
        <script src="js/main.js" type="module"></script>
    </head>
    <body>
        <div id="header">
            <h1>CleanerNotes</h1>
        </div>
        <div id="content">
            <form method="post" action="upload" enctype="multipart/form-data">
                <label class="btn fileUploadBtn">Select Files
                    <input type="file" name="file" multiple="multiple" />
                </label>
                <label class="checkmark-container">White Background
                    <input type="checkbox" id="whiteBG" name="whiteBG" value="white" checked="checked">
                    <span class="checkmark"></span>
                </label>
                <label class="checkmark-container">Saturate Colours
                    <input type="checkbox" id="saturate" name="saturate" value="saturate" checked="checked">
                    <span class="checkmark"></span>
                </label>
                <div id="ncol-container">
                    <h3>Output Colours:</h3>
                    <fieldset id="ncol"></fieldset> <%-- will be populated by main.js --%>
                </div>
                <label class="btn submit-btn">Upload
                    <input type="submit" value="Upload" />
                </label>
            </form>
        </div>
    </body>
</html>