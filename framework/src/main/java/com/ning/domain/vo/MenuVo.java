package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/11 20:52
 **/
@Data
@AllArgsConstructor
public class MenuVo {
    @ApiModelProperty(value = "权限id")
    private List<String> perms;
    @ApiModelProperty(value = "权限名称")
    private List<String> menuName;

}