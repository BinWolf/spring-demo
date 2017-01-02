<%--
  Created by IntelliJ IDEA.
  User: wolf
  Date: 17/1/2
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>upload page</title>
</head>
<body>
上传页面：<br>
<form action="upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="上传">
</form>

</body>
</html>
