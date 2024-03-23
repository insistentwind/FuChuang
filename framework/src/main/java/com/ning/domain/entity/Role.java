package com.ning.domain.entity;

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
@TableName("sys_role")
public class Role  {
    @TableId
    private Integer id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //排序
    private Integer roleSort;
    //是否启用(0是)
    private Integer status;
    //创建者
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //标记
    private String remark;
    //是否启用(0是，1否)
    private Integer delFlag;



}

