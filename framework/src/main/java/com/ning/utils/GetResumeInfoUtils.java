package com.ning.utils;

import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.UserKey;
import com.ning.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: qjn
 * @create: 2024/04/03 19:17
 **/
@Component
public class GetResumeInfoUtils {
    @Autowired
    private KdfUtils kdfUtils;

    /**
     * 简历解密
     *
     * @param userId
     * @param resume
     * @return
     * @throws Exception
     */
    public Resume getResumeVoByKey(Integer userId, Resume resume) throws Exception {
        //注意这里千万不要把不是这个人的简历放进来，因为会报错密钥解密不出来，错误很难找

        UserKey userKey = KeyHttpUtils.sendGetRequest(SystemConstants.KEY_CLIENT_URL, userId);
        if (userKey == null) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
        //用户密钥
        SecretKeySpec urkey = kdfUtils.stringToKey(userKey.getSecretKey());

//        System.out.println("用户密钥:" + kdfUtils.keyToString(urkey));
        //起始的密钥（需要固定）
        SecretKeySpec beginKey = kdfUtils.generateKey(null, null, 512);
        ;
//        System.out.println("初始密钥:" + kdfUtils.keyToString(beginKey));
        //结合密钥
        SecretKey combinedKey = kdfUtils.generateCombinedKey(beginKey, urkey);
//        System.out.println("结合密钥:" + kdfUtils.keyToString(combinedKey));

        String decodeName = kdfUtils.Decoding(resume.getName(), combinedKey);
        String email = kdfUtils.Decoding(resume.getEmail(), combinedKey);
        String tel = kdfUtils.Decoding(resume.getTel(), combinedKey);
        String live = kdfUtils.Decoding(resume.getLive(), combinedKey);

        resume.setName(decodeName)
                .setEmail(email)
                .setTel(tel)
                .setLive(live);

        return resume;
    }

    /**
     * 简历加密
     *
     * @param userId
     * @param resume
     * @return
     */
    public Resume setResumeByKey(Integer userId, Resume resume) {
        UserKey userKey = KeyHttpUtils.sendGetRequest(SystemConstants.KEY_CLIENT_URL, userId);
        if (userKey == null) {
            throw new BaseException(SystemConstants.USER_HAS_NO_KEY);
        }
        try {
            //用户密钥
            SecretKeySpec urkey = kdfUtils.stringToKey(userKey.getSecretKey());

            SecretKeySpec beginKey = kdfUtils.generateKey(null, null, 512);
            //结合密钥
            SecretKey combinedKey = kdfUtils.generateCombinedKey(beginKey, urkey);
            String decodeName = null;
            String email = null;
            String tel = null;
            String live = null;

            if (resume.getName() != null) decodeName = kdfUtils.Encoding(resume.getName(), combinedKey);
            if (resume.getEmail() != null) email = kdfUtils.Encoding(resume.getEmail(), combinedKey);
            if (resume.getTel() != null) tel = kdfUtils.Encoding(resume.getTel(), combinedKey);
            if (resume.getLive() != null) live = kdfUtils.Encoding(resume.getLive(), combinedKey);

            resume.setName(decodeName)
                    .setEmail(email)
                    .setTel(tel)
                    .setLive(live);
            return resume;
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
    }

    /**
     * 拿到当前用户对应的组合密钥
     *
     * @return
     */
    public SecretKey getUserKey(Integer userId) {
        UserKey userKey = KeyHttpUtils.sendGetRequest("http://124.220.208.63:8082", userId);
        if (userKey == null) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
        try {
            //用户密钥
            SecretKeySpec urkey = kdfUtils.stringToKey(userKey.getSecretKey());

            SecretKeySpec beginKey = kdfUtils.generateKey(null, null, 512);
            ;
            //结合密钥
            SecretKey combinedKey = kdfUtils.generateCombinedKey(beginKey, urkey);
            return combinedKey;
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
    }

}