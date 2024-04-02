package com.ning;

import com.ning.domain.entity.User;
import com.ning.service.UserService;
import com.ning.utils.DesUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * @author: qjn
 * @create: 2024/03/21 14:04
 **/
@SpringBootTest
@Slf4j
public class EnCodeTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DesUtils desUtils;


    @Test
    public void desEncript() throws Exception {

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
    public void MyUtilsTest() throws Exception{

        User user = userService.getById(8);

        String username = user.getUsername();

        String tele = user.getTele();

        String mail = user.getMail();


        System.out.println("密钥是:" + DesUtils.getSecretKey());


        String name = desUtils.Encoding(username);


        String telephone = desUtils.Encoding(tele);
        System.out.println("加密后" + name + ", " + telephone);

        String decodeName = desUtils.Decoding(name);

        String decodeTele = desUtils.Decoding(telephone);

        System.out.println("解密后" + decodeName + ", " + decodeTele);
    }

}