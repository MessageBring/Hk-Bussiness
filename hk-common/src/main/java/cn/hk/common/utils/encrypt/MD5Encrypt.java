package cn.hk.common.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {

    private static char []hexDigests = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static String encrypt(String str){
        byte []strBytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            return byte2Hex(messageDigest.digest(strBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byte2Hex(byte []bytes){
        int len = bytes.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<len;i++) {
            byte temp = bytes[i];
            stringBuilder.append(hexDigests[temp >>> 4 & 0xf]);
            stringBuilder.append(hexDigests[temp & 0xf]);
        }
        return stringBuilder.toString();
    }
}
