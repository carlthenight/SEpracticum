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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "deleteWorks")
public class deleteWorks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String p_id = request.getParameter("p_id");
        PrintWriter pw = response.getWriter();
        if(p_id == null ){
            pw.write("ERRO");
        }else{
            Works works = new Works();
            works.setW_id(p_id);
            int responseCode = 0;
            try {
                responseCode = works.deleteWorks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Map<String,String> map = new <String,String> HashMap();
            map.put("result",String.valueOf(responseCode));
            String result = JSON.toJSONString(map);
            System.out.println("我是reslut哟"+ result);
            pw.write(result);
        }
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
