package me.pingwei.rookielib.retrofit;


import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.pingwei.rookielib.utils.LoggerUtils;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @param <T>
 */
public abstract class ApiCallback<T> extends Subscriber<T> {

    protected Context mContext;
    private SweetAlertDialog dialog;
    private String msg;
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private boolean isShowDialog = true;


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
            LoggerUtils.e("code = " + code);
            switch (code) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    msg = "服务器异常，请稍后再试";
                    break;
            }
            onFailure(-200, msg);
        } else if (e instanceof ConnectException) {
            msg = "网络连接失败,请检查网络";
            onFailure(-200, msg);
        } else if (e instanceof SocketTimeoutException) {
            msg = "网络连接超时，请重试";
            onFailure(-200, msg);
        } else {
            onFailure(-100, e.getMessage());
        }
        if (isShowDialog)
            dialog.dismiss();
    }


    @Override
    public void onNext(T model) {
        if (isShowDialog)
            dialog.dismiss();
        LoggerUtils.json(new Gson().toJson(model));
//        onSuccess(model);
    }

    @Override
    public void onCompleted() {
        if (isShowDialog)
            dialog.dismiss();
    }


    public abstract void onSuccess(T model);

    public abstract void onFailure(int errorCode, String msg);
}
