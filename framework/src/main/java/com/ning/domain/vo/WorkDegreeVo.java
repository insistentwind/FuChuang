package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/28 17:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WorkDegreeVo {
    @ApiModelProperty(value = "1")
    private Integer id;
    // 学历
    @ApiModelProperty(value = "学历")
    private String jobDegree;

}