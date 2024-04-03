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
 * @create: 2024/04/02 15:57
 **/
@Component
public class KdfUtils {

    private static SecretKeySpec key;

    private static Cipher cipher;
    /**
     * 初始化
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    @PostConstruct
    public void createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {

        cipher = Cipher.getInstance("AES");
    }


    /**
     * 初始化密钥
     * 用户和独立密钥都通过此方法创建密钥
     * 如果salt为空，那么就会随机生成盐值
     * @return
     * @throws Exception
     */
//    @PostConstruct
    //KDF2加密
    public SecretKeySpec generateKey(@Nullable String password, @Nullable byte[] salt, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException {
        // password将密码转换为字符数组形式。这个数组将用作密钥派生函数的输入之一。
        // salt：用作盐值的字节数组。盐值是密码加密时的一个重要参数，它增加了密码的复杂性，提高了安全性
        // 迭代次数。这是密码加密时使用的迭代次数，它确定了密钥派生函数的工作量。
        //生成的密钥长度，以位为单位。在这个示例中，keyLength 通常是 128、192 或 256，因为这是 AES 加密算法所支持的密钥长度。

        if (password == null){
            password = "userResumeInfo";
        }

//        salt = new byte[16];
        if (salt == null){
            salt = new byte[]{
                    (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB,
                    (byte) 0xCD, (byte) 0xEF, (byte) 0xFE, (byte) 0xDC,
                    (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54,
                    (byte) 0x32, (byte) 0x10, (byte) 0x78, (byte) 0x96
            };
        }
        else {
            //随机生成盐值
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);
        }

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        key = new SecretKeySpec(keyBytes, "AES");
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 组合加密
     * @param
     * @param passwordKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public SecretKey generateCombinedKey(SecretKeySpec existingKey,SecretKeySpec passwordKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 将现有密钥的字节与额外字符串的字节进行组合

        byte[] existingKeyBytes = existingKey.getEncoded();
        byte[] passwordKeyBytes = passwordKey.getEncoded();
        byte[] combinedBytes = new byte[existingKeyBytes.length + passwordKeyBytes.length];
        //这两行代码是用来将两个字节数组 existingKeyBytes 和 additionalStringBytes 进行拼接的。拼接后的结果存储在 combinedBytes 中。
        System.arraycopy(existingKeyBytes, 0, combinedBytes, 0, existingKeyBytes.length);
        System.arraycopy(passwordKeyBytes, 0, combinedBytes, existingKeyBytes.length, passwordKeyBytes.length);



        byte[] salt = new byte[]{
                (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB,
                (byte) 0xCD, (byte) 0xEF, (byte) 0xFE, (byte) 0xDC,
                (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54,
                (byte) 0x32, (byte) 0x10, (byte) 0x78, (byte) 0x96
        };

        // 使用哈希函数生成新的密钥
        KeySpec keySpec = new PBEKeySpec(Base64.getEncoder().encodeToString(combinedBytes).toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKeySpec newKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
        return newKey;
    }
    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
//    @PostConstruct
//    //它用于指示带有此注解的方法应该在依赖注入完成之后立即被调用。在Spring框架中，@PostConstruct注解也得到了支持，并且具有相同的作用。
//    public void init() throws Exception{
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(128);//128,256,192
//        key = keyGen.generateKey();
//    }

    /**
     * 获取密钥
     * @return
     */
    public SecretKey getSecretKey(){
        return key;
    }









    /**
     * 将密钥对象转换为字符串
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
    public String Encoding(String msg,SecretKey newKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {

        cipher.init(Cipher.ENCRYPT_MODE,newKey);

        byte[] encodeMsg = cipher.doFinal(msg.getBytes());

        return Base64.getEncoder().encodeToString(encodeMsg);
    }

    /**
     * 解密
     *
     * @return
     */
    public String Decoding(String encodeMsg,SecretKey newKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE,newKey);

        byte[] msg = cipher.doFinal(Base64.getDecoder().decode(encodeMsg));

        return new String(msg, StandardCharsets.UTF_8);

    }


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException {
        // 生成密钥
        String password = "password";
        byte[] salt = new byte[16]; // 使用随机盐或固定盐
//        SecretKeySpec key = generateKey(null, salt, 255);
//
//        System.out.println("初始密钥:" + keyToString(key));
//
//        String msg = "我想你了";
//        String userPassword = "123456";
//
//        SecretKey newKey = generateCombinedKey(key, userPassword);
//
//        System.out.println("组合密钥:" + keyToString(newKey));

//        createCipher();
//        // 加密
//        String plaintext = "Hello, world!";
//        String ciphertext = Encoding(plaintext, newKey);
//        System.out.println("加密后: " + ciphertext);
//
//        // 解密
//        String decryptedText = Decoding(ciphertext, newKey);
//        System.out.println("解密后: " + decryptedText);
    }

}