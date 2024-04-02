package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/31 17:56
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncePageVo {

    //标题
    @ApiModelProperty(value = "标题")
    private String title;
    //文章摘要
    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "页号")
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}