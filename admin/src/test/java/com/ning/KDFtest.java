package com.ning;

import com.ning.domain.entity.User;
import com.ning.service.UserService;
import com.ning.utils.DesUtils;
import com.ning.utils.KdfUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author: qjn
 * @create: 2024/04/02 18:21
 **/
@SpringBootTest
@Slf4j
public class KDFtest {

    @Autowired
    private UserService userService;

    @Autowired
    private KdfUtils kdfUtils;


    @Test
    public void KDFEncript() throws Exception {

        User user = userService.getById(8);

        String username = user.getUsername();

        String tele = user.getTele();

        String mail = user.getMail();

        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        keyGen.init(56); // DES 密钥长度为 56 位

        SecretKey key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encodingName = cipher.doFinal(username.getBytes());

        byte[] teleNumber = cipher.doFinal(tele.getBytes());

        byte[] email = cipher.doFinal(mail.getBytes());

        String encodeName = Base64.getEncoder().encodeToString(encodingName);

        System.out.println("加密后的数据：" + encodeName
                + ", " + Base64.getEncoder().encodeToString(teleNumber) + ", " + Base64.getEncoder().encodeToString(email));
        // Base64从字符串转回二进制数组
        byte[] strings = Base64.getDecoder().decode(encodeName);

        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] newName = cipher.doFinal(strings);

        byte[] eMai = cipher.doFinal(email);
        byte[] telephone = cipher.doFinal(teleNumber);

        System.out.println("解密后的数据：" + new String(newName)
                + ", " + new String(telephone) + ", " + new String(eMai));
    }

    @Test
    /**
     * 工具类加密流程
     */
    public void MyUtilsTest() throws Exception{

        User user = userService.getById(8);

        String username = user.getUsername();

        String tele = user.getTele();

        String mail = user.getMail();


        SecretKeySpec key = kdfUtils.generateKey(null, null, 256);

        System.out.println("初始密钥为:" + kdfUtils.keyToString(key));

        SecretKeySpec userKey = kdfUtils.generateKey(username, null, 255);

        System.out.println("通过用户信息拿到的密钥:" + kdfUtils.keyToString(userKey));

        SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);

        System.out.println("整合后的密钥:" + kdfUtils.keyToString(combinedKey));

        /**
         * 获取密钥的方式都相同
         */
        String name = kdfUtils.Encoding(username,combinedKey);

        String telephone = kdfUtils.Encoding(tele,combinedKey);
        System.out.println("加密后:" + name + ", " + telephone);

        String decodeName = kdfUtils.Decoding(name,combinedKey);



        String decodeTele = kdfUtils.Decoding(telephone,key);

        System.out.println("解密后:" + decodeName + ", " + decodeTele);
    }
}