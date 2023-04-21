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

    public static void main(String[] args) {
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2aESUYistBiZOQ7hwONJlt5vcvz7ihwWPmFTzMqmHlDJWsCksN/N8IwM9LYVcFqJMX1JWDIm22py5F9w4WvAPk9qSIeBXmD3NXc7DnzmmzxpBEAJ+OVsofjlphm1SdsbCa6rxBNRW/6tjMj380jHUYO2T1BER2uz2ZIUb8CU2ZdR0dDO1LIc+QEyPIwdsw3ifE2olSaqhPBXAd7dPi8LhEaEZ5gGPzYsUFx6cBPUc8+67Iap7uHHDWpEenBfVMIPDkA54aJeTK/XCGiVDUHk7vTDl2eAq3AspTzwx2tL8f5Pg7iUCTioqIPZm+HNMjwPJ8BdWLdRprMz91iXqWwdQQIDAQAB";
        System.out.println(EncryptUtil.getEncrypt("RSA",key).encrypt("34343"));
        System.out.println(EncryptUtil.getEncrypt("AES","hhhh").encrypt("123"));
    }
}
