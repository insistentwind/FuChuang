package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/21 22:45
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClassifyVo {
    @ApiModelProperty(value = "1")
    Integer id;

    //max分类
    @ApiModelProperty(value = "max分类")
    private String bigClassify;
    //middle分类
    @ApiModelProperty(value = "middle分类")
    private String midClassify;
    //子分类
    @ApiModelProperty(value = "子分类")
    private String smallClassify;
    //薪资地址
    @ApiModelProperty(value = "薪资地址")
    private String smallClassifyHtml;

}