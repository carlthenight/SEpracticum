package com.Model;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

public class Works {
    private String uid;
    private String w_Title;
    private String w_id;
    private String g_id;
    private String g_content;

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public void setG_content(String g_content) {
        this.g_content = g_content;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setW_Title(String w_Title) {
        this.w_Title = w_Title;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public Works(String userid,String worksid){
        this.uid = userid;
        this.w_id = worksid;
    }

    public Works(String userid){
        this.uid =userid;
    }

    public Works(){

    }

    public int deleteWorks() throws SQLException {
        int responseCode ;
        String sql = "DELETE  FROM socialstorydb.`product` WHERE p_id=\""+ this.w_id+ "\"";
        DButils db = new DButils();
        responseCode = db.update(sql);
        return responseCode;
    }

    public List searchWorks() throws SQLException {
        List list = new ArrayList();
        String sql = "SELECT * FROM socialstorydb.`product` WHERE p_title LIKE \"%%"+ this.w_Title+"%%\"";
        DButils db = new DButils();
        list  = db.TitleToSearch(sql);
        return list;
    }
    public Map downloadWorks(){
        List list = new ArrayList();
        Map map = new HashMap();
        DButils db = new DButils();
        String sql1 = "SELECT * FROM socialstorydb.`product` WHERE p_id=\""+ this.w_id +"\"";
        map = db.getWorksInfo1(sql1);
        String sql = "SELECT * FROM socialstorydb.`group` WHERE p_id=\""+ this.w_id +"\"";
        list = db.getWorksDetail(sql);
        if(list.isEmpty()){
            map.put("",list);
        }else {
            map.put("story",list);
        }
        return map;
    }

    public List getWorks() throws SQLException {
        List <Object> list = new ArrayList<Object>();////
        String sql = "SELECT * FROM socialstorydb.`product` WHERE id=\""+ this.uid + "\"";
        DButils db = new DButils();
        list = db.getWorksPid(sql);
        System.out.println("我是getWorks"+ list);
        return list;
    }

    public List<Object> getDetails(List list){
        List<Object> list1 = new ArrayList<>() ;
        List<Object> list2 = new ArrayList<>();
        DButils db = new DButils();
       for(int i=0;i<list.size();i++){
           Map  map1=(Map)list.get(i);
           Map maps = new HashMap();
           Iterator iterator = map1.keySet().iterator();
           int j =1;
           while (iterator.hasNext())
           {
               j++;
               iterator.next();
               iterator.next();
               String key1 = "p_title";
               String key2 = "p_id";
               Object object1 = map1.get(key1);
               Object object2 = map1.get(key2);
               maps.put(key1,object1);
               maps.put(key2,object2);
               String sql = "SELECT * FROM socialstorydb.`group` WHERE p_id=\"" + object2+ "\"";
               list1 = db.getWorksDetail(sql);
               maps.put("story", list1);
               //String details = JSON.toJSONString(maps);
               System.out.println("我是details里的map哟"+maps);
               list2.add(maps);
           }
       }
        return  list2;
    }


    public String upLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = null;
        Map<String,String> map = new <String,String> HashMap();
        request.setCharacterEncoding("UTF-8");
        // 创建文件上传核心类
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置编码
        sfu.setHeaderEncoding("UTF-8");
        try{
            //散列做w_id
            Date date = new Date();
            String salt = date.toString();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-1");
                byte[] digest = md.digest(salt.toString().getBytes());
                //对接后的字符串进行sha1加密
                StringBuffer hexString = new StringBuffer();
                //字节数组转换为 十六进制 数 的 字符串
                for (int i = 0; i < digest.length; i++) {
                    String shaHex = Integer.toHexString(digest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexString.append(0);
                    }
                    hexString.append(shaHex);
                }
                this.setW_id(hexString.toString());
                //签名密文字符串
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            // 处理表单请求
            List<FileItem> itemList = sfu.parseRequest(request);
            int responseCode = 0;
            for (FileItem fileItem : itemList) {
                // 对应表单中的控件的name
                Long size = fileItem.getSize();
                if(size==0){return "{\"result\":\"0\"}";}
                String savePath = "/usr/local/test/story/" + this.w_id;
                File saveDir = new File(savePath);
                if(!saveDir.exists()){
                    saveDir.mkdir();
                }
                String fieldName = fileItem.getFieldName();
                System.out.println("控件名称：" + fieldName);
                // 如果是普通表单控件
                if(fileItem.isFormField()){
                    String value = fileItem.getString();
                    //重新编码,解决乱码
                    value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    System.out.println("普通内容：" + "=" + value);
                    switch (fieldName){
                        case "uid":this.setUid(value);
                            break;
                        case "title":this.setW_Title(value);
                            break;
                        case"g_id":this.setG_id(value);
                            break;
                        case "g_content":this .setG_content(value);
                            break;
                        default:
                            break;
                    }
                    if(fieldName.equals("g_id" ) || fieldName.equals("file")){

                    }else if(fieldName.equals("title")){
                        //传输至 作品 数据库
                        DButils db = new DButils();
                        String sql = "INSERT INTO socialstorydb.`product`(p_id,p_title,id) VALUES (\""+ this.w_id +"\",\""+ this.w_Title+ "\",\""+ this.uid+"\")";
                        //System.out.println(sql);
                        responseCode = db.update(sql);
                        System.out.println("这是储存works的标题和作者id还有自生成id哟 " + responseCode);
                    }
                    // 上传文件
                }else{
                    // 获得文件名
                    String fileName = fileItem.getName();
                    String fileEx = fileName.substring(fileName.length()-4);
                    String lastfileName  = this.g_id + fileEx ;
                    System.out.println("修改后的文件名："+lastfileName );
                    //将文件信息保存到数据库
                    DButils db = new DButils();
                    String storyPicPath = "/storyPic/"+ this.w_id;
                    String sql = "INSERT INTO socialstorydb.`group` (g_id,picture_url,picture_content,p_id) VALUE (\""+ this.g_id +"\",\""+ storyPicPath + "/"+ lastfileName+"\",\""+ this.g_content+"\",\""+ this.w_id+ "\")";
                    System.out.println(sql);
                    responseCode = db.update(sql);
                    System.out.println("这是储存works的作品顺序还有作品你内容及地址哟 "+ responseCode);
                    //将文件保存到指定的路径
                    File file = new File(savePath,lastfileName);
                    fileItem.write(file);
                    System.out.println("msg : 上传成功！");
                }
            }
            map.put("result",String.valueOf(responseCode));
            result = JSON.toJSONString(map);
        } catch(FileUploadException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
