package com.Controller;

import com.Model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "getUserInfoController")
public class getUserInfoController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uphone = request.getParameter("uphone");
        PrintWriter pw = response.getWriter();
        if(uphone==null){
            pw.write("ERRO");
        }else{
            User user = new User(uphone);
            Map map = user.doGetInfo();
            String result = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
            response.setContentType("text/html");
            pw.write(result);
        }
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
