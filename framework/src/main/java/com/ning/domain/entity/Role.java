package com.ning.domain.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Role)表实体类
 *
 * @author makejava
 * @since 2024-03-11 20:38:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "系统角色表",description = "")
@TableName("sys_role")
public class Role  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 角色名称 */
    @ApiModelProperty(name = "角色名称",notes = "")
    private String roleName ;
    /** 角色权限字符串 */
    @ApiModelProperty(name = "角色权限字符串",notes = "")
    private String roleKey ;
    /** 排序 */
    @ApiModelProperty(name = "排序",notes = "")
    private Integer roleSort ;
    /** 是否启用(0是,1是) */
    @ApiModelProperty(name = "是否启用(0是,1是)",notes = "")
    private Integer status ;
    /** 创建人id */
    @ApiModelProperty(name = "创建人id",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;
    /** 更新人id */
    @ApiModelProperty(name = "更新人id",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime ;
    /** 标记 */
    @ApiModelProperty(name = "标记",notes = "")
    private String remark ;
    /** 是否启用(0是，1否) */
    @ApiModelProperty(name = "是否启用(0是，1否)",notes = "")
    private Integer delFlag ;


}

