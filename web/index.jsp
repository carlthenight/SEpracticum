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
  <form action="/SEpracticum1/works/ulStory" method="post" enctype="multipart/form-data">
    用户:<input type="text" name = "uid" ><br>
    故事标题:<input type="text" name = "title"><br>
    故事顺序:<input type="text" name = "g_id"><br>
      故事描述:<input type="text" name = "g_content" ><br>
    故事图片<input type="file" name="file"><br>
    故事顺序:<input type="text" name = "g_id"><br>
      故事描述:<input type="text" name = "g_content" ><br>
    故事图片:<input type="file" name="file"><br>

    <input type="submit" value="submit">
  </form>
  </body>
</html>
