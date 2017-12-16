package com.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DButils {
    private static String driverName;
    private static String url;
    private static String user;
    private static String password;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    /*
     * 静态代码块，类初始化时加载数据库驱动
     */
    static {
        try {
            // 加载dbinfo.properties配置文件
            InputStream in = DButils.class.getClassLoader()
                    .getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(in);
            // 获取驱动名称、url、用户名以及密码
            driverName = properties.getProperty("driverName");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            // 加载驱动
            Class.forName(driverName);
            System.out.print("数据库配置加载成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
     * 获取连接
     */
    public  Connection getConnection() throws SQLException {
       return  DriverManager.getConnection(url, user, password);
    }

    /*
     * 释放资源
     */
    public static void releaseResources(ResultSet resultSet,
                                        Statement statement, Connection connection) {
        try {
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            connection = null;
        }
        try {
            if(resultSet!=null)
                resultSet.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            resultSet = null;
        }
        try {
            statement.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            statement = null;
        }
    }

    public List getWorksDetail(String sql){
        List<Object> list = new ArrayList<Object>();
        try{
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Map<String,String> map = new HashMap<String,String>();
                String g_id = resultSet.getString("g_id");
                String picture_url = resultSet.getString("picture_url");
                String picture_content = resultSet.getString("picture_content");
                map.put("g_id", g_id);
                map.put("picture_url", picture_url);
                map.put("picture_content", picture_content);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.releaseResources(resultSet,statement,connection);
        }
        return list;
    }

    public List getWorksPid(String sql) {
        List <Object> list =new ArrayList<Object>();////
        try{
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Map <String ,String >map1 = new <String ,String > HashMap();
                String p_id = resultSet.getString("p_id");
                System.out.println("我是获取PID里面的"+ p_id);
                String p_title = resultSet.getString("p_title");
                map1.put("p_id", p_id);
                map1.put("p_title", p_title);
                list.add(map1);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
            return list;
        }
    }

    public Map getUserInfoQuerry(String sql)  {
        Map map = new HashMap();
        try {
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String username = resultSet.getString("NAME");
                String userphone = resultSet.getString("phonenum");
                String headUrl = resultSet.getString("headphoto");
                String userID = resultSet.getString("id");
                map.put("uid", userID);
                map.put("uname", username);
                map.put("uphone", userphone);
                map.put("headUrl", headUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.releaseResources(resultSet,statement,connection);
        }
        return map;
    }
    //查询
    public int query(String sql) throws SQLException {
        int responseCode = 0;
        System.out.println(sql);
        connection = this.getConnection();
        System.out.print("链接数据库OK");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                responseCode=1;
            return responseCode;
        } catch (SQLException e) {
            e.printStackTrace();
            return responseCode;
        }finally {
            this.releaseResources(resultSet,statement,connection);
        }
    }

    // 修改
    public int update(String sql) throws SQLException {
        connection = this.getConnection();
        System.out.print("updata链接数据库OK");
        System.out.println(sql);
        if (connection != null) {
            try {
                statement = connection.createStatement();
                int flag = statement.executeUpdate(sql);
                System.out.println("我是flag哟"+ flag);
                return flag;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }finally {
                this.releaseResources(resultSet,statement,connection);
            }
        }else {
            return 0;
        }
    }

    public List TitleToSearch(String sql) throws SQLException {
        List list = new ArrayList();
        connection = this.getConnection();
        connection = this.getConnection();
        System.out.print("链接数据库OK");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String,String> map = new HashMap<String,String>();
                String p_id = resultSet.getString("p_id");
                String uid = resultSet.getString("id");
                String p_title = resultSet.getString("p_title");
                map.put("p_id", p_id);
                map.put("p_title", p_title);
                map.put("uid", uid);
                //String result = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.releaseResources(resultSet,statement,connection);
        }
        return list ;
    }

    public List getRotationPic(String sql){
        List list = new ArrayList();
        try{
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Map<String, String> map1 = new HashMap<String, String>();
                String rpid = resultSet.getString("rp_id");
                String rpurl = resultSet.getString("rp_url");
                String rptitle = resultSet.getString("rp_title");
                String rpcontent = resultSet.getString("rp_content");
                map1.put("rp_id", rpid);
                map1.put("rp_url", rpurl);
                map1.put("rp_title", rptitle);
                map1.put("rp_content", rpcontent);
                list.add(map1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.releaseResources(resultSet,statement,connection);
        }
        return list;

    }

    public Map getWorksInfo1(String sql) {
        Map <String ,String >map1 = new <String ,String > HashMap();
        try{
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String p_id = resultSet.getString("p_id");
                String uid = resultSet.getString("id");
                String p_title = resultSet.getString("p_title");
                map1.put("uid",uid);
                map1.put("p_id", p_id);
                map1.put("p_title", p_title);
            }
            return map1;
        }catch (SQLException e){
            e.printStackTrace();
            return map1;
        }
    }

    public List getWorksInfo2(String sql) {
        List list = new ArrayList();
        try{
            connection = this.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Map <String ,String >map1 = new <String ,String > HashMap();
                String p_id = resultSet.getString("p_id");
                String uid = resultSet.getString("id");
                String p_title = resultSet.getString("p_title");
                map1.put("uid",uid);
                map1.put("p_id", p_id);
                map1.put("p_title", p_title);
                list.add(map1);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
            return list;
        }
    }
}



