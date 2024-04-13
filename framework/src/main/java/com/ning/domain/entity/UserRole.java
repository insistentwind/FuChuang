package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Accessors(chain = true)
@ApiModel(value = "用户-角色表",description = "")
@TableName("sys_user_role")
public class UserRole  {
    /** 管理端用户id */
    @ApiModelProperty(name = "管理端用户id",notes = "")
    private Long userId;
    /** 角色id */
    @ApiModelProperty(name = "角色id",notes = "")
    private Long roleId;




}

