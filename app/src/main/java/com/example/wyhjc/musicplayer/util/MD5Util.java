package com.example.wyhjc.musicplayer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * 将源字符串通过MD5进行加密为字节数组
     * @param source
     * @return
     */
    public static byte[] encodeToBytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();// 重置
            md.update(source.getBytes("UTF-8"));// 添加需要加密的源
            result = md.digest();// 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将源字符串通过MD5加密成32位16进制数
     * @param source
     * @return
     */
    public static String encodeToHex(String source) {
        byte[] data = encodeToBytes(source);// 先加密为字节数组
        StringBuffer hexSb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1) {
                hexSb.append("0");
            }
            hexSb.append(hex);
        }
        return hexSb.toString();
    }
}
