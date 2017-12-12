package com.Controller;

import com.Model.DButils;
import com.Model.User;
import com.sun.jndi.toolkit.url.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "upLoadPicController")
@MultipartConfig(location = "/usr/local/test/headPic")
public class upLoadPicController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uphone = request.getParameter("uphone");
        Part part = request.getPart("file");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw=response.getWriter();
        User user = new User(uphone);
        String URL = "/headPic/" + uphone +".jpg";
        System.out.println(URL);
        if(uphone.equals("") || part == null){
            pw.write("ERRO");
        }else {
            try {
                int responseCode = user.doUploadPic(URL);
                if(responseCode >=1 ){
                    part.write(uphone + ".jpg");
                    pw.write("{\"reslut\":\"" + responseCode + "\"}" );
                    System.out.print("上传成功！");
                }else{
                    pw.write("{\"reslut\":\"" + responseCode + "\"}" );
                    System.out.print("上传失败！");
                }
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
