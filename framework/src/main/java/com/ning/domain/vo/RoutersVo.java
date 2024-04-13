package com.ning.domain.vo;

import com.ning.domain.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "路由权限")
public class RoutersVo {
    @ApiModelProperty(value = "路由权限")
    private List<Menu> menus;
}
