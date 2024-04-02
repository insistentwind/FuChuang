package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: qjn
 * @Date: 2024/1/16 12:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户条件")
@Accessors(chain = true)
public class UserVo {

    /**  */
    @ApiModelProperty(value = "1")
    private Integer id ;
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name ;
    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String username ;
    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password ;
    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String avatar ;
    /** 求职状态（是否立刻到岗，1是，0否） */
    @ApiModelProperty(value = "求职状态（是否立刻到岗，1是，0否）")
    private Integer status ;
    /** 性别（1男，0女） */
    @ApiModelProperty(value = "性别（1男，0女）")
    private Integer sex ;
    /** 身份（学生，社会人士） */
    @ApiModelProperty(value = "身份（学生，社会人士）")
    private String idCard ;
    /** 出生日期 */
    @ApiModelProperty(value = "出生日期")
    private String birthday ;
    /** 电话 */
    @ApiModelProperty(value = "电话")
    private String tele ;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String mail ;
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy ;
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime ;
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy ;
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;
    /** 是否为公司hr(0否，1是) */
    @ApiModelProperty(value = "是否为公司hr(0否，1是)")
    private Integer isCompany ;
    /**  */
    @ApiModelProperty(value = "1")
    private Integer delFlag ;


    @ApiModelProperty(value = "jwt")
    private String jwt;

    // 修改时用（旧密码）
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;




}
