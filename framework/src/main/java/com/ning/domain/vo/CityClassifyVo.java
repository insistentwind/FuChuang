package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/26 16:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CityClassifyVo {
    @ApiModelProperty(value = "1")
    private Integer id;

    //城市
    @ApiModelProperty(value = "城市")
    private String cityName;

}