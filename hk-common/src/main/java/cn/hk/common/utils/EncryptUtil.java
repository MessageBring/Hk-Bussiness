package cn.hk.common.utils;

import cn.hk.common.utils.encrypt.AESEncrypt;
import cn.hk.common.utils.encrypt.IEncrypt;
import cn.hk.common.utils.encrypt.RSAEncrypt;

public class EncryptUtil {


    /**
     * 传入需要用到的加密算法，目前只有RSA，AES和对应的密钥
     * @param encryptName
     * @param key
     * @return
     */
    public static IEncrypt getEncrypt(String encryptName,String key){
        IEncrypt encrypt;
        switch (encryptName) {
            case "RSA":
                encrypt = new RSAEncrypt(key);
                break;
            case "AES":
                encrypt = new AESEncrypt(key);
                break;
            default:
                throw new IllegalArgumentException("No such algorithm");
        }
        return encrypt;
    }
}
