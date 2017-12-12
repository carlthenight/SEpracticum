package com.Controller;

import com.Model.User;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "loginController")
public class loginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uphone = request.getParameter("uphone");
        String upsw = request.getParameter("upsw");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        if(uphone==null || upsw ==null){
            pw.write("ERRO");
        }else{
            User user = new User(uphone,upsw);
            int responseCode = user.doLogin();
            Map<String, String> map = new HashMap<String, String>();
            map.put("result",String.valueOf(responseCode));
            String result = JSON.toJSONString(map);
            pw.write(result);
        }
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
