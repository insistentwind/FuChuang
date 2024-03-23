package com.ning.utils;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author: qjn
 * @create: 2024/03/21 14:49
 **/
@Component
public class DesUtils {

    private static SecretKey key;

    private static Cipher cipher;
    /**
     * 初始化
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    @PostConstruct
    public void createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {

        cipher = Cipher.getInstance("DES");
    }

    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        key = keyGen.generateKey();
    }

    /**
     * 获取密钥
     * @return
     */

    public static SecretKey getSecretKey(){
        return key;
    }

    /**
     * 加密
     *
     * @return
     */
    public String Encoding(String msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE,key);

        byte[] encodeMsg = cipher.doFinal(msg.getBytes());

        return Base64.getEncoder().encodeToString(encodeMsg);
    }

    /**
     * 解密
     *
     * @return
     */
    public String Decoding(String encodeMsg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE,key);

        byte[] msg = cipher.doFinal(Base64.getDecoder().decode(encodeMsg));

        return new String(msg, StandardCharsets.UTF_8);

    }

}