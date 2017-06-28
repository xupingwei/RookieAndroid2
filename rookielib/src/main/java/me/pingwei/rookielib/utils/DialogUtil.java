package me.pingwei.rookielib.utils;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ChuyaoShi on 16/12/23.
 */

public class DialogUtil {

    private static final int DEFAULT_WIDTH = 480;
    private static final int DEFAULT_HEIGHT = 590;
    private static final int DEFAULT_GRAVITY = Gravity.CENTER_VERTICAL | Gravity.RIGHT;


    public static void initLayoutParams(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DEFAULT_WIDTH;
        params.height = DEFAULT_HEIGHT;
        params.gravity = DEFAULT_GRAVITY;
        window.setAttributes(params);
    }

    public static void initLayoutParams(Dialog dialog, int width, int height) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public static void initLayoutParams(Dialog dialog, int width, int height, int gravity) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
    }

}
