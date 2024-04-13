package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2023/12/2 21:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "状态")
    private Integer status;

}
