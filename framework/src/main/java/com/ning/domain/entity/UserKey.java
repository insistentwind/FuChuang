package com.ning.domain.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (UserKey)表实体类
 *
 * @author makejava
 * @since 2024-04-02 22:51:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserKey  {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //用户id
    private Integer userId;
    //密钥
    private String secretKey;

}

