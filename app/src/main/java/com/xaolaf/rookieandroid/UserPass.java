package com.xaolaf.rookieandroid;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by xupingwei on 2016/12/30.
 */

public class UserPass extends DataSupport implements Serializable {


    /**
     * id : 268479923
     * name : xupingwei
     * email : xupingwei@cqcye.com
     * create_time : 1483103179
     * lastlogintime : 1483103179
     * password : dd894c80cad16cf8
     * status : 1
     * role : 1
     * huser_ext : {"action":"post","application":"71940000-c6af-11e6-815a-3d5ce3c345dd","path":"\/users","uri":"https:\/\/a1.easemob.com\/1171161220178974\/queble\/users","entities":[{"uuid":"c1740f0a-ce90-11e6-8e91-4b06bddfb79e","type":"user","created":1483103179760,"modified":1483103179760,"username":"268479923","activated":true,"nickname":"xupingwei"}],"timestamp":1483103179760,"duration":31,"organization":"1171161220178974","applicationName":"queble"}
     */
    private long id;
    private String name;
    private String email;
    private String create_time;
    private String lastlogintime;
    private String password;
    private int status;
    private int role;
    private String huser_ext;
    private String userLocalPath;
    private String image;


    public String getUserLocalPath() {
        return userLocalPath;
    }

    public void setUserLocalPath(String userLocalPath) {
        this.userLocalPath = userLocalPath;
    }


    public UserPass(long id) {
        this.id = id;
    }

    public UserPass() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHuser_ext() {
        return huser_ext;
    }

    public void setHuser_ext(String huser_ext) {
        this.huser_ext = huser_ext;
    }
}
