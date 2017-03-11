package me.pingwei.rookielib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 获取网络视频的第一帧图片
 */
public class VideoImageUtil {
    /**
     * 获取网络视频的第一帧缩略图
     * @param url
     * @return
     */

    public static Bitmap getVideoImage(Context context, String url){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //获取网络视频
        retriever.setDataSource(url, new HashMap<String, String>());
        //获取本地视频
        //retriever.setDataSource(url);
        Bitmap bitmap = retriever.getFrameAtTime();
        String videoName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        File fileVideoImage = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + videoName + ".png");
        try {
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(fileVideoImage);     //new File(context.getExternalCacheDir().getAbsolutePath() + "/" + videoName + ".png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, outStream);
            outStream.close();
            retriever.release();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 获取网络视频的第一帧缩略图
     * @param url
     * @return
     */

    public static File getVideoImageFile(Context context, String url){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //获取网络视频
        retriever.setDataSource(url, new HashMap<String, String>());
        //获取本地视频
        //retriever.setDataSource(url);
        Bitmap bitmap = retriever.getFrameAtTime();
        String videoName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        File fileVideoImage = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + videoName + ".png");
        try {
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(fileVideoImage);     //new File(context.getExternalCacheDir().getAbsolutePath() + "/" + videoName + ".png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, outStream);
            outStream.close();
            retriever.release();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileVideoImage;
    }
}
