package cn.hk.common.utils.encrypt;

import cn.hk.common.BusinessException;
import cn.hk.common.enums.RespEnums;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncrypt implements IEncrypt{
    private final int DECRYPT_MODE = 2;

    private final int ENCRYPT_MODE = 1;

    private static final String ALGORITHM = "AES";

    private static final String CIPHER_MODE = "AES/ECB/pkcs5padding";

    private String key;
    
    public AESEncrypt(String key) {
        this.key = key;
    }
    @Override
    public String encrypt(String plainText) {
        return aes(ENCRYPT_MODE, plainText);
    }

    @Override
    public String decrypt(String cipherText) {
        return aes(DECRYPT_MODE,cipherText);
    }

    private String aes(int mode,String text) {
        try {
            SecretKey secretKey = getSecretKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(mode, secretKey);
            String result = null;
            switch (mode){
                case ENCRYPT_MODE:
                    result = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
                    break;
                case DECRYPT_MODE:
                    byte []decodeByte = Base64.getDecoder().decode(text);
                    result = new String(cipher.doFinal(decodeByte));
                    break;
                default:
                    break;
            }
            return result;
        }catch (Exception e) {
            throw new BusinessException(RespEnums.FAIL.getCode(),e.getMessage());
        }
    }

    private SecretKeySpec getSecretKey(String secretKey) throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());

        kgen.init(128, random);
        SecretKey sk = kgen.generateKey();

        byte[] enCodeFormat = sk.getEncoded();
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    public static void main(String[] args) {
        AESEncrypt encrypt = new AESEncrypt("wayhk");
        String enc = encrypt.encrypt("123676787989ghghjghjghj");
        System.out.println(enc);
        String deEnc = encrypt.decrypt(enc);
        System.out.println(deEnc);
    }
}
