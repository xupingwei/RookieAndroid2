package com.xaolaf.rookieandroid;

import java.io.Serializable;

import me.pingwei.rookielib.retrofit.Response;

/**
 * Created by xupingwei on 2016/12/30.
 */

public class LoginBean extends Response implements Serializable {

    private String token;
    private UserPass pass;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserPass getPass() {
        return pass;
    }

    public void setPass(UserPass pass) {
        this.pass = pass;
    }


}
