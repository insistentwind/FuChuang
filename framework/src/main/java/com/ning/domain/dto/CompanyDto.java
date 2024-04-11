package com.ning.domain.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
public class CompanyDto {
    //当前分页页数
    @ApiModelProperty(value = "当前分页页数")
    private int pageNum;

    //每页显示记录数
    @ApiModelProperty(value = "每页显示记录数")
    private int pageSize;

    private Integer id;

    //行业
    @ApiModelProperty(value = "行业")
    private String brandIndustry;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String brandName;
    //公司图标
    @ApiModelProperty(value = "公司图标")
    private String brandLogo;
    //公司规模
    @ApiModelProperty(value = "公司规模")
    private String brandScaleName;
    /** 审核状态 */
    @ApiModelProperty(value = "审核状态(0待审核，1审核通过，2审核拒绝)")
    private Integer status;

}

