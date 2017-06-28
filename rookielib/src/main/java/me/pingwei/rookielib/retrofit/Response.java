package me.pingwei.rookielib.retrofit;

/**
 * Created by xupingwei on 2016/1/11.
 */
public class Response {

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{flag: " + status + "\n" +
                "msg: " + msg + "\n" +
                "}";
    }


    public boolean success() {
        return status == 1 || status == 80102003 || status == 9001;
    }
}
