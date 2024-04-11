package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: qjn
 * @create: 2024/03/24 10:35
 **/
@Data
public class WorkPageVo {

//    //页数
//    private Integer pageNum;
//    //分页大小
//    private Integer pageSize;

    // id前端可传可不传
    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    @ApiModelProperty(value = "学历要求")
    private Integer education ;
    /** 工作经验要求 */
    @ApiModelProperty(value = "工作经验要求")
    private Integer jobExperience ;
    /** 薪资分类 */
    @ApiModelProperty(value = "薪资分类")
    private Integer salary ;
    //分类id
    @ApiModelProperty(value = "分类id")
    private Integer classifyId;
}