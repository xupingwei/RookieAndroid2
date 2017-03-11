package me.pingwei.rookielib.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import me.pingwei.rookielib.R;


/**
 * Created by xupingwei on 2015/5/9.
 */
public class DialogManager {


    private static Dialog mDialog = null;

    /**
     * 提示对话框
     *
     * @param title
     * @param msg
     */
    public static void showTipDialog(
            Context mContext,
            String title,
            String msg,
            final OnConfirmListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.Ok();
            }
        });
        builder.show();
    }

    /**
     * 确认对话框
     *
     * @param title
     * @param msg
     */
    public static void showConfirmDialog(
            Context mContext,
            String title,
            String msg,
            String positiveString,
            final OnConfirmListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveString, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.Ok();
            }
        });
        builder.show();
    }

    /**
     * 确认对话框
     *
     * @param title
     * @param msg
     */
    public static void showConfirmDialog(
            Context mContext,
            String title,
            String msg,
            final OnConfirmListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("呼叫", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.Ok();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.cancel();
            }
        });
        builder.show();
    }

    /**
     * 确认对话框
     *
     * @param title
     * @param msg
     */
    public static void showConfirmDialog(
            Context mContext,
            String title,
            String msg,
            String yes,
            String no,
            final OnConfirmListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.Ok();
            }
        });
        builder.setNegativeButton(no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.cancel();
            }
        });
        builder.show();
    }

    public interface OnConfirmListener {
        void Ok();

        void cancel();
    }


    /**
     * 显示正在加载数据
     *
     * @param context
     */
    public static void showLoading(Context context) {
        mDialog = new Dialog(context, R.style.progress_dialog);
        mDialog.setContentView(R.layout.loading_dialog);
        mDialog.setCancelable(true);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView msg = (TextView) mDialog.findViewById(R.id.id_tv_loadingmsg);
        mDialog.show();
    }

    /**
     * 关闭Dialog对话框
     */
    public static void dimissDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = null;
    }
}
