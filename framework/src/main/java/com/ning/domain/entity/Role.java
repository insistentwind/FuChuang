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
    @TableId
    private Long id;

    //角色名称
    @ApiModelProperty(value = "角色名称",notes = "")
    private String name;
    //角色编码
    @ApiModelProperty(value = "角色编码",notes = "")
    private String code;
    //显示顺序
    @ApiModelProperty(value = "显示顺序",notes = "")
    private Integer sort;
    //角色状态(1-正常；0-停用)
    @ApiModelProperty(value = "角色状态(1-正常；0-停用)",notes = "")
    private Integer status;
    //数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)
    @ApiModelProperty(value = "数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)",notes = "")
    private Integer dataScope;
    //逻辑删除标识(0-未删除；1-已删除)
    @ApiModelProperty(value = "逻辑删除标识(0-未删除；1-已删除)",notes = "")
    private Integer delFlag;
    //更新时间
    @ApiModelProperty(value = "更新时间",notes = "")
    private LocalDateTime createTime;
    //创建时间
    @ApiModelProperty(value = "创建时间",notes = "")
    private LocalDateTime updateTime;


}

