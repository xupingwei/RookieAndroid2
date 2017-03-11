package me.pingwei.rookielib.retrofit;

/**
 * Created by xupingwei on 2016/1/11.
 */
public class Response<T> {

    private int flag;
    private String msg;
    private T data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{flag: " + flag + "\n" +
                "msg: " + msg + "\n" +
                "data: " + data + "\n" +
                "}";
    }


    public boolean success() {
        return flag == 0;
    }
}
