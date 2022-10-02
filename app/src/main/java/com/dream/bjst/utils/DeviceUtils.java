package com.dream.bjst.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.Utils;
import com.dream.bjst.bean.PhotoInfoBean;
import com.tcl.base.kt.StringExtKt;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    /**
     * 获取ip地址
     */
    public static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {

                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // return inetAddress.getAddress().toString();
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("BaseScanTvDeviceClient", "获取本机IP false =" + ex.toString());
        }
        return null;
    }

    /**
     * 从本地获取相册列表
     *
     * @param pageIndex 从0开始
     * @param pageSize  页码大小
     */

    public static String getLocalAlbumList(int pageIndex, int pageSize) {
        List<PhotoInfoBean> photos = new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        try {
            Cursor mCursor = Utils.getApp().getContentResolver().query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=? ",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED + " limit " + (pageIndex * pageSize) + "," + pageSize);

            while (mCursor.moveToNext()) {
                String createDate = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                String height = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                String width = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
                String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //String author = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.AUTHOR));
                PhotoInfoBean bean = new PhotoInfoBean(name,"author",createDate,width,height);
                photos.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringExtKt.GZIPCompress(GsonUtils.toJson(photos));
    }

}
