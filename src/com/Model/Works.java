package com.Model;

import java.sql.SQLException;
import java.util.HashMap;
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
        //增加作品INSERT INTO socialstorydb.`product` ( p_title,id ) VALUE("nihao2","1")
    public Map getWorks() throws SQLException {
        Map <String, String> map = new <String, String> HashMap();////
        String sql = "SELECT * FROM socialstorydb.`product` WHERE id=\""+ this.uid + "\"";
        DButils db = new DButils();
        map = db.getWorks(sql);
        return map;
    }
}
