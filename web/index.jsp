<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017-12-08
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>TestFileUpLoad</title>
  </head>
  <body>
  <form action="/SEpracticum1/user/ulhPhoto" method="post" enctype="multipart/form-data">
    <input type="text" name = "uphone"><<br>
    <input type="file" name="file"><br>
    <input type="submit" value="submit">
  </form>
  </body>
</html>
