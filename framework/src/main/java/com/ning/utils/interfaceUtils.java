package com.ning.utils;

import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author: qjn
 * @create: 2024/04/06 17:37
 **/
@Component
public class interfaceUtils {

    private static SecretKeySpec key;

    private static Cipher cipher;

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 初始化
     *
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    @PostConstruct
    public void createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {

        cipher = Cipher.getInstance(ALGORITHMSTR);
    }


    /**
     * 初始化密钥
     * 用户和独立密钥都通过此方法创建密钥
     * 如果salt为空，那么就会随机生成盐值
     *
     * @return
     * @throws Exception
     */
    //KDF2加密
    @PostConstruct
    public SecretKeySpec generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException {
        // password将密码转换为字符数组形式。这个数组将用作密钥派生函数的输入之一。
        // salt：用作盐值的字节数组。盐值是密码加密时的一个重要参数，它增加了密码的复杂性，提高了安全性
        // 迭代次数。这是密码加密时使用的迭代次数，它确定了密钥派生函数的工作量。
        //生成的密钥长度，以位为单位。在这个示例中，keyLength 通常是 128、192 或 256，因为这是 AES 加密算法所支持的密钥长度。
        int keyLength = 256;
        String password = "MyInterfacesEncodeUtils";


//        salt = new byte[16];
        byte[] salt = new byte[]{
                (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB,
                (byte) 0xCD, (byte) 0xEF, (byte) 0xFE, (byte) 0xDC,
                (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54,
                (byte) 0x32, (byte) 0x10, (byte) 0x78, (byte) 0x96
        };

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        key = new SecretKeySpec(keyBytes, "AES");
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 获取密钥
     *
     * @return
     */
    public SecretKey getSecretKey() {
        return key;
    }


    /**
     * 将密钥对象转换为字符串
     *
     * @param key 密钥对象
     * @return 密钥字符串
     */
    public String keyToString(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public SecretKeySpec stringToKey(String encodedKey) {
        // 使用 Base64 解码字符串
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        // 使用解码后的字节数组构造 SecretKey 对象
        //0：这是数组的起始索引，表示从字节数组的第一个字节开始，作为密钥的起始位置。
        SecretKeySpec key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        return key;
    }


    /**
     * 加密
     *
     * @return
     */
    public String Encoding(String msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        SecretKeySpec newKey = key;
        cipher.init(Cipher.ENCRYPT_MODE, newKey);

        byte[] encodeMsg = cipher.doFinal(msg.getBytes());

        return Base64.getEncoder().encodeToString(encodeMsg);
    }

    /**
     * 解密
     *
     * @return
     */
    public String Decoding(String encodeMsg) throws Exception {



        SecretKeySpec newKey = key;

        cipher.init(Cipher.DECRYPT_MODE, newKey);

        byte[] msg = cipher.doFinal(Base64.getDecoder().decode(encodeMsg));

        return new String(msg, StandardCharsets.UTF_8);

    }


}