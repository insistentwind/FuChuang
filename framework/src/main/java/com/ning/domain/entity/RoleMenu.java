package com.ning.domain.entity;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author makejava
 * @since 2024-03-12 20:02:32
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "角色和菜单关联表",description = "")
@TableName("sys_role_menu")
public class RoleMenu {

    //角色ID
    /** 角色ID */
    @ApiModelProperty(name = "角色ID",notes = "")
    private Long roleId;
    //菜单ID
    /** 菜单ID */
    @ApiModelProperty(name = "菜单ID",notes = "")
    private Long menuId;


}

