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
        <link rel="stylesheet" href="css/home.css" />
        <link rel="stylesheet" href="css/main.css" />
        <link rel="stylesheet" href="https://use.typekit.net/ixd8bfq.css">
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
        <div id="dimmer"></div>
        <div id="header">
            <a href="." class="home-link"><h1>CleanerNotes</h1></a>
        </div>
        <div id="infoBtn">?</div>
        <div id="info">
            <span id="closeInfo"></span>
            <div id="info-container">
                <h1>What is CleanerNotes?</h1>
                <span id="tagline">CleanerNotes is a simple web app that cleans and packages photos of handwritten notes into a pdf.</span>
                <h2>How to use?</h2>
                <p>
                    Simply upload any number of photos of your handwritten notes in
                    <code>JPEG</code>, <code>PNG</code>, <code>BMP</code>, <code>WEBMP</code> or
                    <code>GIF</code> format. Choose the settings you'd like, and CleanerNotes will
                    produce a lovely cleaned up pdf for you.
                </p>
                <p>
                    CleanerNotes will identify the background colour, and group all the other colours
                    in your photo into a number of groups you decide by setting the <code>output colours</code>
                    option. You can choose if you would like CleanerNotes to force the background of
                    each note page in the pdf to white, and if you would like CleanerNotes to saturate
                    the colours in the cleaned up pdf, in order to improve clarity.
                </p>
                <h2>How does it do it?</h2>
                <p>
                    Behind the scenes, CleanerNotes analyses each input image, and identifies the most
                    frequently occurring colour. This is taken to be the background colour. It then
                    uses a <a href="https://en.wikipedia.org/wiki/K-means_clustering">k-means clustering</a>
                    algorithm to split the remaining foreground colours into a number of distinct groups.
                    Finally, it passes over the image, and sets the colour of each pixel to its most similar
                    colour group.
                </p>
                <p>
                    Behind the scenes, CleanerNotes works extremely similarly to the method described
                    <a href="https://mzucker.github.io/2016/09/20/noteshrink.html">here</a>. It is mainly
                    just a web app wrapped around very similar code translated into Java.
                </p>
                <h2>Why?</h2>
                <p>
                    I made this mostly for fun, and to learn more about the technologies behind a large
                    number of web apps that we use every day. CleanerNotes is implemented by Apache
                    Tomcat 9, and runs on an AWS EC2 instance. I really enjoyed learning to write
                    and deploy a web app in a similar way that a web app would be deployed in real life.
                </p>
                <p>
                    The source code can be found on my <a href="https://github.com/julesdehon/CleanerNotes">github</a>
                    Along with a number of other cool projects you should have a look at!
                </p>
            </div>
        </div>
        <div id="content">
            <form method="post" action="upload" enctype="multipart/form-data" id="uploadForm">
                <label class="btn fileUploadBtn"><span id="fileUploadTxt">Select Files</span>
                    <input type="file" id="fileUpload" name="file" multiple="multiple" />
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
            <div class="boxes">
                <div class="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div class="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div class="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div class="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
            </div>
        </div>
        <script src="js/main.js" type="module"></script>
    </body>
</html>