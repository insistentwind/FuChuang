package com.ning.domain.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class CompanyDto {
    //当前分页页数
    private int pageNum;

    //每页显示记录数
    private int pageSize;

    private Integer id;

    //行业
    private String brandIndustry;
    //公司名称
    private String brandName;
    //公司图标
    private String brandLogo;
    //公司规模
    private String brandScaleName;

}

