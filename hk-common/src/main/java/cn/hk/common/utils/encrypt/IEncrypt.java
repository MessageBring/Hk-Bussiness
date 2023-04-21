package cn.hk.common.utils.encrypt;

public interface IEncrypt {
    String encrypt(String plainText);

    String decrypt(String cipherText);
}
