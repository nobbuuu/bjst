package com.dream.bjst.utils;


import com.tcl.base.utils.encipher.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @description: 加密解密工具
 **/
public class EncryptUtil {

    public static final String DES = "DES";
    /**DES*/
    public int keysizeDES = 0;

    /**编码格式；默认使用uft-8*/
    public String charset = "utf-8";

    //单例
    public static EncryptUtil Instance;

    private EncryptUtil(){}

    //双重锁
    public static EncryptUtil getInstance(){
        if(Instance == null){
            synchronized (EncryptUtil.class){
                if(Instance == null){
                    Instance = new EncryptUtil();
                }
            }
        }
        return Instance;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     * @param res 加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key  加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private String keyGeneratorES(String res,String algorithm,String key,int keysize,boolean isEncode){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            }else if (key==null) {
                kg.init(keysize);
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            }else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制*/
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 使用Base64进行加密
     * @param res 密文
     * @return String
     */
    public String Base64Encode(String res){
        return Base64.encode(res.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用Base64进行解密
     * @param res 密文
     * @return String
     */
    public String Base64Decode(String res) {
        return new String(Base64.decode(res));
    }

    /**
     * 使用DES加密算法进行加密（可逆）
     * @param res 需要加密的原文
     * @return String
     */
    public String DESencode(String res) {
        return keyGeneratorES(res, DES, CommonConstant.SECRET_KEY, keysizeDES, true);
    }
    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     * @param res 需要解密的密文
     * @return String
     */
    public String DESdecode(String res) {
        return keyGeneratorES(res, DES, CommonConstant.SECRET_KEY, keysizeDES, false);
    }

//如何使用加密 String password = EncryptUtil.getInstance().DESencode(chkDatabase.getPassword());
//如何使用解密 String password = EncryptUtil.getInstance().DESdecode(chkDatabase.getPassword());

}