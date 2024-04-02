package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 12:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {
    @ApiModelProperty(value = "权限")
    List<TreeSelectVo> menus;

    @ApiModelProperty(value = "已有权限")
    List<String> checkedKeys;
}