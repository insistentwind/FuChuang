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
@TableName("company")
public class Company  {
    @TableId
    private Integer id;

    //加密id
    private String encryptBrandid;
    //行业
    private String brandIndustry;
    //公司名称
    private String brandName;
    //公司图标
    private String brandLogo;
    //公司规模
    private String brandScaleName;
    //逻辑删除 0为未删除，1为删除
    private Integer delFlag;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    //创建者
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

