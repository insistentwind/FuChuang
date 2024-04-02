package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: qjn
 * @create: 2024/03/05 21:54
 **/
@Data
public class HistoryVo {
    //职位id
    @ApiModelProperty(value = "职位id")
    private Integer workId;
    //职位名称
    @ApiModelProperty(value = "职位名称")
    private String title;
    //薪资
    @ApiModelProperty(value = "薪资")
    private String salary;
    //所属公司
    @ApiModelProperty(value = "所属公司")
    private String company;
    //工作地点
    @ApiModelProperty(value = "工作地点")
    private String address;
    //浏览量
    @ApiModelProperty(value = "浏览量")
    private Long viewCount;
}