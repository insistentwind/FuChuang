package com.ning.domain.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2024-01-16 14:27:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "用户表",description = "")
@TableName("user")
public class User  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 姓名 */
    @ApiModelProperty(name = "姓名",notes = "")
    private String name ;
    /** 用户名 */
    @ApiModelProperty(name = "用户名",notes = "")
    private String username ;
    /** 密码 */
    @ApiModelProperty(name = "密码",notes = "")
    private String password ;
    /** 头像 */
    @ApiModelProperty(name = "头像",notes = "")
    private String avatar ;
    /** 求职状态（是否立刻到岗，1是，0否） */
    @ApiModelProperty(name = "求职状态（是否立刻到岗，1是，0否）",notes = "")
    private Integer status ;
    /** 性别（1男，0女） */
    @ApiModelProperty(name = "性别（1男，0女）",notes = "")
    private Integer sex ;
    /** 身份（学生，社会人士） */
    @ApiModelProperty(name = "身份（学生，社会人士）",notes = "")
    private String idCard ;
    /** 出生日期 */
    @ApiModelProperty(name = "出生日期",notes = "")
    private String birthday ;
    /** 电话 */
    @ApiModelProperty(name = "电话",notes = "")
    private String tele ;
    /** 邮箱 */
    @ApiModelProperty(name = "邮箱",notes = "")
    private String mail ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;
    /** 是否为公司hr(0否，1是) */
    @ApiModelProperty(name = "是否为公司hr(0否，1是)",notes = "")
    private Integer isCompany ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer delFlag ;
}

