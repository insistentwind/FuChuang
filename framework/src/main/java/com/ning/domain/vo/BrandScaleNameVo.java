package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/28 18:18
 **/
@Data
@Accessors(chain = true)
public class BrandScaleNameVo {
    @ApiModelProperty(value = "1")
    private Integer id;
    // 公司规模
    @ApiModelProperty(value = "公司规模")
    private String brandScaleName;

}