package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (UserRole)表实体类
 *
 * @author makejava
 * @since 2024-03-11 21:40:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role")
@Accessors(chain = true)
public class UserRole  {
    @TableId
    private Integer id;

    //管理端用户id
    private Integer userId;
    //角色id
    private Integer roleId;



}

