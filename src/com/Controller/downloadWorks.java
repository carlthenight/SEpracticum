package com.Controller;

import com.Model.DButils;
import com.Model.User;
import com.Model.Works;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import sun.security.pkcs11.Secmod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "downloadWorks")
public class downloadWorks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p_id = request.getParameter("p_id");
        String u_id = request.getParameter("uid");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        if(p_id == null || u_id == null){
            pw.write("ERRO");
        }else{
            Works works = new Works(u_id,p_id);
            List list = works.downloadWorks();
            String result = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
            pw.write(result);
        }
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
