package com.ning.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/04/05 17:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "学校信息实体",description = "")
public class UniversityDto {
    /** id */
    private Integer schoolId ;
    /** 学校名称 */
    @ApiModelProperty(value = "学校名称",notes = "")
    private String name ;
    /** 省份名称 */
    @ApiModelProperty(value = "省份名称",notes = "")
    private String provinceName ;
    /** 城市名称 */
    @ApiModelProperty(value = "城市名称",notes = "")
    private String cityName ;
    /** 乡镇名称 */
    @ApiModelProperty(value = "乡镇名称",notes = "")
    private String townName ;
    /** 地区 */
    @ApiModelProperty(value = "地区",notes = "")
    private String area ;

}