package me.pingwei.rookielib.retrofit;


import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.pingwei.rookielib.utils.LoggerUtils;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @param <T>
 */
public abstract class ApiCallback<T> extends Subscriber<T> {

    private Context mContext;
    private SweetAlertDialog dialog;
    private String msg;
    private boolean isShowDialog = true;

//    protected boolean showDialog() {
//        return true;
//    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public ApiCallback(Context context, String msg, boolean isShowDialog) {
        this.mContext = context;
        this.msg = msg;
        this.isShowDialog = isShowDialog;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public ApiCallback(Context context, String msg) {
        this(context, msg, true);
    }

    /**
     * @param context context
     */
    public ApiCallback(Context context) {
        this(context, "请稍后...", true);
    }

    /**
     * @param context context
     */
    public ApiCallback(Context context, boolean isShowDialog) {
        this(context, "请稍后...", isShowDialog);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isShowDialog) {
            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            //点击取消的时候取消订阅
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!isUnsubscribed()) {
                        unsubscribe();
                    }
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            LoggerUtils.d("code = " + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(e.getMessage());
        }
        if (isShowDialog)
            dialog.dismiss();
    }


    @Override
    public void onNext(T model) {
        if (isShowDialog)
            dialog.dismiss();
        LoggerUtils.json(new Gson().toJson(model));
        onSuccess(model);
    }

    @Override
    public void onCompleted() {
        if (isShowDialog)
            dialog.dismiss();
    }


    public abstract void onSuccess(T model);

    public abstract void onFailure(String msg);
}
