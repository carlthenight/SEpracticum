package com.Model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public Map doQuery(String sql)  {
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
    public int query(String sql) throws SQLException {
        int st = 1;
        System.out.println(sql);
        connection = this.getConnection();
        System.out.print("链接数据库OK");
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet.next())
                st=0;
            return st;
        } catch (SQLException e) {
            e.printStackTrace();
            return st;
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
                return flag;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }finally {
                this.releaseResources(resultSet,statement,connection);
            }
        } else {
            return -1;
        }
    }
}
