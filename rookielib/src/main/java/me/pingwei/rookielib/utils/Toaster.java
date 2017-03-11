package me.pingwei.rookielib.utils;

import android.content.Context;
import android.widget.Toast;

import me.pingwei.rookielib.interfaces.ILoadingListener;


/**
 * Created by xupingwei on 2016/10/26.
 */

public class Toaster implements ILoadingListener {

    private Toast mToast;

    public Toaster(Context ctx, String msg) {
        mToast = create(ctx, msg, Toast.LENGTH_LONG);
    }

    /**
     * 创建Toast
     *
     * @param ctx
     * @param msg
     * @param length
     * @return
     */
    private static Toast create(Context ctx, String msg, int length) {
        return Toast.makeText(ctx, msg, length);
    }

    /**
     * 弹出toast
     *
     * @param ctx 上下文对象
     * @param msg 消息信息
     */
    public static void showToast(Context ctx, String msg) {
        if (ctx == null) {
            return;
        }
        showToast(ctx, msg, Toast.LENGTH_LONG);
    }

    /**
     * 弹出toast
     *
     * @param ctx    上下文对象
     * @param msg    消息信息
     * @param length toast 弹出的消息时长短
     */
    public static void showToast(Context ctx, String msg, int length) {
        create(ctx, msg, length).show();
    }

    /**
     * 弹出toast
     *
     * @param ctx   上下文对象
     * @param resId 资源文件ID
     */
    public static void showToast(Context ctx, int resId) {
        showToast(ctx, resId, Toast.LENGTH_LONG);
    }

    /**
     * 弹出toast
     *
     * @param ctx    上下文对象
     * @param resId  资源文件ID
     * @param length toast 弹出的消息时长短
     */
    public static void showToast(Context ctx, int resId, int length) {
        showToast(ctx, ctx.getString(resId), length);
    }

    @Override
    public void showLoading() {
        mToast.show();
    }

    @Override
    public void hideLoading() {
        mToast.cancel();
    }
}
