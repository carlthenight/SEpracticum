package com.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.Model.User;
import com.alibaba.fastjson.JSON;

@WebServlet(name = "registerController")
public class registerController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uphone = request.getParameter("uphone");
        String upsw = request.getParameter("upsw");
        String uname = request.getParameter("uname");
        User user = new User(uphone, upsw, uname);
        int responseCode = user.doRegister();
        Map<String, String> map = new HashMap<String, String>();
        map.put("result",String.valueOf(responseCode));
        String result = JSON.toJSONString(map);
        PrintWriter pw = response.getWriter();
        pw.write(result);
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
