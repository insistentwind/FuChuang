package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TreeSelectVo {
    @ApiModelProperty(value = "1")
    private Long id;

    //菜单（权限）名称
    @ApiModelProperty(value = "菜单（权限）名称")
    private String label;
    //父id
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "菜单类型(1:菜单 2:目录 3:外链 4:按钮)")
    private Integer type;
    @ApiModelProperty(value = "组件路径(vue页面完整路径，省略.vue后缀)")
    private String component;
    //子目录
    @ApiModelProperty(value = "子目录")
    private List<TreeSelectVo> children;
}
