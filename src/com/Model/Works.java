package com.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Works {
    private String uid;
    private String w_Title;
    private String w_id;

    public Works(String userid,String worksid){
        this.uid = userid;
        this.w_id = worksid;
    }

    public Works(String userid){
        this.uid =userid;
    }
    public Works(){

    }

    public List downloadWorks(){
        List list = new ArrayList();
        DButils db = new DButils();
        String sql = "SELECT * FROM socialstorydb.`group` WHERE p_id=\""+ this.w_id +"\"";
        list = db.getWorksDetail(sql);
        return list;
    }
        //增加作品INSERT INTO socialstorydb.`product` ( p_title,id ) VALUE("nihao2","1")
    public List getWorks() throws SQLException {
        List <Object> list = new ArrayList<Object>();////
        String sql = "SELECT * FROM socialstorydb.`product` WHERE id=\""+ this.uid + "\"";
        DButils db = new DButils();
        list = db.getWorksTitle(sql);
        System.out.println("我是getWorks"+ list);
        return list;
    }
}
