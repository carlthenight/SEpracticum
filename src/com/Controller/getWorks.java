package com.Controller;

import com.Model.DButils;
import com.Model.Works;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "getWorks")
public class getWorks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        String result = null;
        if(uid==null){
            pw.write("ERRO");
        }else{
            List list = new ArrayList();
            Works works = new Works(uid);
            try {
                list = works.getWorks();
                Map map = new HashMap();
                map.put("array",list);
                if(list.isEmpty()){
                    result = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
                }else{
                    result = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
                }
                System.out.println(result);
                pw.write(result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
