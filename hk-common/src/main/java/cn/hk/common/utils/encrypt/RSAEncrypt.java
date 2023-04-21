package cn.hk.common.utils.encrypt;

import cn.hk.common.BusinessException;
import cn.hk.common.enums.RespEnums;
import cn.hk.common.utils.StringUtil;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncrypt implements IEncrypt{
    private static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "public";

    private static final String PRIVATE_KEY = "private";

    private static final String CIPHER_MODE = "RSA/ECB/PKCS1Padding";

    private byte []key;

    /**
     * 传入加密或者解密用到的密钥，创建加密或解密用的RSAEncrypt对象
     * @param key
     */
    public RSAEncrypt(String key) {
        if (StringUtil.isEmpty(key)){
            throw new IllegalArgumentException("Encrypt key can't be empty");
        }
        this.key = Base64.getDecoder().decode(key);
    }

    /**
     * 传入明文字符传，使用公钥加密
     * @param plainText
     * @return
     */
    @Override
    public String encrypt(String plainText) {
        try {
            //根据公钥信息创建公钥对象
            Key key = getKey(PUBLIC_KEY);
            //获取RSA加密算法实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new BusinessException(RespEnums.FAIL.getCode(),e.getMessage());
        }
    }

    /**
     * 传入密文，使用私钥解密
     * @param cipherText
     * @return
     */
    @Override
    public String decrypt(String cipherText) {
        try {
            byte []decryptByte = Base64.getDecoder().decode(cipherText);
            Key key = getKey(PRIVATE_KEY);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE,key);
            return new String(cipher.doFinal(decryptByte));
        } catch (Exception e) {
            throw new BusinessException(RespEnums.FAIL.getCode(),e.getMessage());
        }
    }

    private Key getKey(String mode) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key keys = null;
        switch (mode){
            case PRIVATE_KEY:
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
                keys = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
                break;
            case PUBLIC_KEY:
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
                keys = keyFactory.generatePublic(x509EncodedKeySpec);
                break;
            default:
                break;
        }
        return keys;
    }

    public static void main(String[] args) {
        RSAEncrypt encrypt = new RSAEncrypt("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2aESUYistBiZOQ7hwONJlt5vcvz7ihwWPmFTzMqmHlDJWsCksN/N8IwM9LYVcFqJMX1JWDIm22py5F9w4WvAPk9qSIeBXmD3NXc7DnzmmzxpBEAJ+OVsofjlphm1SdsbCa6rxBNRW/6tjMj380jHUYO2T1BER2uz2ZIUb8CU2ZdR0dDO1LIc+QEyPIwdsw3ifE2olSaqhPBXAd7dPi8LhEaEZ5gGPzYsUFx6cBPUc8+67Iap7uHHDWpEenBfVMIPDkA54aJeTK/XCGiVDUHk7vTDl2eAq3AspTzwx2tL8f5Pg7iUCTioqIPZm+HNMjwPJ8BdWLdRprMz91iXqWwdQQIDAQAB");
        String enResult = encrypt.encrypt("hdfjhfjkdshfjkdhsfjdks");
        System.out.println(enResult);

        RSAEncrypt decrypt = new RSAEncrypt("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDZoRJRiKy0GJk5DuHA40mW3m9y/PuKHBY+YVPMyqYeUMlawKSw383wjAz0thVwWokxfUlYMibbanLkX3Dha8A+T2pIh4FeYPc1dzsOfOabPGkEQAn45Wyh+OWmGbVJ2xsJrqvEE1Fb/q2MyPfzSMdRg7ZPUERHa7PZkhRvwJTZl1HR0M7Ushz5ATI8jB2zDeJ8TaiVJqqE8FcB3t0+LwuERoRnmAY/NixQXHpwE9Rzz7rshqnu4ccNakR6cF9Uwg8OQDnhol5Mr9cIaJUNQeTu9MOXZ4CrcCylPPDHa0vx/k+DuJQJOKiog9mb4c0yPA8nwF1Yt1GmszP3WJepbB1BAgMBAAECggEBAKQFClpa6Na/J1PijHCknITRVivS0P2RF46iKddR8AsvFeKJCFaqBntCAk24ocXQ5k9fPpTzD2gU/UxzRg8O94++qLrLLcxkxkwMdX0A04ix6fAjF6D3wHz+qo4aGBD2ypTsiVY4vPvZoRFXMdCwzE/6Nb/nHd66JI1Y1IA5I7YtOtlBphAEiLDfxQ5BYDZbhqoIWoQE+30jjXjBG9lCZBQV9rQSEdgmXTfiuFstQRRydhzGugWskUMb6g3pP69sFk31QkkqoFVDUdx+fpIzmu7dzUr5a11VLrKogGUIr/tm/QPWT5Yl2HIh3dVwvr6FvEhCvUfJIvdom6696F0ouJUCgYEA7OU9azgCOIZfkKLjv+iXEkzxenmoZSbUSDiu6qxxsqSiI/v2rzMvi00h4cmGM/Ewt6ucWyNWS/8wCWEBcIQZERXdZbF7Z/gG9AoZA5yvwiR9TgT3Tv+opv+168taCs6cZLae0MWAIJ2xEBqo5oHuebOLI3n1uvgXT682PB2/OgsCgYEA6y4TLXPHxts7DnJVJvOndcLIcXvCG4pzA32fW0HX+v4t/U1VAaRElr/HV6mY/ecgIxihFvfAieAOnaCoyrplPgqR62vBmOYVeoWxKYTAkalWunGfGla64XklIR60VcAOoXQRFZfFH3IkrsEOq3OibeHiMrMOjRNGGoJEmcF/4WMCgYEAwCcmDZ0tt/nO9Hwb7qNUPwTobqbV386CLF7GCxZ7VyNRXYzYSL5lF0ImdIXJdMJz+NebiRV9v0EsDr9/smuEBabNodo3tlqqETNLnPFwYzLD7Q/8Jjkb7PHH945H6DI1C0BSTBeXWV1vrRdi3y6PaGopN0qK9vCbwAfoHxUl8ocCgYEA6WOqKHs0EQZTjzyrgKJg157b6LMWqNo21Yn/cULgofdJkI0EBUMNxkTAASEP8TlDTVEN3Is2Ch3Oo2Ni3v0np/A5a1Zeo+wB+LtgW4Gg0W0p4pELgcn8X/InhLzB3i9g1Z0nrzQUreZKvRpeAowY788w+Xt0v1AXTAtnJOdAj10CgYBgWJmxY24mEKSlTRM1dL3MwRbwTkELGHnS9uvdHXwg5mKHorGAyTsib0h47RuCtmpmklvZMiaETO9GCwQNK5cejOv9U9yiggyq3DTiRIudZYUPOCWxIXhL1wDqQtq0KK1j3xQ/WaVU9m8BiHzQJcSHV0Qi08MQWi1B96w3w4g0mw==");
        System.out.println(decrypt.decrypt(enResult));
    }

}
