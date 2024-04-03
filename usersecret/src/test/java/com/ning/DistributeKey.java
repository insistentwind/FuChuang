package com.ning;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.constants.MqConstants;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserKey;
import com.ning.domain.entity.UserResume;
import com.ning.service.ResumeService;
import com.ning.service.UserResumeService;
import com.ning.service.UserService;
import com.ning.utils.GetResumeInfoUtils;
import com.ning.utils.KdfUtils;
import com.ning.utils.KeyHttpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/03 17:43
 **/
@SpringBootTest
public class DistributeKey {

    @Autowired
    private UserService userService;
    @Autowired
    private KdfUtils kdfUtils;

    @Autowired
    private UserResumeService userResumeService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GetResumeInfoUtils getResumeInfoUtils;
    /**
     * 给所有用户创建密钥
     * TODO 加密了，公司如何向用户申请查看简历呢？
     */
    @Test
    public void createKeyForUsers() throws Exception{

        List<User> userList = userService.list();

        for (User user : userList) {
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getUserId, user.getId());
            List<UserResume> userResumes = userResumeService.list(wrapper);
            if (userResumes.size() > 0) {


                for (UserResume userResume : userResumes) {
                    //初始密钥得固定
                    SecretKeySpec key = kdfUtils.generateKey(null, null, 512);
//                    System.out.println("初始密钥为:" + kdfUtils.keyToString(key));
//                    SecretKeySpec secondKey = kdfUtils.generateKey(null, null, 512);
//                    System.out.println("初始密钥为:" + kdfUtils.keyToString(key));

                    Resume resume = resumeService.getById(userResume.getResumeId());
                    String name = resume.getName();
                    String email = resume.getEmail();
                    String tel = resume.getTel();
                    String live = resume.getLive();
                    try {

                        //用户密钥后期可以变更,因为会被存储
                        SecretKeySpec userKey = kdfUtils.generateKey(user.getUsername(), null, 512);
//                        System.out.println("通过(userId" + user.getId() +  ")信息拿到的密钥:" + kdfUtils.keyToString(userKey));
                        //结合密钥
                        SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);
//                        System.out.println("整合后的密钥:" + kdfUtils.keyToString(combinedKey));
                        System.out.println();
                        UserKey userKey1 = new UserKey();
                        userKey1.setUserId(user.getId());
                        String msg = kdfUtils.keyToString(userKey);
                        userKey1.setSecretKey(msg);

                        rabbitTemplate.convertAndSend(MqConstants.FUCHUANG_EXCHANGE,
                                MqConstants.FUCHUANG_INSERT_KEY,userKey1);

                        /**
                         * 获取密钥的方式都相同
                         */
                        String username = kdfUtils.Encoding(name, combinedKey);
                        String codeMail = kdfUtils.Encoding(email,combinedKey);
                        String codeTel = kdfUtils.Encoding(tel,combinedKey);
                        String codeLive = kdfUtils.Encoding(live, combinedKey);
                        resume.setName(username)
                                .setEmail(codeMail)
                                .setTel(codeTel)
                                .setLive(codeLive);
                        resumeService.updateById(resume);
//                String telephone = kdfUtils.Encoding(tele,combinedKey);
//                System.out.println("加密后:" + name + ", " + telephone);

                        String decodeName = kdfUtils.Decoding(username, combinedKey);
                        String sec = kdfUtils.Decoding(codeMail, combinedKey);
                        String thir = kdfUtils.Decoding(codeTel, combinedKey);
                        String four = kdfUtils.Decoding(codeLive, combinedKey);


//                String decodeTele = kdfUtils.Decoding(telephone,combinedKey);

//                        System.out.println("解密后:" + decodeName + "," + sec + "," + thir + "," + four);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 密钥反解
     */
    @Test
    public void secureContrary() throws Exception {
        Resume resume = resumeService.getById(7);
        System.out.println("解密前简历:" + resume);
        Resume resumeVoByKey = getResumeInfoUtils.getResumeVoByKey(2, resume);
        System.out.println("解密后的简历：" + resumeVoByKey);
    }


    @Test
    public void createKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        List<User> userList = userService.list();
        for (User user : userList) {
            String username = user.getUsername();

            SecretKeySpec key = kdfUtils.generateKey(null, null, 512);
            System.out.println("初始密钥为:" + kdfUtils.keyToString(key));
            //用户密钥后期可以变更,因为会被存储
            SecretKeySpec userKey = kdfUtils.generateKey(username, null, 512);
            System.out.println("通过(userId" + user.getId() +  ")信息拿到的密钥:" + kdfUtils.keyToString(userKey));
            //结合密钥
            SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);
            System.out.println("整合后的密钥:" + kdfUtils.keyToString(combinedKey));
            System.out.println();
            UserKey userKey1 = new UserKey();
            userKey1.setUserId(user.getId());
            String msg = kdfUtils.keyToString(userKey);
            userKey1.setSecretKey(msg);

            rabbitTemplate.convertAndSend(MqConstants.FUCHUANG_EXCHANGE,
                    MqConstants.FUCHUANG_INSERT_KEY,userKey1);
        }
    }


}