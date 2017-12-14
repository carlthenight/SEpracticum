package com.Controller;

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

@WebServlet(name = "searchWorks")
public class searchWorks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p_title = request.getParameter("title");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        if(p_title == null){
            pw.write("ERRO");
        }else{
            List list = new ArrayList();
            String result = null;
            Works works = new Works();
            works.setW_Title(p_title);
            try {
                list = works.searchWorks();
                Map map = new HashMap();
                map.put("array",list);
                result = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pw.write(result);
        }
        pw.flush();
        pw.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
