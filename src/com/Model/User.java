package com.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class User {
    private String user_Phone;
    private String user_Password;
    private String user_name;
    private String user_id;
    private String user_headUrl;

    public User(String userphone, String userpsw, String username){
        this.user_Phone = userphone;
        this.user_Password = userpsw;
        this.user_name = username;
    }

    public User(String userphone, String userpsw){
        this.user_Phone = userphone;
        this.user_Password = userpsw;
    }

    public User(String userphone){
        this.user_Phone = userphone;
    }

    public User(){

    }

    public int doRegister(){
        int responseCode=0;
        String sql = "INSERT INTO socialstorydb.`user`(PASSWORD,NAME,phonenum) VALUES ("+ this.user_Password +",\""+ this.user_name+ "\","+ this.user_Phone+")";
        String sql1 = "SELECT * FROM socialstorydb.`user` WHERE phonenum = \"" + this.user_Phone +"\"";
        DButils db = new DButils();
        try {
            responseCode = db.query(sql1);
            System.out.println("我是检测手机的注册后台"+responseCode);
            if(responseCode == 1 )
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            responseCode = db.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    public int doLogin(){
        String sql = "SELECT * FROM socialstorydb.`user` WHERE phonenum = \"" + this.user_Phone +"\" AND PASSWORD = \""+ this.user_Password + "\"";
        DButils db = new DButils();
        int responseCode=0;
        try {
            responseCode = db.query(sql);
            System.out.println("我是登陆返回值"+ responseCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    public Map doGetInfo(){
        String sql = "SELECT id,NAME,phonenum,headphoto FROM socialstorydb.`user` WHERE phonenum=\""+ this.user_Phone + "\"";
        Map<String, String> map = new HashMap<String, String>();
        DButils db = new DButils();
        map =  db.getUserInfoQuerry(sql);
        return map;
    }

    //存头像地址
    public int doUploadPic(String Url) throws SQLException {
        String filename = this.user_Phone;
        DButils db = new DButils();
        int result = 0;
        String sql = "UPDATE socialstorydb.`user` SET headphoto=\""+ Url + "\" WHERE phonenum=\""+ filename +"\"";
        result = db.update(sql);
        System.out.println(result);
        return result;
    }

    public String getUser_Phone() {
        return user_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        this.user_Phone = user_Phone;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_headUrl() {
        return user_headUrl;
    }

    public void setUser_headUrl(String user_headUrl) {
        this.user_headUrl = user_headUrl;
    }
}
