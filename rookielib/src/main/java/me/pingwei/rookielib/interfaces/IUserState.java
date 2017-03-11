package me.pingwei.rookielib.interfaces;


/**
 * Created by xupingwei on 2016/10/26.
 */

public interface IUserState<T> {

    /**
     * 判断用户是否登录
     *
     * @return true:登录  false:没有登录
     */
    public boolean isLogined();

    /**
     * 获取用户的token
     *
     * @return
     */
    public String getToken();

    /**
     * 获取用户的个人信息
     * 一般用户的基本数据会保存在本地
     *
     * @return
     */
    public T getUserInfo();

    /**
     * 更新用户信息
     *
     * @param t
     */
    public void setUserInfo(T t);
}
