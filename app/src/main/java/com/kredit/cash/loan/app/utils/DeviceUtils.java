package com.kredit.cash.loan.app.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.Utils;
import com.kredit.cash.loan.app.bean.AppInfoBean;
import com.kredit.cash.loan.app.bean.ContactsBean;
import com.kredit.cash.loan.app.bean.PhotoInfoBean;
import com.kredit.cash.loan.app.bean.SMSInfoBean;
import com.kredit.cash.loan.app.common.Constant;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.kredit.cash.loan.app.bean.ContactsBean;
import com.kredit.cash.loan.app.bean.SMSInfoBean;
import com.tcl.base.utils.MmkvUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    /**
     * 获取ip地址(内网)
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
     * 获取ip地址(公网) 前提是要先请求地址获取
     */
    public static String getGlobalIPAddress() {
        return MmkvUtil.INSTANCE.decodeString(Constant.IPADDRESS);
    }

    /**
     * 从本地获取相册列表
     *
     * @param pageIndex 从0开始
     * @param pageSize  页码大小
     */

    public static List<PhotoInfoBean> getLocalAlbumList(int pageIndex, int pageSize) {
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
//                String author = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.AUTHOR));
//                String dateModified = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                PhotoInfoBean bean = new PhotoInfoBean(name, "author", createDate, width, height);
                photos.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photos;
    }

    /**
     * 读取外部音频数量
     */
    public static int readExternalMusicNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取内部音频数量
     */
    public static int readInternalMusicNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取内部视频数量
     */
    public static int readInternalVideoNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Video.Media.INTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Video.Media.DISPLAY_NAME);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取外部视频数量
     */
    public static int readExternalVideoNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Video.Media.DISPLAY_NAME);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取外部图片数量
     */
    public static int readExternalImgNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA},
                MediaStore.Images.ImageColumns.MIME_TYPE + "=? or "
                        + MediaStore.Images.ImageColumns.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.ImageColumns.DATE_MODIFIED);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取内部图片数量
     */
    public static int readInternalImgNum() {
        Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA},
                MediaStore.Images.ImageColumns.MIME_TYPE + "=? or "
                        + MediaStore.Images.ImageColumns.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.ImageColumns.DATE_MODIFIED);
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 读取下载文件数量
     */
    public static int readDownloadNum() {
        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            cursor = Utils.getApp().getContentResolver().query(MediaStore.Downloads.INTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Downloads.IS_DOWNLOAD);
        }
        int num = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                num++;
            }
            cursor.close();
        }
        return num;
    }

    /**
     * 获得设备硬件标识
     *
     * @return 设备硬件标识
     */
    public static String getDeviceId() {
        StringBuilder sbDeviceId = new StringBuilder();

        //获得设备默认IMEI（>=6.0 需要ReadPhoneState权限）
        String imei = getIMEI();
        //获得AndroidId（无需权限）
        String androidid = getAndroidId();
        //获得设备序列号（无需权限）
        String serial = getSERIAL();
        //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
        String uuid = getDeviceUUID().replace("-", "");

        //追加imei
        if (imei != null && imei.length() > 0) {
            sbDeviceId.append(imei);
            sbDeviceId.append("|");
        }
        //追加androidid
        if (androidid != null && androidid.length() > 0) {
            sbDeviceId.append(androidid);
            sbDeviceId.append("|");
        }
        //追加serial
        if (serial != null && serial.length() > 0) {
            sbDeviceId.append(serial);
            sbDeviceId.append("|");
        }
        //追加硬件uuid
        if (uuid != null && uuid.length() > 0) {
            sbDeviceId.append(uuid);
        }

        //生成SHA1，统一DeviceId长度
        if (sbDeviceId.length() > 0) {
            try {
                byte[] hash = getHashByString(sbDeviceId.toString());
                String sha1 = bytesToHex(hash);
                if (sha1 != null && sha1.length() > 0) {
                    //返回最终的DeviceId
                    return sha1;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //如果以上硬件标识数据均无法获得，
        //则DeviceId默认使用系统随机数，这样保证DeviceId不为空
        return UUID.randomUUID().toString().replace("-", "");
    }

    //需要获得READ_PHONE_STATE权限，>=6.0，默认返回null
    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager)
                    Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备的AndroidId
     *
     * @return 设备的AndroidId
     */
    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(Utils.getApp().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
     *
     * @return 设备序列号
     */
    public static String getSERIAL() {
        try {
            return Build.SERIAL;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获得设备硬件uuid
     * 使用硬件信息，计算出一个随机数
     *
     * @return 设备硬件uuid
     */
    public static String getDeviceUUID() {
        try {
            String dev = "3883756" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.HARDWARE.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.SERIAL.length() % 10;
            return new UUID(dev.hashCode(),
                    Build.SERIAL.hashCode()).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 取SHA1
     *
     * @param data 数据
     * @return 对应的hash值
     */
    private static byte[] getHashByString(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(data.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            return "".getBytes();
        }
    }

    /**
     * 转16进制字符串
     *
     * @param data 数据
     * @return 16进制字符串
     */
    private static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        for (int n = 0; n < data.length; n++) {
            stmp = (Integer.toHexString(data[n] & 0xFF));
            if (stmp.length() == 1)
                sb.append("0");
            sb.append(stmp);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    //获取 GAID 必须在子线程中调用
    public static String getGAID() {
        String gaid = "";
        AdvertisingIdClient.Info adInfo = null;
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(Utils.getApp());
        } catch (IOException e) {
            // Unrecoverable error connecting to Google Play services (e.g.,
            // the old version of the service doesn't support getting AdvertisingId).
            Log.e("getGAID", "IOException");
        } catch (GooglePlayServicesNotAvailableException e) {
            // Google Play services is not available entirely.
            Log.e("getGAID", "GooglePlayServicesNotAvailableException");
        } catch (Exception e) {
            Log.e("getGAID", "Exception:" + e.toString());
            // Encountered a recoverable error connecting to Google Play services.
        }
        if (adInfo != null) {
            gaid = adInfo.getId();
            Log.w("getGAID", "gaid:" + gaid);
        }
        return gaid;
    }

    /**
     * 获取手机信号强度，需添加权限 android.permission.ACCESS_COARSE_LOCATION
     * API要求不低于17
     *
     * @return 当前手机主卡信号强度, 单位 dBm(-1是默认值，表示获取失败)
     */

    public static int getMobileDbm() {
        int dbm = -1;
        TelephonyManager tm = (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfoList;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (ActivityCompat.checkSelfPermission(Utils.getApp(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return dbm;
            } else {
                cellInfoList = tm.getAllCellInfo();
                if (null != cellInfoList) {
                    for (CellInfo cellInfo : cellInfoList) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthGsm.getDbm();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            CellSignalStrengthCdma cellSignalStrengthCdma =
                                    ((CellInfoCdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthCdma.getDbm();
                        } else if (cellInfo instanceof CellInfoWcdma) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                CellSignalStrengthWcdma cellSignalStrengthWcdma =
                                        ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                                dbm = cellSignalStrengthWcdma.getDbm();
                            }
                        } else if (cellInfo instanceof CellInfoLte) {
                            CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthLte.getDbm();
                        }
                    }
                }
            }
        }
        return dbm;
    }

    /**
     * 开机到现在的时间
     *
     * @return
     */
    public static long getSystemStartupTime() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMac() {
        String strMac = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            strMac = getLocalMacAddressFromWifiInfo(Utils.getApp());
            return strMac;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            strMac = getMacAddress(Utils.getApp());
            return strMac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("=====", "7.0以上");
            if (!TextUtils.isEmpty(getMacAddress())) {
                strMac = getMacAddress();
                return strMac;
            } else if (!TextUtils.isEmpty(getMachineHardwareAddress())) {
                strMac = getMachineHardwareAddress();
                return strMac;
            } else {
                strMac = getLocalMacAddressFromBusybox();
                return strMac;
            }
        }
        return "02:00:00:00:00:00";
    }


    /**
     * 根据wifi信息获取本地mac
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        return mac;
    }

    /**
     * android 6.0及以上、7.0以下 获取mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial;
    }

    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getMacAddress() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * android 7.0及以上 （2）扫描各个网络接口获取mac地址
     *
     */
    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }


    /**
     * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
     *
     */

    /**
     * 根据busybox获取本地Mac
     *
     * @return
     */
    public static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "network anomaly";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }

    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            while ((line = br.readLine()) != null
                    && line.contains(filter) == false) {
                result += line;
            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取手机品牌
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 当前环境的wifi数量
     */
    public static int getAroundWifiNum() {
        WifiManager mWifiManager = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = mWifiManager.getScanResults();//搜索到的设备列表
        List<ScanResult> newScanResultList = new ArrayList<>();
        for (ScanResult scanResult : scanResults) {
            int position = getItemPosition(newScanResultList, scanResult);
            if (position != -1) {
                if (newScanResultList.get(position).level < scanResult.level) {
                    newScanResultList.remove(position);
                    newScanResultList.add(position, scanResult);
                }
            } else {
                newScanResultList.add(scanResult);
            }
        }
        return newScanResultList.size();
    }

    /**
     * 返回item在list中的坐标
     */
    private static int getItemPosition(List<ScanResult> list, ScanResult item) {
        for (int i = 0; i < list.size(); i++) {
            if (item.SSID.equals(list.get(i).SSID)) {
                return i;
            }
        }
        return -1;
    }

    public static long getSDTotalSize() {
        /*获取存储卡路径*/
        File sdcardDir = Environment.getExternalStorageDirectory();
        /*StatFs 看文件系统空间使用情况*/
        StatFs statFs = new StatFs(sdcardDir.getPath());
        long blockSize = statFs.getBlockSizeLong();

        long totalSize = statFs.getBlockCountLong();

        return blockSize * totalSize;
    }

    /**
     * 获得sd卡剩余容量
     *
     * @return
     */
    public static long getSdAvaliableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSizeLong();
        long availableBlocks = statFs.getAvailableBlocksLong();
        return blockSize * availableBlocks;
    }

    /**
     * 获得机身内存大小
     *
     * @return
     */
    public static long getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSizeLong();
        long tatalBlocks = statFs.getBlockCountLong();
        return blockSize * tatalBlocks;
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    public static long getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSizeLong();
        long availableBlocks = statFs.getAvailableBlocksLong();
        return blockSize * availableBlocks;
    }

    public static List<SMSInfoBean> getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";
        StringBuilder smsBuilder = new StringBuilder();
        List<SMSInfoBean> smsList = new ArrayList<>();
        try {
            ContentResolver cr = Utils.getApp().getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type", "read", "status"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");
            if (cur != null && cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");
                int readColumn = cur.getColumnIndex("read");
                int statusColumn = cur.getColumnIndex("status");
                do {
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);
                    int typeId = cur.getInt(typeColumn);
                    SMSInfoBean smsInfoBean = new SMSInfoBean();
                    smsInfoBean.set808D8491(typeId);
                    smsInfoBean.set849C9B9A91(phoneNumber);
                    smsInfoBean.set969B908D(smsbody);
                    smsInfoBean.set86919590(readColumn);
                    smsInfoBean.set8791919A(readColumn);
                    smsInfoBean.set878095808187(statusColumn);
                    smsInfoBean.set95909086918787(name);
                    smsInfoBean.set87919A90BB86909186A69197919D8291A09D9991(date);
                    smsList.add(smsInfoBean);
                } while (cur.moveToNext());
            }
        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }
        return smsList;
    }

    //获取所有联系人
    public static List<ContactsBean> getContacts() {
        List<ContactsBean> contacts = new ArrayList<>();
        ContentResolver cr = Utils.getApp().getContentResolver();
        //联系人提供者的uri
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String num = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String updatedTimestamp = ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP;
        Cursor cursor = cr.query(phoneUri, new String[]{num, name, updatedTimestamp}, null, null, null);
        while (cursor.moveToNext()) {
            ContactsBean phoneDto = new ContactsBean();
            phoneDto.set879B81869791(cursor.getString(cursor.getColumnIndex(name)));
            phoneDto.set849C9B9A91(cursor.getString(cursor.getColumnIndex(num)));
            phoneDto.set818490958091A09D9991(cursor.getString(cursor.getColumnIndex(updatedTimestamp)));
            contacts.add(phoneDto);
        }
        return contacts;
    }

    /**
     * 获取手机已安装应用列表
     */
    public static List<AppInfoBean> getAllAppInfo() {
        ArrayList<AppInfoBean> appBeanList = new ArrayList<>();
        PackageManager packageManager = Utils.getApp().getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (PackageInfo t : list) {
            AppInfoBean bean = new AppInfoBean();
            ApplicationInfo p = t.applicationInfo;
            bean.set958484BA959991(p.loadLabel(packageManager).toString());
            bean.set9298959387(p.flags);
            bean.set8495979FB59391(p.packageName);
            bean.set829186879D9B9AB79B9091(t.versionCode);
            bean.set829186879D9B9ABA959991(t.versionName);
            // 判断是否是属于系统的apk
            if ((p.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                bean.set958484A08D8491(1);
            } else {
                bean.set958484A08D8491(0);
            }
            appBeanList.add(bean);
        }
        return appBeanList;
    }
}
