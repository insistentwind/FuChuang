package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * @Author: qjn
 * @Date: 2024/2/1 18:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavorDto {
    //收藏人id
    @ApiModelProperty(value = "收藏人id")
    private Integer userId;
    //职位id
    @ApiModelProperty(value = "职位id")
    private Integer workId;
}
