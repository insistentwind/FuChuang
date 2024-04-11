package com.ning.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (Company)表实体类
 *
 * @author makejava
 * @since 2024-01-30 16:40:54
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "公司表",description = "")
@Builder
@TableName("company")
public class Company  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 加密id */
    @ApiModelProperty(name = "加密id",notes = "")
    private String encryptBrandid ;
    /** 行业 */
    @ApiModelProperty(name = "行业",notes = "")
    private String brandIndustry ;
    /** 公司名称 */
    @ApiModelProperty(name = "公司名称",notes = "")
    private String brandName ;
    /** 公司图标 */
    @ApiModelProperty(name = "公司图标",notes = "")
    private String brandLogo ;
    /** 公司规模 */
    @ApiModelProperty(name = "公司规模",notes = "")
    private Integer brandScaleName ;
    /** 审核状态 */
    @ApiModelProperty(value = "审核状态(0待审核，1审核通过，2审核拒绝)")
    private Integer status;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy ;
    /** 删除标志位 */
    @ApiModelProperty(name = "删除标志位",notes = "")
    private Integer delFlag ;

}

