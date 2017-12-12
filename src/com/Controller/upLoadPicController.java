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
//@MultipartConfig(location = "D://test")
@MultipartConfig(location = "/usr/local/test/headPic")
public class upLoadPicController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uphone = request.getParameter("uphone");
        Part part = request.getPart("file");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw=response.getWriter();
        if(uphone.equals("") || part == null){
            pw.write("ERRO");
        }else {
            try {
                User user = new User(uphone);
                String namePic = part.getSubmittedFileName();
                String filenameEx = namePic.substring(namePic.length()-4);
                if(filenameEx.equals(".jpg") || filenameEx.equals(".png")){
                    System.out.println(filenameEx);
                    String URL = "/headPic/" + uphone + filenameEx;
                    System.out.println(URL);
                    int responseCode = user.doUploadPic(URL);
                    if(responseCode >=1 ){
                        part.write(uphone + filenameEx);
                        pw.write("{\"result\":\"" + responseCode + "\"}" );
                        System.out.print("上传成功！");
                    }else{
                        pw.write("{\"result\":\"" + responseCode + "\"}" );
                        System.out.print("上传失败！");
                    }
                }else{
                    pw.write("ERRO");
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
